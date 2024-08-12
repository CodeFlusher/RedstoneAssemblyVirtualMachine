package me.codeflusher.ravm.bytecode.utils;

import me.codeflusher.ravm.bytecode.instructions.Instructions;
import me.codeflusher.ravm.machine.utils.VMUtils;

public class Decompiler {
    public static String[] decompile(int[] memory){
        String[] array = new String[memory.length];
        for (int i = 0; i < memory.length; i++) {
            if (VMUtils.isInstruction(memory[i])){
                array[i] = Instructions.getValues()[VMUtils.getInstructionID(memory[i])].name();
            }
            else{
                array[i] = String.valueOf(memory[i]);
            }
        }
        return array;
    }
}
