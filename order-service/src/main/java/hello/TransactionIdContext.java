package hello;

public class TransactionIdContext {

    private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();

    public static void setTransactionId(String tenantId) {
        CONTEXT.set(tenantId);
    }

    public static String getTransactionId() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }


}
