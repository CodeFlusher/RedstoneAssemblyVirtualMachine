package me.codeflusher.ravm.bytecode;

import java.util.function.Function;

public interface IBytecode {
    String getInstructionName();
    int getInstructionArgumentLen();
    int getInstructionCode();
    Function<Integer, Boolean> getValueOperation();
    BytecodeExecutor getExecutor();
}
