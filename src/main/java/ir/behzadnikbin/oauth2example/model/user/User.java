package ir.behzadnikbin.oauth2example.model.user;

import ir.behzadnikbin.oauth2example.model.AbstractModel;
import ir.behzadnikbin.oauth2example.service.serviceresult.ValidationError;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

/*
    User entity
 */
@Getter
@Setter
@Entity
public class User extends AbstractModel {

    /*
        Default username and password of admin user
     */
    public static final String
            ADMIN_USERNAME = "admin",
            ADMIN_PASSWORD = "admin"
                    ;

    @Column(unique = true, nullable = false, updatable = false)
    private String username;        //  username of user

    @Column(nullable = false)
    private String name;            //  full name of user

    @Column(nullable = false)
    private String password;        //  password of user. it must be encrypted or hashed before persisting

    @Column(nullable = false)
    private Boolean enabled;        //  if user is enabled, login will be possible

    @Column(nullable = false, updatable = false)
    private Boolean systemUser = false;     //  the default user (admin) cannot be deleted
}
