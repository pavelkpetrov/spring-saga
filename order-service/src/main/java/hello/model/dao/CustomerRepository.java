package hello.model.dao;

import hello.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, CrudRepository<Customer, Long> {

    @Modifying(clearAutomatically = true)
    @Query("Update Customer ot Set ot.active=true Where ot.transactionId=:transId")
    void successOrderCommit(@Param("transId") String transId);

}
