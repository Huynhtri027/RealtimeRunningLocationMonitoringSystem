package demo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class PaymentInfo {
    private Long orderId;
    private double totalPrice;

    private int cardNum;
    private int expirationMonth;
    private int expirationYear;
    private int securityCode;

    private Long paymentId;
    private Date paymentTimestamp;
    private boolean paymentComplete;
}
