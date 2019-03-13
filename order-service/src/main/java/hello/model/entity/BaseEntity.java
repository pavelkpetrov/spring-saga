package hello.model.entity;

import hello.TransactionIdContext;
import lombok.Data;

import javax.persistence.*;

@Data
@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;
    @Column(name="transaction_id", nullable = false)
    private String transactionId;
    @Column(name="active", nullable = false)
    private boolean active = false;

    @PrePersist
    public void setTransactionIdFromContext(){
        this.setTransactionId(TransactionIdContext.getTransactionId());
    }

}
