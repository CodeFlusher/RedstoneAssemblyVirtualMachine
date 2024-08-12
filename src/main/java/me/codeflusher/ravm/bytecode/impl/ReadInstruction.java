package me.codeflusher.ravm.bytecode.impl;

import me.codeflusher.ravm.bytecode.BytecodeExecutor;
import me.codeflusher.ravm.machine.RedstoneVMContext;

public class ReadInstruction implements BytecodeExecutor {
    @Override
    public void run(int[] arguments, RedstoneVMContext context) {
//        System.out.println("Reading value mem address: %d, value: %d".formatted(arguments[0], context.getMemoryValue(arguments[0])));
        context.setMainIndex(context.getMemoryValue(arguments[0]));
    }
}
