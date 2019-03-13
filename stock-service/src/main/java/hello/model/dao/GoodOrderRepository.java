package hello.model.dao;

import hello.model.entity.Good;
import hello.model.entity.GoodOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface GoodOrderRepository extends JpaRepository<GoodOrder, Long>, CrudRepository<GoodOrder, Long> {

}
