package ru.kinoposisk.dto.changePass;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangePassByLoginDTO {

    @NotEmpty(message = "Login is required")
    @Size(min = 4, max = 20, message = "Login must be between 4 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Password must contain only letters and numbers")
    private String login;

    @NotEmpty(message = "Old password is required")
    @Size(min = 8, max = 40, message = "Old password must be between 8 and 40 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Old password must contain numbers and uppercase and lowercase Latin letters and symbols")
    private String oldPassword;

    @NotEmpty(message = "New password is required")
    @Size(min = 8, max = 40, message = "New password must be between 8 and 40 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "New password must contain numbers and uppercase and lowercase Latin letters and symbols")
    private String newPassword;
}
