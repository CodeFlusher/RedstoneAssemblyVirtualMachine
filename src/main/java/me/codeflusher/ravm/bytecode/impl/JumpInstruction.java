package me.codeflusher.ravm.bytecode.impl;

import me.codeflusher.ravm.bytecode.BytecodeExecutor;
import me.codeflusher.ravm.machine.RedstoneVMContext;

public class JumpInstruction implements BytecodeExecutor {
    @Override
    public void run(int[] arguments, RedstoneVMContext context) {
        System.out.printf("Jump to: %d%nCurrent memory point %d%n", context.getMemoryPointer(arguments[0]), context.getMemoryCursor());
        context.setMemoryCursor(context.getMemoryPointer(arguments[0]));
        System.out.printf("New Memory Point: %d%n",context.getMemoryCursor());
    }
}
