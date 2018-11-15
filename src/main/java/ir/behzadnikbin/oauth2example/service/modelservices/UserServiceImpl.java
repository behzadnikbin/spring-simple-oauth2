package ir.behzadnikbin.oauth2example.service.modelservices;

import ir.behzadnikbin.oauth2example.dto.ChangePasswordDto;
import ir.behzadnikbin.oauth2example.dto.ListPageRequestDto.UserPageRequestDto;
import ir.behzadnikbin.oauth2example.model.user.User;
import ir.behzadnikbin.oauth2example.repository.user.UserRepository;
import ir.behzadnikbin.oauth2example.service.serviceresult.ServiceResult;
import ir.behzadnikbin.oauth2example.service.serviceresult.ServiceResultFactory;
import ir.behzadnikbin.oauth2example.service.serviceresult.ValidationError;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    @Override
    public ServiceResult list(UserPageRequestDto pageRequestDto) {
        Pageable pageable = PageRequest.of(
                pageRequestDto.page,
                pageRequestDto.pageSize,
                new Sort(pageRequestDto.direction, pageRequestDto.order)
        );

        Page<User> users = userRepository.findAll((Specification<User>) (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (pageRequestDto.name != null) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + pageRequestDto.name.toLowerCase() + "%"));
            }
            if (pageRequestDto.username != null) {
                predicates.add(cb.like(cb.lower(root.get("username")), "%" + pageRequestDto.username.toLowerCase() + "%"));
            }
            return predicates.stream().reduce(cb::and).orElse(null);
        }, pageable);

        users.getContent().forEach(user -> user.setPassword(null));

        return ServiceResultFactory.ok(users);
    }

    @Override
    public ServiceResult create(User user) {

        List<ValidationError> errors = user.validate();
        if (errors.size() > 0) {
            return ServiceResultFactory.validationError(errors);
        }

        User dbUser = userRepository.findUserByUsername(user.getUsername());
        if (dbUser != null) {
            errors.add(new ValidationError("username", "exists"));
            return ServiceResultFactory.validationError(errors);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getEnabled() == null) {
            user.setEnabled(true);
        }
        user.setSystemUser(false);
        userRepository.save(user);
        return ServiceResultFactory.ok();
    }

    @Transactional(readOnly = true)
    @Override
    public ServiceResult read(User user) {
        if (user == null || user.getId() == null) {
            return ServiceResultFactory.validationError(Collections.singletonList(new ValidationError("id", "empty")));
        }
        User dbUser = userRepository.findOneById(user.getId());
        dbUser.setPassword(null);
        return ServiceResultFactory.ok(dbUser);
    }

    @Override
    public ServiceResult update(User user) {
        List<ValidationError> errors = user.validateForUpdate();
        if (errors.size() > 0) {
            return ServiceResultFactory.validationError(errors);
        }

        User dbUserWithUsername = userRepository.findUserByUsername(user.getUsername());
        User dbUser = userRepository.findOneById(user.getId());
        if (dbUserWithUsername != null && !Objects.equals(dbUser.getId(), user.getId())) {
            errors.add(new ValidationError("username", "exists"));
            return ServiceResultFactory.validationError(errors);
        }


        boolean isSystemUserInDb = dbUser.isSystemUser();

        user.setPassword(null);
        user.setUsername(null);
        user.setSystemUser(null);
        modelMapper.map(user, dbUser);

        if (isSystemUserInDb) {      //  system user cannot be disabled
            dbUser.setEnabled(true);
        }

        userRepository.save(dbUser);
        return ServiceResultFactory.ok();
    }

    @Override
    public ServiceResult delete(User user) {
        List<ValidationError> errors = user.validateForDelete();
        if (errors.size() > 0) {
            return ServiceResultFactory.validationError(errors);
        }
        if (userRepository.deleteDistinctById(user.getId()) > 0) {
            return ServiceResultFactory.ok();
        }
        return ServiceResultFactory.validationError(Collections.singletonList(new ValidationError("id", "not found")));
    }

    @Override
    public ServiceResult changePassword(ChangePasswordDto changePasswordDto) {
        List<ValidationError> errors = new ArrayList<>();
        User dbUser = userRepository.findOneById(changePasswordDto.userId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String sessionUsername = authentication.getName();
        User sessionDbUser = userRepository.findUserByUsername(sessionUsername);
        boolean isSessionDbUserSystemUser = sessionDbUser.isSystemUser();
        boolean differentUsername = !Objects.equals(sessionUsername, dbUser.getUsername());
        if (differentUsername) {
            if (!isSessionDbUserSystemUser) {
                throw new AccessDeniedException("user can only change its own password. only system user can change other user's userpassowrd");
            }
        }

        if (!(isSessionDbUserSystemUser && differentUsername) && (changePasswordDto.oldPassword == null || !passwordEncoder.matches(changePasswordDto.oldPassword, dbUser.getPassword()))) {
            errors.add(new ValidationError("oldPassword", "invalid"));
        }
        if (StringUtils.isEmpty(changePasswordDto.newPassword)) {
            errors.add(new ValidationError("newPassword", "empty"));
        }

        if (errors.size() > 0) {
            return ServiceResultFactory.validationError(errors);
        }
        String encNewPass = passwordEncoder.encode(changePasswordDto.newPassword);
        dbUser.setPassword(encNewPass);
        userRepository.save(dbUser);
        return ServiceResultFactory.ok();
    }
}
