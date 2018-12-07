package ir.behzadnikbin.oauth2example.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/*
    UserDetails to handle Spring Authentication
 */
@Getter
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    public String getPassword() {
        return user.getPassword();      //  password of user
    }

    @Override
    public String getUsername() {
        return user.getUsername();      //  username of user
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;                    //  user never expires
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;                    //  user will be never locked
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;                    //  user's credentials will never expire
    }

    @Override
    public boolean isEnabled() {
        return user.getEnabled();       //  By setting user.enabled, authentication of user can be disabled
    }
}
