package me.codeflusher.ravm.bytecode.impl;

import me.codeflusher.ravm.bytecode.BytecodeExecutor;
import me.codeflusher.ravm.machine.RedstoneVMContext;

public class AddInstruction implements BytecodeExecutor {
    @Override
    public void run(int[] arguments, RedstoneVMContext context) {
//        System.out.println("Add instruction main buffer: %d, Add value mem address %d, Value: %d".formatted(context.getMainIndex(), arguments[0], context.getMemoryValue(arguments[0])));
        context.setMainIndex(context.getMainIndex() + context.getMemoryValue(arguments[0]));
    }
}
