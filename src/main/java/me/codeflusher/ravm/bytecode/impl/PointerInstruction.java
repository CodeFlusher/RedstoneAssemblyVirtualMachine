package me.codeflusher.ravm.bytecode.impl;

import me.codeflusher.ravm.bytecode.BytecodeExecutor;
import me.codeflusher.ravm.machine.RedstoneVMContext;

public class PointerInstruction implements BytecodeExecutor {
    @Override
    public void run(int[] arguments, RedstoneVMContext context) {
//        System.out.println(context.getMemoryCursor());
        context.registerPointer(context.getMemoryCursor() - 1, arguments[0]);
    }
}
