package me.codeflusher.ravm.bytecode.impl;

import me.codeflusher.ravm.bytecode.BytecodeExecutor;
import me.codeflusher.ravm.machine.RedstoneVMContext;
import me.codeflusher.ravm.machine.exception.VMException;

public class ThrowInstruction implements BytecodeExecutor {
    @Override
    public void run(int[] arguments, RedstoneVMContext context) {
        throw new VMException("Bytecode exception thrown", arguments[0], true);
    }
}
