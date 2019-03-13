package hello;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;
import hello.model.entity.OrderTable;
import hello.service.OrderAgregator;
import hello.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
@EnableTransactionManagement
@EnableJpaRepositories
@EnableWebMvc
@EnableCircuitBreaker
@EnableFeignClients(
        basePackages = {"hello.service.clients"})
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}

@RestController
@Transactional
@Slf4j
class OrderController {

    @Autowired
    private OrderAgregator orderAgregator;

    @Value("${spring.application.name}")
    private String appName;

    @Value("${server.port}")
    private String port;

    @RequestMapping("/transactional/message")
    String getMessage() {
        log.info("Do getMessage");
        return "Application:" + appName + " port:" + port;
    }

    @RequestMapping("/transactional/allOrderTable")
    public List<OrderTable> getAllOrderTable() {
        log.info("Get All Order_Table data");
        return orderAgregator.getAllOrderTable();
    }

    @RequestMapping("/transactional/doOrder")
    public OrderTable doOrder() {
        log.info("Going to execute successfully order workflow");
        return orderAgregator.doOrderWorkflowSuccess();
    }

    @RequestMapping("/transactional/doOrderFailed")
    public OrderTable doOrderFailed() {
        log.info("Going to execute failed order workflow");
        return orderAgregator.doOrderWorkflowFailed();
    }

}


