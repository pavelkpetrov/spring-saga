package hello.model.dao;

import hello.model.entity.OrderTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderTable, Long>, CrudRepository<OrderTable, Long> {

    @Modifying(clearAutomatically = true)
    @Query("Update OrderTable ot set ot.active=true where ot.transactionId=:transId")
    void successOrderCommit(@Param("transId") String transId);

}
