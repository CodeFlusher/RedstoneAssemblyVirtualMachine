package me.codeflusher.ravm.bytecode.impl;

import me.codeflusher.ravm.bytecode.BytecodeExecutor;
import me.codeflusher.ravm.machine.RedstoneVMContext;

public class JumpInstruction implements BytecodeExecutor {
    @Override
    public void run(int[] arguments, RedstoneVMContext context) {
        System.out.println(context.getMemoryPointer(arguments[0]));
        context.setMemoryCursor(context.getMemoryPointer(arguments[0]));
    }
}
