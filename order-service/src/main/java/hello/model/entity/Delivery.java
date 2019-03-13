package hello.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Data
@Where(clause="active=true")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Delivery extends BaseEntity{
    @Column(name="delivery_address", nullable = false)
    private String deliveryAddress;

}
