package me.codeflusher.ravm.data;

import lombok.Getter;

public abstract class IBasicException extends RuntimeException {

    public IBasicException(String message) {
        super(message);
    }


}
