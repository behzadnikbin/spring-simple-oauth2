package ir.behzadnikbin.oauth2example.dto;

import java.util.UUID;

public class ChangePasswordDto {
    public UUID userId;
    public String oldPassword, newPassword;
}
