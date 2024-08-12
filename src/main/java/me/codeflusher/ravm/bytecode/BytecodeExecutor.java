package me.codeflusher.ravm.bytecode;

import me.codeflusher.ravm.machine.RedstoneVMContext;

public interface BytecodeExecutor {
    void run(int[] arguments, RedstoneVMContext context);
}
