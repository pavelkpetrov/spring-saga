package hello.model.entity;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Data
@Where(clause="active=true")
public class Good extends BaseEntity{
    @Column(name="name", nullable = false)
    private String name;
    @Column(name="price", nullable = false)
    private double price;

}
