package kz.bitlab.middle02.micro01.micro01.client;

import kz.bitlab.middle02.micro01.micro01.dto.UserCreateDTO;
import kz.bitlab.middle02.micro01.micro01.dto.UserLoginDto;
import kz.bitlab.middle02.micro01.micro01.dto.UserTokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.core.Response;
import java.nio.channels.MulticastChannel;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeyCloakClient {

    private final Keycloak keycloak;
    private final RestTemplate restTemplate;

    @Value("${keycloak.url}")
    private String url;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    public UserRepresentation createUser(UserCreateDTO userCreateDTO){// I need to take it from user service
        UserRepresentation newUser = new UserRepresentation();
        newUser.setEmail(userCreateDTO.getEmail());
        newUser.setEmailVerified(true);
        newUser.setUsername(userCreateDTO.getUserName());
        newUser.setFirstName(userCreateDTO.getFirstName());
        newUser.setLastName(userCreateDTO.getLastName());
        newUser.setEnabled(true);

        CredentialRepresentation credentials = new CredentialRepresentation();
        credentials.setType(CredentialRepresentation.PASSWORD);
        credentials.setValue(userCreateDTO.getPassword());
        credentials.setTemporary(false);

        newUser.setCredentials(List.of(credentials));
        Response response = keycloak
                .realm(realm)
                .users()
                .create(newUser);

        if (response.getStatus() != HttpStatus.CREATED.value()){
            log.error("Error on creating user, status : {}", response.getStatus());
            throw new RuntimeException("Failed to create user in keycloak" + response.getStatus());
        }

        List<UserRepresentation> userList = keycloak.realm(realm)// search
                .users()
                .search(userCreateDTO.getUserName());

        return userList.get(0);// search
    }

    public UserTokenDto signIn(UserLoginDto userLoginDto){

        String tokenEndpoint = url + "/realms/" + realm + "/protocol/openid-connect/token";

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "password");
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("username", userLoginDto.getUsername());
        formData.add("password", userLoginDto.getPassword());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        ResponseEntity<Map> response = restTemplate
                .postForEntity(tokenEndpoint, new HttpEntity<>(formData, headers), Map.class);

        Map<String, Object> responseBody = response.getBody();

        if(!response.getStatusCode().is2xxSuccessful() || responseBody == null){
            log.error("Error on signing in with user {}", userLoginDto.getUsername());
            throw new RuntimeException("Error on  signing in with user, status = " + response.getStatusCode());
        }

        UserTokenDto userTokenDto = new UserTokenDto();
        userTokenDto.setToken((String) responseBody.get("access_token"));
        userTokenDto.setRefreshToken((String) responseBody.get("refresh_token"));

        return userTokenDto;
    }

    public void changePassword(String username, String newPassword) {
        List<UserRepresentation> users = keycloak
                .realm(realm)
                .users()
                .search(username);

        if (users.isEmpty()) {
            log.error("User not found with username: {}", username);
            throw new RuntimeException("User not found with username: " + username);
        }

        UserRepresentation user = users.get(0);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(newPassword);
        credential.setTemporary(false);

        keycloak
                .realm(realm)
                .users()
                .get(user.getId())
                .resetPassword(credential);

        log.info("Password changed successfully for username: {}", username);
    }


}
