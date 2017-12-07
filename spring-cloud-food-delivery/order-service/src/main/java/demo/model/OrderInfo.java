package demo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "ORDERS")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class OrderInfo {
    @Id
    @GeneratedValue
    private Long orderId;
    private String userName;
    private String restaurantName;

    @Embedded
    private List<OrderedItem> orderedItems;

    private String note;
    private String deliveryAddress;
    private double totalPrice;
    private int deliveryTime;
    private Date orderedTimestamp;

    private int cardNum;
    private int expirationMonth;
    private int expirationYear;
    private int securityCode;

    private Long paymentId;
    private Date paymentTimestamp;
    private boolean paymentComplete;

    @JsonCreator
    public OrderInfo(@JsonProperty("userName") String userName,
                     @JsonProperty("restaurantName") String restaurantName,
                     @JsonProperty("orderedItem") List<OrderedItem> orderedItems,
                     @JsonProperty("note") String note,
                     @JsonProperty("deliveryAddress") String deliveryAddress,
                     @JsonProperty("cardNum") int cardNum,
                     @JsonProperty("expirationMonth") int expirationMonth,
                     @JsonProperty("expirationYear") int expirationYear,
                     @JsonProperty("securityCode") int securityCode) {
        this.userName = userName;
        this.orderedItems = orderedItems;
        this.note = note;
        this.deliveryAddress = deliveryAddress;
        for (OrderedItem orderedItem : orderedItems) {
            this.totalPrice += orderedItem.getItemPrice() * orderedItem.getItemQuantity();
        }

        this.deliveryTime = generateDeliveryTime();
        this.orderedTimestamp = new Date();
        this.cardNum = cardNum;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
        this.securityCode = securityCode;
        this.paymentComplete = false;
    }

    private int generateDeliveryTime() {
        Random random = new Random();
        return this.deliveryTime = random.nextInt(55) + 5;
    }
}
