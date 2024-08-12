package me.codeflusher.ravm.bytecode.impl;

import me.codeflusher.ravm.bytecode.BytecodeExecutor;
import me.codeflusher.ravm.machine.RedstoneVMContext;

public class SetInstruction implements BytecodeExecutor {

    @Override
    public void run(int[] arguments, RedstoneVMContext context) {
        context.setMainIndex(arguments[0]);
    }
}
