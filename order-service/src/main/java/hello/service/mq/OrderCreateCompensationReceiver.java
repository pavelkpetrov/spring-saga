package hello.service.mq;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.model.dto.OrderGoodCompensatorDto;
import hello.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class OrderCreateCompensationReceiver {

    @Autowired
    private OrderService orderService;

    public void receiveMessage(String message)  {
        log.info("Received <" + message + ">");
        try {
            ObjectMapper mapper = new ObjectMapper();
            OrderGoodCompensatorDto compensationDto =
                    mapper.readValue(message, OrderGoodCompensatorDto.class);
            if (OrderGoodCompensatorDto.FAIL.equals(compensationDto.getType())) {
                orderService.orderFailedCompensation(compensationDto.getTransactionId());
            } else {
                orderService.orderSuccessCompensation(compensationDto.getTransactionId());
            }
        } catch(Exception ex){
            log.error(ex.getMessage(), ex);
        }
    }


}
