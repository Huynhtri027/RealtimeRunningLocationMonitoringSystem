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

    @Embedded
    private OrderedItem orderedItem;

    private String note;
    private String deliveryAddress;
    private double totalPrice;
    private Date orderedTimestamp;
    private Date paymentTimestamp;

    private int cardNum;
    private int expirationMonth;
    private int expirationYear;
    private int securityCode;

    private boolean paymentComplete = false;

    @JsonCreator
    public OrderInfo(@JsonProperty("userName") String userName,
                     @JsonProperty("orderedItem") OrderedItem orderedItem,
                     @JsonProperty("note") String note,
                     @JsonProperty("deliveryAddress") String deliveryAddress,
                     @JsonProperty("cardNum") int cardNum,
                     @JsonProperty("expirationMonth") int expirationMonth,
                     @JsonProperty("expirationYear") int expirationYear,
                     @JsonProperty("securityCode") int securityCode) {
        this.userName = userName;
        this.orderedItem = orderedItem;
        this.note = note;
        this.deliveryAddress = deliveryAddress;
        this.totalPrice = orderedItem.getItemPrice();
        this.cardNum = cardNum;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
        this.securityCode = securityCode;
    }
}
