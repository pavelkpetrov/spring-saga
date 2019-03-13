package hello;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import javax.servlet.http.HttpServletRequest;

import java.util.UUID;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.*;

public class TransactionIdPreFilter extends ZuulFilter {

    private static final String HEADER_TRANSACTION_ID = "transactionId";

    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 1; // run before PreDecoration
    }

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        return !ctx.containsKey(FORWARD_TO_KEY) // a filter has already forwarded
                && !ctx.containsKey(HEADER_TRANSACTION_ID); // a filter has already determined serviceId
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        String uuid = UUID.randomUUID().toString();
        ctx.put(HEADER_TRANSACTION_ID, uuid);
        ctx.getResponse().setHeader(HEADER_TRANSACTION_ID, uuid);
        ctx.addZuulRequestHeader(HEADER_TRANSACTION_ID, uuid);
        return null;
    }
}