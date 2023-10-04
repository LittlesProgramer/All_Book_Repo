package my.app.com.MyProject04.domain;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

@Entity
@Data
@Table(name = "Order_Taco")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date placeAt;

    @NotBlank(message = "delivery name cannot be blank")
    private String deliveryName;
    @NotBlank(message = "delivery street cannot be blank")
    private String deliveryStreet;
    @NotBlank(message = "delivery city cannot be blank")
    private String deliveryCity;
    @NotBlank(message = "delivery state cannot be blank")
    private String deliveryState;
    @NotBlank(message = "delivery zip cannot be blank")
    private String deliveryZip;

    @CreditCardNumber(message = "Please insert correct Credit Card Number")
    private String ccNumber;
    @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$",message="Must be formatted MM/YY")
    private String ccExpiration;
    @Digits(integer = 3,fraction = 0,message = "Please insert correct cvv number")
    private String ccCVV;

    @ManyToMany(targetEntity = Taco.class)
    private List<Taco> tacos = new ArrayList<>();

    public void addDesignTacoToOrder(Taco taco){
        this.tacos.add(taco);
    }

    @PrePersist
    public void setPlaceAtDate(){
        this.placeAt = new Date();
    }
}
