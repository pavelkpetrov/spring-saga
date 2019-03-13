package hello.model.dao;

import hello.model.entity.Delivery;
import hello.model.entity.OrderTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface DeliveryRepository extends JpaRepository<Delivery, Long>, CrudRepository<Delivery, Long> {

    @Modifying(clearAutomatically = true)
    @Query("Update Delivery ot Set ot.active=true Where ot.transactionId=:transId")
    void successOrderCommit(@Param("transId") String transId);

}
