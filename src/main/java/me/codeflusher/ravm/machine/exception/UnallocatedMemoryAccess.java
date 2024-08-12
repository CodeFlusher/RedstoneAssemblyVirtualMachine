package me.codeflusher.ravm.machine.exception;

import me.codeflusher.ravm.data.IBasicException;

public class UnallocatedMemoryAccess extends VMException {
    public UnallocatedMemoryAccess(String message) {
        super(message, 0x01);
    }
}
