package com.epam.task9.exception;

public class TunnelException  extends Exception{
    public TunnelException(String message) {
        super(message);
    }

    public TunnelException(String message, Throwable cause) {
        super(message, cause);
    }

    public TunnelException(Throwable cause) {
        super(cause);
    }
}
