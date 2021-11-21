package com.task.core.controller.advices;

public class UnknownResultException extends RuntimeException {
    public UnknownResultException() {
        super("received task status is unknown");
    }
}
