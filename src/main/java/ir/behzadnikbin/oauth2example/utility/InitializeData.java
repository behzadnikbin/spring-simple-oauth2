package ir.behzadnikbin.oauth2example.utility;

import ir.behzadnikbin.oauth2example.model.user.User;
import ir.behzadnikbin.oauth2example.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;

import java.util.Date;

/*
    InitializeData bean is used to persist initial data
    In this project admin user is persisted at startup
 */
@Component
@Slf4j
public class InitializeData implements ApplicationListener<ContextRefreshedEvent>, ResourceLoaderAware {

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        persistInitialData();
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
    }

    private void persistInitialData() {
        TransactionStatus transactionStatus = transactionManager.getTransaction(null);
        /////////////   begin persisting data

        //  find admin user in DB
        User admin = userRepository.findUserByUsername(User.ADMIN_USERNAME);
        boolean firstTime = admin == null;

        if (firstTime) {    //  persist admin user if it doesn't exist
            //////          begin user
            admin = new User();
            admin.setUsername(User.ADMIN_USERNAME);
            admin.setName("Admin User");
            admin.setPassword(passwordEncoder.encode(User.ADMIN_PASSWORD));
            admin.setEnabled(true);
            admin.setSystemUser(true);
            userRepository.save(admin);
            //////          end user
        }

        /////////////   end persisting data
        transactionManager.commit(transactionStatus);
    }
}
