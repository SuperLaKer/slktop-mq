package slktop.mq.order.globalexception;

public class OrderServiceException extends RuntimeException {

    public OrderServiceException() {
    }

    public OrderServiceException(String message) {
        super(message);
    }
}
