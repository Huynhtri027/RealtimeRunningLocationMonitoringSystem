package assignment1.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Entity
@Table(name = "RUNNING_ANALYSIS")
public class RunningInfo {

    private String runningId;
    private int num;
    private double latitude;
    private double longitude;
    private double runningDistance;
    private double totalRunningTime;
    private int heartRate;
    private Date timestamp = new Date();
    private String healthWarningLevel;

    @Id
    @GeneratedValue
    private long id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "username", column = @Column(name = "userInfo_username")),
            @AttributeOverride(name = "address", column = @Column(name = "userInfo_address"))
    })
    private UserInfo userInfo;

    public RunningInfo() {
        this.runningId = "";
        this.healthWarningLevel = "";
    }

    //?
    @JsonCreator
    public RunningInfo(@JsonProperty("runningId") String runningId) {
        this.runningId = runningId;
        this.healthWarningLevel = "";
    }

    public RunningInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    // override getRunningId?
}
