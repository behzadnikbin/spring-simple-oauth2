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

@Getter
@Setter
@Entity
public class User extends AbstractModel {

    @Override
    public List<ValidationError> validate() {
        List<ValidationError> errors = new ArrayList<>();
        if (StringUtils.isEmpty(username)) {
            errors.add(new ValidationError("username", "empty"));
        }
        if (StringUtils.isEmpty(password)) {
            errors.add(new ValidationError("password", "empty"));
        }
        return errors;
    }

    public List<ValidationError> validateForUpdate() {
        List<ValidationError> errors = new ArrayList<>();
        if (StringUtils.isEmpty(username)) {
            errors.add(new ValidationError("username", "empty"));
        }
        return errors;
    }

    public List<ValidationError> validateForChangePassword() {
        List<ValidationError> errors = new ArrayList<>();
        if (StringUtils.isEmpty(password)) {
            errors.add(new ValidationError("password", "empty"));
        }
        return errors;
    }

    public List<ValidationError> validateForDelete() {
        List<ValidationError> errors = new ArrayList<>();
        if (systemUser == null || systemUser) {
            errors.add(new ValidationError("", "system user cannot be deleted"));
        }
        return errors;
    }

    public static final String
            ADMIN_USERNAME = "admin",
            ADMIN_PASSWORD = "admin"
                    ;

    @Column(unique = true)
    private String username;

    @Column
    private String name;

    @Column
    private String password;

    @Column
    private Boolean enabled;

    @Column
    private Boolean systemUser = false;

    public boolean isSystemUser() {
        return systemUser != null && systemUser;
    }
}
