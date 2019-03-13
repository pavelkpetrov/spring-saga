package hello;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Slf4j
public class TransactionIdInterceptor extends HandlerInterceptorAdapter {

    private static final String HEADER_TRANSACTIONID = "transactionId";


    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        String transactionId = req.getHeader(HEADER_TRANSACTIONID);
        log.debug("Got transactionId as {}", transactionId);
        if (Objects.nonNull(transactionId)) {
            TransactionIdContext.setTransactionId(transactionId);
            return true;
        } else {
            log.error("Could get transactionId header");
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            res.setContentType(MediaType.APPLICATION_JSON_VALUE);
            res.getWriter().write("{\"error\": \"Could get transactionId header!\"}");
            res.getWriter().flush();
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        TransactionIdContext.clear();
    }

}
