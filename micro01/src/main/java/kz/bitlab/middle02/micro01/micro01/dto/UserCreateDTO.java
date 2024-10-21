package kz.bitlab.middle02.micro01.micro01.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDTO {

    private String email;
    private String fistName;
    private String lastName;
    private String userName;
    private String password;

}
