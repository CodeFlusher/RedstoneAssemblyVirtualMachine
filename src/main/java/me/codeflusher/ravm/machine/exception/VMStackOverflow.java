package me.codeflusher.ravm.machine.exception;

public class VMStackOverflow extends VMException {
    public VMStackOverflow(String message) {
        super(message, 0x06);
    }
}
