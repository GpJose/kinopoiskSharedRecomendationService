package ru.kinoposisk.dto.auth;

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
public class AuthLoginDTO {

    @NotEmpty(message = "Login is required")
    @Size(min = 4, max = 20, message = "Login must be between 4 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Login must contain only letters and numbers")
    private String login;

    @NotEmpty(message = "Password is required")
    @Size(min = 8, max = 40, message = "Password must be between 8 and 40 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password must contain numbers and uppercase and lowercase Latin letters and symbols")
    private String password;
}
