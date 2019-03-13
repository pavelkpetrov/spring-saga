package hello.model.dto;

import lombok.Data;

@Data
public class OrderGoodCompensatorDto {
    public final static String SUCCESS = "success";
    public final static String FAIL = "fail";

    private String type;
    private String transactionId;
}
