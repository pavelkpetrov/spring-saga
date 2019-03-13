package hello.service.clients.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class GoodOrderRequestDto implements Serializable {

    private Long goodId;
    private int count;
    private Long orderId;

}
