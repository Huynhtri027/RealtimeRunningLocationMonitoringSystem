package demo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Embeddable
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class OrderedItem {

    private String itemName;
    private int itemQuantity;
    private double itemPrice;

    @JsonCreator
    public OrderedItem(@JsonProperty("itemName") String itemName,
                       @JsonProperty("itemQuantity") int itemQuantity,
                       @JsonProperty("itemPrice") double itemPrice) {
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemPrice = itemPrice;
    }
}
