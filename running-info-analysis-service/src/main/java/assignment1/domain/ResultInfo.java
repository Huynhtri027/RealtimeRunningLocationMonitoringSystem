package assignment1.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ResultInfo {
    private String runningId;
    private double totalRunningTime;
    private int heartRate;
    private long userId;
    private String username;
    private String userAddress;
    private String healthWarningLevel;

    public ResultInfo() {
    }

    public ResultInfo(String runningId, double totalRunningTime,
                      int heartRate, long userId,
                      String username, String userAddress,
                      String healthWarningLevel) {
        this.runningId = runningId;
        this.totalRunningTime = totalRunningTime;
        this.heartRate = heartRate;
        this.userId = userId;
        this.username = username;
        this.userAddress = userAddress;
        this.healthWarningLevel = healthWarningLevel;
    }

}
