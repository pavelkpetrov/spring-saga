package hello.model.dao;

import hello.model.entity.GoodStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface GoodStockRepository extends JpaRepository<GoodStock, Long>, CrudRepository<GoodStock, Long> {

}
