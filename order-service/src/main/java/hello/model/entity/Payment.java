package hello.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Data
@Where(clause="active=true")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Payment extends BaseEntity{
    @Column(name="price", nullable = false)
    private double price;
    @Column(name="currency", nullable = false)
    private String currency;
    @Column(name="good_count", nullable = false)
    private int goodCount;
    @Column(name="good_id", nullable = false)
    private Long goodId;

}
