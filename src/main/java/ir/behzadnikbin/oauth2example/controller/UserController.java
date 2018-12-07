package ir.behzadnikbin.oauth2example.controller;

import ir.behzadnikbin.oauth2example.dto.ChangePasswordDto;
import ir.behzadnikbin.oauth2example.dto.ListPageRequestDto.UserPageRequestDto;
import ir.behzadnikbin.oauth2example.model.user.User;
import ir.behzadnikbin.oauth2example.service.modelservices.UserService;
import ir.behzadnikbin.oauth2example.service.serviceresult.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
@EnableAutoConfiguration
@RequestMapping("/user")
public class UserController implements CrudControllerInterface <User>  {

    @Autowired
    private UserService userService;

    @RequestMapping("/list")
    public ServiceResult<Page<User>> list(@RequestBody UserPageRequestDto userPageReq) {
        return userService.list(userPageReq);
    }

    @RequestMapping("/create")
    @Override
    public ServiceResult create(@RequestBody User user) {
        return userService.create(user);
    }

    @RequestMapping("/read")
    @Override
    public ServiceResult<User> read(@RequestBody User user) {
        return userService.read(user);
    }

    @RequestMapping("/update")
    @Override
    public ServiceResult update(@RequestBody User user) {
        return userService.update(user);
    }

    @RequestMapping("/delete")
    @Override
    public ServiceResult delete(@RequestBody User user) {
        return userService.delete(user);
    }

    @RequestMapping("/changePassword")
    public ServiceResult changePassword(@RequestBody ChangePasswordDto changePasswordDto, HttpServletRequest request) {
        return userService.changePassword(changePasswordDto);
    }

}
