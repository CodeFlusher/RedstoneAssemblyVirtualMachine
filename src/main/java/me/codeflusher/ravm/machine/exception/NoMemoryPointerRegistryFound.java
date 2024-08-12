package me.codeflusher.ravm.machine.exception;

public class NoMemoryPointerRegistryFound extends VMException {
    public NoMemoryPointerRegistryFound(String message) {
        super(message, 0x04);
    }
}
