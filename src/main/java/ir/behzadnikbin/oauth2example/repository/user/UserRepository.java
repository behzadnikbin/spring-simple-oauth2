package ir.behzadnikbin.oauth2example.repository.user;

import ir.behzadnikbin.oauth2example.model.user.User;
import ir.behzadnikbin.oauth2example.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends AbstractRepository<User, UUID> {
    User findUserByUsername(String username);
}
