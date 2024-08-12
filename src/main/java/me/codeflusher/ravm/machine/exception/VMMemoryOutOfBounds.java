package me.codeflusher.ravm.machine.exception;

public class VMMemoryOutOfBounds extends VMException {
    public VMMemoryOutOfBounds(String message) {
        super(message, 0x03);
    }
}
