package hello.model.dao;

import hello.model.entity.Good;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface GoodRepository extends JpaRepository<Good, Long>, CrudRepository<Good, Long> {

}
