package hello.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import hello.model.dto.GoodOrderRequestDto;
import hello.model.entity.Good;
import hello.model.entity.GoodOrder;

public interface StockService {

    Good getGoodById(final Long goodId);
    GoodOrder orderGood(GoodOrderRequestDto goodOrder) throws JsonProcessingException;
    void orderGoodFailed() throws JsonProcessingException;

}
