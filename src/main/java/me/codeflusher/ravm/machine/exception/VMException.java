package me.codeflusher.ravm.machine.exception;

import lombok.Getter;

public class VMException extends RuntimeException {
    @Getter
    private boolean isUserThrown;
    @Getter
    private int code;
    public VMException(String message, int code) {
        super(message);
        this.code = code;
        this.isUserThrown = false;
    }

    public VMException(String message, int code, boolean isUserThrown) {
      super(message);
      this.isUserThrown = isUserThrown;
      this.code = code;
    }
}
