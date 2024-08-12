package me.codeflusher.ravm.bytecode.impl;

import me.codeflusher.ravm.bytecode.BytecodeExecutor;
import me.codeflusher.ravm.machine.RedstoneVMContext;

public class JumpInstruction implements BytecodeExecutor {
    @Override
    public void run(int[] arguments, RedstoneVMContext context) {
        context.setMemoryCursor(arguments[0]);
    }
}
