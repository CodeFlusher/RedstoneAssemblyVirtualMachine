package me.codeflusher.ravm.bytecode.instructions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.codeflusher.ravm.bytecode.BytecodeExecutor;
import me.codeflusher.ravm.bytecode.IBytecode;
import me.codeflusher.ravm.bytecode.impl.*;
import me.codeflusher.ravm.data.impl.IORegistryTypes;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;

@AllArgsConstructor
public enum Instructions implements IBytecode {
    RUN( 0,0,i->false, null),
    END( 0,1,i->false, null),
    INP(1, 2,i->false, new RegistryBytecodeExecutor(IORegistryTypes.INPUT)),
    OUT(1, 3,i->false, new RegistryBytecodeExecutor(IORegistryTypes.OUTPUT)),
    MOV(1, 4,i->false, new MoveInstruction()),
    RD(1, 5,i->false, new ReadInstruction()),
    SET(1, 6,i->true, new SetInstruction()),

    ADD(1,7,i->false,new AddInstruction()),
    ADDV(1,8,i->true, new AddValueInstruction()),
    SUB(1,9,i->false, new SubstractInstruction()),
    SUBV(1,10,i->true,new SubstractValueInstruction()),
    MUL(1,11,i->false, new MultiplyInstruction()),
    MULV(1,12,i->true,new MultiplyValueInstruction()),
    DIV(1,13,i->false, new DivideInstruction()),
    DIVV(1,14,i->true, new DivideValueInstruction()),
    MOD(1,15,i->false,new ModInstruction()),
    MODV(1,16,i->true,new ModValueInstruction()),
    JMP(1, 17, i->true, new JumpInstruction()),
    JIS(2, 18, integer -> integer == 0, new ComparativeJumpInstruction(-1)),
    JIL(2, 19, integer -> integer == 0, new ComparativeJumpInstruction(1)),
    JIE(2, 20, integer -> integer == 0, new ComparativeJumpInstruction(0)),

    ;
    private static Instructions[] values = null;
    private final int instructionArgs;
    private final int instructionID;
    @Getter
    private final Function<Integer, Boolean> valueOperation;
    private final BytecodeExecutor executor;

    @Override
    public String getInstructionName() {
        return this.name().toLowerCase();
    }

    @Override
    public int getInstructionArgumentLen() {
        return instructionArgs ;
    }

    @Override
    public int getInstructionCode() {
        return 32768 + 256 * instructionArgs + instructionID;

    }

    @Override
    public BytecodeExecutor getExecutor() {
        return executor;
    }

    public static Instructions[] getValues() {
        if (values == null){
            values = new Instructions[values().length];
            var currentValues = values();
            Arrays.sort(currentValues, Comparator.comparingInt(Instructions::getInstructionCode));
            values = currentValues;
        }
        return values;
    }
}
