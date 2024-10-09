package kz.bitlab.middle02.micro02.micro02.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String email;
    private String fullName;
}
