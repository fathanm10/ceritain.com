package id.ac.ui.cs.advprog.finalprojectc1.core.registration;

import lombok.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {


    private String fullname;

    @NotBlank(message = "Name cannot be empty!")
    private  String name;

    @NotBlank(message = "Enter your email")
    @Email(message = "email is invalid")
    private  String email;

    private  String password;

}
