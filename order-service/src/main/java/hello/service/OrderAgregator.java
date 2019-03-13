package hello.service;

import hello.model.entity.OrderTable;

import java.util.List;

public interface OrderAgregator {
    OrderTable doOrderWorkflowSuccess();
    OrderTable doOrderWorkflowFailed();
    List<OrderTable> getAllOrderTable();
}
