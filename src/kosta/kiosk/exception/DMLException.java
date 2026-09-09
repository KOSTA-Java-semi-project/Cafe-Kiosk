package kosta.kiosk.exception;

public class DMLException extends Exception {

    public DMLException(String message) {
        super(message);
    }

    public DMLException(String message, Throwable cause) {
        super(message, cause);
    }
}