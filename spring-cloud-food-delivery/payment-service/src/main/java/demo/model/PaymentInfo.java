package demo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "PAYMENTS")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class PaymentInfo {
    @Id
    @GeneratedValue
    private Long paymentId;

    private Long orderId;
    private double totalPrice;

    private int cardNum;
    private int expirationMonth;
    private int expirationYear;
    private int securityCode;

    private Date paymentTimestamp;
    private boolean paymentComplete;
}
