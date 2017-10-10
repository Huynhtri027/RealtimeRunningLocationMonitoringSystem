package assignment1.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class UserInfo {
    private String username;
    private String address;

    public UserInfo() {
    }

    public UserInfo(String username, String address) {
        this.username = username;
        this.address = address;
    }
}
