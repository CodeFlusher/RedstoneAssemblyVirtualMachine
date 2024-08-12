package me.codeflusher.ravm.bytecode.impl;

import me.codeflusher.ravm.bytecode.BytecodeExecutor;
import me.codeflusher.ravm.machine.RedstoneVMContext;

public class MoveInstruction implements BytecodeExecutor {

    @Override
    public void run(int[] arguments, RedstoneVMContext context) {
        context.setMemoryValue(arguments[0], context.getMainIndex());
    }
}
