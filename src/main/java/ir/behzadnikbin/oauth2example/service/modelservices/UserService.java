package ir.behzadnikbin.oauth2example.service.modelservices;

import ir.behzadnikbin.oauth2example.dto.ChangePasswordDto;
import ir.behzadnikbin.oauth2example.dto.ListPageRequestDto.UserPageRequestDto;
import ir.behzadnikbin.oauth2example.model.user.User;
import ir.behzadnikbin.oauth2example.service.serviceresult.ServiceResult;

public interface UserService extends CrudServiceInterface<User> {

    ServiceResult list(UserPageRequestDto pageRequestDto);

    ServiceResult changePassword(ChangePasswordDto changePasswordDto);
}
