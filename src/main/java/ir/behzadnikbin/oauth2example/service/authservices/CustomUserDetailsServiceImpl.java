package ir.behzadnikbin.oauth2example.service.authservices;

import ir.behzadnikbin.oauth2example.model.user.CustomUserDetails;
import ir.behzadnikbin.oauth2example.model.user.User;
import ir.behzadnikbin.oauth2example.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
    This class is used by Spring to handle user login.
    If the user is not found in database, UsernameNotFoundException is thrown.
 */

@Transactional(readOnly = true)
@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /*
        Successful login: return of UserDetails
        Unsuccessful login: throwing UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("user '" + username + "' not found.");
        }
        return new CustomUserDetails(user);
    }
}
