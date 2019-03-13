package hello.service;

import hello.TransactionIdContext;
import hello.model.dao.CustomerRepository;
import hello.model.dao.DeliveryRepository;
import hello.model.dao.OrderRepository;
import hello.model.dao.PaymentRepository;
import hello.service.clients.model.GoodDto;
import hello.model.entity.Customer;
import hello.model.entity.Delivery;
import hello.model.entity.OrderTable;
import hello.model.entity.Payment;
import hello.service.clients.StockClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    public final static long SMITH_CUSTOMER_ID = 1;
    public final static long GARFANKEL_CUSTOMER_ID = 2;
    public final static long BOOK_GOOD_ID = 1;
    public final static long PHONE_GOOD_ID = 2;

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private DeliveryRepository deliveryRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private StockClient stockClient;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public OrderTable doOrderSuccess(){
        GoodDto goodDto = stockClient.getGoodById(TransactionIdContext.getTransactionId(), BOOK_GOOD_ID);

        if (goodDto == null) {
            log.error("Can not find good by id:" + BOOK_GOOD_ID);
            throw new EntityNotFoundException("Can not found good by id: " + BOOK_GOOD_ID);
        }
        Customer customer = customerRepository.findById(SMITH_CUSTOMER_ID).get();

        Delivery delivery = new Delivery();
        delivery.setDeliveryAddress(customer.getAddress());
        delivery = deliveryRepository.save(delivery);

        Payment payment = new Payment();
        int count = 2;
        payment.setPrice(goodDto.getPrice() * count);
        payment.setCurrency("EUR");
        payment.setGoodCount(count);
        payment.setGoodId(BOOK_GOOD_ID);
        payment = paymentRepository.save(payment);

        OrderTable order = new OrderTable();
        order.setOrderNo(UUID.randomUUID().toString());
        order.setDelivery(delivery);
        order.setCustomer(customer);
        order.setPayment(payment);
        order = orderRepository.save(order);

        return order;
    }

    @Override
    public List<OrderTable> getAllOrderTable() {
        return orderRepository.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void orderSuccessCompensation(String transactionId){
        orderRepository.successOrderCommit(transactionId);
        paymentRepository.successOrderCommit(transactionId);
        deliveryRepository.successOrderCommit(transactionId);
        customerRepository.successOrderCommit(transactionId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void orderFailedCompensation(String transactionId){
    }


}
