package hello.model.entity;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Data
@Where(clause="active=true")
public class GoodOrder extends BaseEntity{
    @Column(name="order_id", nullable = false)
    private Long orderId;
    @Column(name="quantity", nullable = false)
    private int quantity;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "good_id", referencedColumnName = "id")
    private Good good;

}
