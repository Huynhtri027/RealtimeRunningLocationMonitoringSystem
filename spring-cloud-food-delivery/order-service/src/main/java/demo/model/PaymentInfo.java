package demo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class PaymentInfo {
    private Long orderId;
    private double totalPrice;
    private int cardNum;
    private int expirationMonth;
    private int expirationYear;
    private int securityCode;
    private boolean paymentComplete;
}
