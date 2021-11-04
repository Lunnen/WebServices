package assign3be.datahandlers.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthDTO {
    String username;
    String token;

    public AuthDTO(String username, String token) {
        this.username = username;
        this.token = token;
    }
}
