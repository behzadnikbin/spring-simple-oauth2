package ir.behzadnikbin.oauth2example.service.modelservices;

import ir.behzadnikbin.oauth2example.dto.ChangePasswordDto;
import ir.behzadnikbin.oauth2example.dto.ListPageRequestDto.UserPageRequestDto;
import ir.behzadnikbin.oauth2example.model.user.User;
import ir.behzadnikbin.oauth2example.service.serviceresult.ServiceResult;
import org.springframework.data.domain.Page;

public interface UserService extends CrudServiceInterface<User> {

    ServiceResult<Page<User>> list(UserPageRequestDto pageRequestDto);

    ServiceResult changePassword(ChangePasswordDto changePasswordDto);
}
