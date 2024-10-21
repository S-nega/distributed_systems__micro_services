package kz.bitlab.middle02.micro01.micro01.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTokenDto {
    private String token;
    private String refreshToken;
}
