package hello.service.clients.model;


import lombok.Data;

@Data
public class GoodOrderDto {

    private Long orderId;
    private int quantity;
    private GoodDto good;



}
