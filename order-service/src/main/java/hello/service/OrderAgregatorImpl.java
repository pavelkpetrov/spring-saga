package hello.service;

import hello.TransactionIdContext;
import hello.model.entity.OrderTable;
import hello.service.clients.StockClient;
import hello.service.clients.model.GoodOrderDto;
import hello.service.clients.model.GoodOrderRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OrderAgregatorImpl implements OrderAgregator {

    @Autowired
    private StockClient stockClient;
    @Autowired
    private OrderService orderService;

    @Override
    public OrderTable doOrderWorkflowSuccess(){
        OrderTable orderTable  = orderService.doOrderSuccess();
        log.info("Create order {}", orderTable);
        GoodOrderRequestDto goodOrder = new GoodOrderRequestDto();
        goodOrder.setGoodId(orderTable.getPayment().getGoodId());
        goodOrder.setCount(orderTable.getPayment().getGoodCount());
        goodOrder.setOrderId(orderTable.getId());
        GoodOrderDto goDto = stockClient.orderGood(TransactionIdContext.getTransactionId(), goodOrder);
        log.info("Pass order to stock {}", orderTable);
        return orderTable;
    }

    @Override
    public OrderTable doOrderWorkflowFailed(){
        OrderTable orderTable  = orderService.doOrderSuccess();
        log.info("Create order {}", orderTable);
        GoodOrderRequestDto goodOrder = new GoodOrderRequestDto();
        goodOrder.setGoodId(orderTable.getPayment().getGoodId());
        goodOrder.setCount(orderTable.getPayment().getGoodCount());
        goodOrder.setOrderId(orderTable.getId());
        GoodOrderDto goDto = stockClient.orderGoodFailed(TransactionIdContext.getTransactionId(), goodOrder);
        log.info("Pass order to stock {}", orderTable);
        return orderTable;
    }

    @Override
    public List<OrderTable> getAllOrderTable(){
        return orderService.getAllOrderTable();
    }

}
