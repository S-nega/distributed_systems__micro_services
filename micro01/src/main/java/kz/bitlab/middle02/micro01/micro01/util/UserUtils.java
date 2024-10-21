package kz.bitlab.middle02.micro01.micro01.util;

import kz.bitlab.middle02.micro01.micro01.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@Slf4j
public final class UserUtils {

    public static Jwt getCurrentUserJwt() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken) {
            return ((JwtAuthenticationToken) authentication).getToken();
        }
        log.warn("Couldn't get user data");
        return null;
    }

    public static String getCurrentUsername() {
        Jwt jwt = getCurrentUserJwt();
        if (jwt != null) {
            return jwt.getClaimAsString("preferred_username");
        }
        log.warn("Couldn't get user data");
        return null;
    }

    public static UserDto getCurrentUser() {
        Jwt jwt = getCurrentUserJwt();
        if (jwt != null) {
            return UserDto.builder()
                    .email(jwt.getClaimAsString("email"))
                    .userName(jwt.getClaimAsString("preferred_username"))
                    .firstName(jwt.getClaimAsString("given_name"))
                    .lastName(jwt.getClaimAsString("family_name"))
                    .build();
        }
        log.warn("Couldn't get user data");
        return null;
    }
}
