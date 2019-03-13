package hello.service;

import hello.model.entity.OrderTable;

import java.util.List;

public interface OrderService {

    OrderTable doOrderSuccess();
    List<OrderTable> getAllOrderTable();
    void orderSuccessCompensation(String transactionId);
    void orderFailedCompensation(String transactionId);
}
