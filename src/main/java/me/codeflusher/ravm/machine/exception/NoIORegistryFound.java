package me.codeflusher.ravm.machine.exception;

public class NoIORegistryFound extends VMException {
    public NoIORegistryFound(String message) {
        super(message, 0x02);
    }
}
