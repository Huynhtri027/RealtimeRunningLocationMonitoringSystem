package demo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
//@Table(name = "PAYMENTS")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class PaymentInfo {
    private Long orderId;
    private double totalPrice;
    private int cardNum;
    private int expirationMonth;
    private int expirationYear;
    private int securityCode;
    private boolean paymentComplete;

//    @JsonCreator
//    public PaymentInfo(@JsonProperty("orderId") String userName,
//                       @JsonProperty("totalPrice") double totalPrice,
//                       @JsonProperty("cardNum") int cardNum,
//                       @JsonProperty("expirationMonth") int expirationMonth,
//                       @JsonProperty("expirationYear") int expirationYear,
//                       @JsonProperty("securityCode") int securityCode) {
//        this.totalPrice = totalPrice;
//        this.cardNum = cardNum;
//        this.expirationMonth = expirationMonth;
//        this.expirationYear = expirationYear;
//        this.securityCode = securityCode;
//    }
}
