package me.codeflusher.ravm.bytecode.impl;

import lombok.AllArgsConstructor;
import me.codeflusher.ravm.bytecode.BytecodeExecutor;
import me.codeflusher.ravm.machine.RedstoneVMContext;
@AllArgsConstructor
public class ComparativeJumpInstruction implements BytecodeExecutor {

    public final int result;
    @Override
    public void run(int[] arguments, RedstoneVMContext context) {
        if (Integer.compare(context.getMainIndex(), arguments[0]) == result){
//            System.out.println("Comparative jump main register: %d, Than : %d, Jump to: %d".formatted(context.getMainIndex(), arguments[0], arguments[1]));
            context.setMemoryCursor(context.getMemoryPointer(arguments[1]));
        }
    }
}
