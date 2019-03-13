package hello;

import com.fasterxml.jackson.core.JsonProcessingException;
import hello.model.dto.GoodOrderRequestDto;
import hello.model.entity.Good;
import hello.model.entity.GoodOrder;
import hello.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@EnableDiscoveryClient
@EnableTransactionManagement
@EnableJpaRepositories
@Slf4j
public class StockApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockApplication.class, args);
    }
}

@RestController
@Transactional
@Slf4j
class StockController {

    @Value("${spring.application.name}")
    private String appName;

    @Autowired
    private StockService stockService;

    @RequestMapping("/transactional/message")
    String getMessage() {
        return "Application:" + appName;
    }

    @GetMapping(value = "/transactional/good/{goodId}", produces = "application/json")
    public Good getGoodById(@PathVariable(name = "goodId") final Long goodId) {
        log.info("get good by id {}", goodId);
        return stockService.getGoodById(goodId);
    }

    @PostMapping(value = "/transactional/order", produces = "application/json")
    public GoodOrder orderGood(@RequestBody GoodOrderRequestDto goodOrder) throws JsonProcessingException {
        log.info("Order passed to stock {}", goodOrder);
        return stockService.orderGood(goodOrder);
    }

    @PostMapping(value = "/transactional/order/failed", produces = "application/json")
    public GoodOrder orderGoodFailed(@RequestBody GoodOrderRequestDto goodOrder) throws JsonProcessingException {
        stockService.orderGoodFailed();
        return null;
    }

}