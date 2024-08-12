package me.codeflusher.ravm.machine.utils;

public class VMUtils {
    public static boolean isInstruction(int bytecode){
        return bytecode / 32768 >= 1;
    }

    public static int getInstructionID(int bytecode){
        return bytecode % 256;
    }
}
