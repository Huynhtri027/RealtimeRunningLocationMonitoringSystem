package demo.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO Data Transfer Object
 * 1. Entity ....... -> REST API -> Front-end
 *    Entity -> DTO class (service layer) -> REST API -> Client
 *    UserEntity (id, username, password, firstName, lastName) -> User (DTO) (id, username, password) -> REST API -> Client
 * 2. Client -> backend -> service layer -> repository -> persistence
 *    User (userId, username, address) (DTO) -> service layer (convert to appropriate entities/ dto)-> repository persist entities
 */
@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class CurrentPosition { // object to send to distribution service
    private String runningId;
    private Point location;
    private RunnerStatus runnerStatus;
    private Double speed;
    private Double heading;// forward direction in earth

    //@Nike Running
    private MedicalInfo medicalInfo;
}
