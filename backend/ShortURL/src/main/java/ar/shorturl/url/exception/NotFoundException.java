package ar.shorturl.url.exception;

import org.slf4j.helpers.MessageFormatter;

import java.util.function.Supplier;

public class NotFoundException extends RuntimeException {

    public final String messageId;

    public NotFoundException(String messageId, String message) {
        super(message);
        this.messageId = messageId;
    }

    public NotFoundException(String messageId, String message, Object ... params) {
        super(MessageFormatter.arrayFormat(message, params).getMessage());
        this.messageId = messageId;
    }

    public static final Supplier<NotFoundException> supplier(String messageId, String message) {
        return () -> new NotFoundException(messageId, message);
    }

    public static final Supplier<NotFoundException> supplier(String messageId, String message, Object ... params) {
        return () -> new NotFoundException(messageId, message, params);
    }

}

