package hello.model.dao;

import hello.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>, CrudRepository<Payment, Long> {

    @Modifying(clearAutomatically = true)
    @Query("Update Payment ot Set ot.active=true Where ot.transactionId=:transId")
    void successOrderCommit(@Param("transId") String transId);

}
