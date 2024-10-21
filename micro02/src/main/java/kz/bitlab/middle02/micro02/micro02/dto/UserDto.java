package kz.bitlab.middle02.micro02.micro02.dto;

import lombok.*;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class UserDto {
//
//    private Long id;
//    private String email;
//    private String fullName;
//}
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String userName;
}