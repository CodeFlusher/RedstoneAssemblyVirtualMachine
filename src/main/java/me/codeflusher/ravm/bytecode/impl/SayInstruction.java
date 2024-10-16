package me.codeflusher.ravm.bytecode.impl;

import me.codeflusher.ravm.bytecode.BytecodeExecutor;
import me.codeflusher.ravm.machine.RedstoneVMContext;

/**
 * @author Egor (CodeFlusher)
 * @since 10/16/2024 at 8:06 PM
 * For RedstoneAssemblyVirtualMachine project.
 */
public class SayInstruction implements BytecodeExecutor {
    @Override
    public void run(int[] arguments, RedstoneVMContext context) {
        context.pushMessage("[%d]: %d".formatted(context.getMemoryCursor(), arguments[0]));
    }
}
