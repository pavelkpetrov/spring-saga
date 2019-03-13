package hello.service.clients;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import hello.TransactionIdContext;
import hello.TransactionIdInterceptor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class TransactionFeignRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return;
        }
        HttpServletRequest request = requestAttributes.getRequest();
        if (request == null) {
            return;
        }
        requestTemplate.header(TransactionIdInterceptor.HEADER_TRANSACTION_ID, TransactionIdContext.getTransactionId());
    }
}