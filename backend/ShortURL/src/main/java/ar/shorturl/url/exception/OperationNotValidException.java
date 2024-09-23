package ar.shorturl.url.exception;

import org.slf4j.helpers.MessageFormatter;

import java.util.function.Supplier;

public class OperationNotValidException extends RuntimeException {

    public final String messageId;

    public OperationNotValidException(String messageId, String message) {
        super(message);
        this.messageId = messageId;
    }

    public OperationNotValidException(String messageId, String message, Object ... params) {
        super(MessageFormatter.arrayFormat(message, params).getMessage());
        this.messageId = messageId;
    }

    public static final Supplier<OperationNotValidException> supplier(String messageId, String message) {
        return () -> new OperationNotValidException(messageId, message);
    }

    public static final Supplier<OperationNotValidException> supplier(String messageId, String message, Object ... params) {
        return () -> new OperationNotValidException(messageId, message, params);
    }

}

