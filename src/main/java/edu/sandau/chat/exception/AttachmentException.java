package edu.sandau.chat.exception;

public class AttachmentException extends RuntimeException {

    public AttachmentException(String message) {
        super(message);
    }

    public AttachmentException(String message, Throwable cause) {
        super(message, cause);
    }
}
