package global.logic.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpRequestDTO {

    private String name;
    @NotBlank(message = "The email is mandatory")
    @Pattern(regexp = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$",
            message = "The email is not a valid email")
    private String email;
    @NotBlank(message = "The password is mandatory")
    @Pattern(regexp = "^(?=(?:[^A-Z]*[A-Z]){1,1})(?=(?:\\D*\\d){2,})[a-zA-Z0-9]{8,12}$",
            message = "The password is not a valid. It must have at least one capital letter, two numbers, minimum of 8 and maximum of 12 characters")
    private String password;
    private Set<PhoneDTO> phones;
}
