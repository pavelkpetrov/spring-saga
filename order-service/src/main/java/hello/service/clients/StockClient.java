package hello.service.clients;

import feign.Request;
import hello.service.clients.model.GoodDto;
import hello.service.clients.model.GoodOrderDto;
import hello.service.clients.model.GoodOrderRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "http://stock-service", fallback = StockFallback.class)
public interface StockClient {

    @GetMapping(value = "/transactional/good/{goodId}", produces = "application/json")
    GoodDto getGoodById(@RequestHeader("transactionId") String transactionId, @PathVariable(name = "goodId") final Long goodId);

    @PostMapping(value = "/transactional/order", produces = "application/json")
    GoodOrderDto orderGood(@RequestHeader("transactionId") String transactionId, @RequestBody GoodOrderRequestDto goodOrder);

    @PostMapping(value = "/transactional/order/failed", produces = "application/json")
    GoodOrderDto orderGoodFailed(@RequestHeader("transactionId") String transactionId, @RequestBody GoodOrderRequestDto goodOrder);

    @Configuration
    class FeignConfiguration {

        private int connectTimeout = 10000;
        private int readTimeout = 10000;

        @Bean
        public Request.Options options() {
            return new Request.Options(connectTimeout, readTimeout);
        }

    }

}
