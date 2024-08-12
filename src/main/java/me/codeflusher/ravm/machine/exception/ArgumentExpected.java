package me.codeflusher.ravm.machine.exception;

public class ArgumentExpected extends VMException {
    public ArgumentExpected(String message) {
        super(message, 0x05);
    }
}
