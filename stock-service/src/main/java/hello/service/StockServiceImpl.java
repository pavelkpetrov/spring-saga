package hello.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.TransactionIdContext;
import hello.model.dao.GoodOrderRepository;
import hello.model.dao.GoodRepository;
import hello.model.dto.GoodOrderRequestDto;
import hello.model.dto.OrderGoodCompensatorDto;
import hello.model.entity.Good;
import hello.model.entity.GoodOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Slf4j
@Transactional
public class StockServiceImpl implements StockService {

    static final String topicExchangeName = "order-create-exchange";
    static final String orderCreateScope = "order.create";

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private GoodRepository goodRepository;
    @Autowired
    private GoodOrderRepository goodOrderRepository;

    @Override
    public Good getGoodById(final Long goodId){

        Good good = goodRepository.findById(goodId)
                .orElseThrow(()-> new EntityNotFoundException("Can not found good by id " + goodId));
        log.info("Good by id {}: {}", goodId, good);
        return good;
    }

    @Override
    public GoodOrder orderGood(GoodOrderRequestDto goodOrder) throws JsonProcessingException {
        log.info("Got order passed to stock {}", goodOrder);
        Good good = goodRepository.findById(goodOrder.getGoodId())
                .orElseThrow(()-> new EntityNotFoundException("Can not found good by id " + goodOrder.getGoodId()));
        GoodOrder gO = new GoodOrder();
        gO.setOrderId(goodOrder.getOrderId());
        gO.setQuantity(goodOrder.getCount());
        gO.setGood(good);
        gO = goodOrderRepository.save(gO);
        orderGoodSuccess();
        return gO;
    }

    @Override
    public void orderGoodFailed() throws JsonProcessingException {
        orderGoodFail();
    }

    private void orderGoodSuccess() throws JsonProcessingException {
        log.info("Sending success compensation message...");
        OrderGoodCompensatorDto dto = new OrderGoodCompensatorDto();
        dto.setType(OrderGoodCompensatorDto.SUCCESS);
        dto.setTransactionId(TransactionIdContext.getTransactionId());
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(dto);
        rabbitTemplate.convertAndSend(topicExchangeName, orderCreateScope, jsonInString);
    }

    private void orderGoodFail() throws JsonProcessingException {
        log.info("Sending failed compensation message...");
        OrderGoodCompensatorDto dto = new OrderGoodCompensatorDto();
        dto.setType(OrderGoodCompensatorDto.FAIL);
        dto.setTransactionId(TransactionIdContext.getTransactionId());
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(dto);
        rabbitTemplate.convertAndSend(topicExchangeName, orderCreateScope, jsonInString);
    }

}
