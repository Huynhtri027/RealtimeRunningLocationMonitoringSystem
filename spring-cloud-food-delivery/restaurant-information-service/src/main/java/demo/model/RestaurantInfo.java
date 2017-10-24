package demo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "RESTAURANTS")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class RestaurantInfo {
    @Id
    @GeneratedValue
    private long id;
    private String restaurantName;

    @Embedded
    private MenuInfo menuInfoList;

    @JsonCreator
    public RestaurantInfo(@JsonProperty("restaurantName") String restaurantName,
                          @JsonProperty("menuInfo") MenuInfo menuInfoList) {
        this.restaurantName = restaurantName;
        this.menuInfoList = menuInfoList;
    }
}
