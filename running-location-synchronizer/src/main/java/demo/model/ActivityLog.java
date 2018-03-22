package demo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class ActivityLog {

    private int activityId;
    private int activityParentId;
    private int calories;
    private String description;
    private double distance;
    private int duration;
    private boolean isFavorite;
    private int logId;
    private String name;
    private String startTime;
    private int steps;

}
