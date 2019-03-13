package hello.service.clients;

import hello.service.clients.model.GoodDto;
import hello.service.clients.model.GoodOrderDto;
import hello.service.clients.model.GoodOrderRequestDto;
import org.springframework.stereotype.Component;

@Component
public class StockFallback implements StockClient{
    @Override
    public GoodDto getGoodById(String transactionId, Long goodId) {
        return null;
    }

    @Override
    public GoodOrderDto orderGood(String transactionId, GoodOrderRequestDto goodOrder) {
        return null;
    }

    @Override
    public GoodOrderDto orderGoodFailed(String transactionId, GoodOrderRequestDto goodOrder) {
        return null;
    }

}
