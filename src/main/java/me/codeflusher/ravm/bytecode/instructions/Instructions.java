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
    RUN( 0,0,i->false, null, false),
    END( 0,1,i->false, null, false),
    INP(1, 2,i->false, new RegistryBytecodeExecutor(IORegistryTypes.INPUT), false),
    OUT(1, 3,i->false, new RegistryBytecodeExecutor(IORegistryTypes.OUTPUT),false),
    MOV(1, 4,i->false, new MoveInstruction(),false),
    RD(1, 5,i->false, new ReadInstruction(),false),
    SET(1, 6,i->true, new SetInstruction(),false),
    ADD(1,7,i->false,new AddInstruction(),false),
    ADDV(1,8,i->true, new AddValueInstruction(),false),
    SUB(1,9,i->false, new SubstractInstruction(),false),
    SUBV(1,10,i->true,new SubstractValueInstruction(),false),
    MUL(1,11,i->false, new MultiplyInstruction(),false),
    MULV(1,12,i->true,new MultiplyValueInstruction(),false),
    DIV(1,13,i->false, new DivideInstruction(),false),
    DIVV(1,14,i->true, new DivideValueInstruction(),false),
    MOD(1,15,i->false,new ModInstruction(),false),
    MODV(1,16,i->true,new ModValueInstruction(),false),
    PTR(1,17, i->true, new PointerInstruction(),true),
    SAY(1, 18, i->true, new SayInstruction(),false),
    JMP(1, 19, i->true, new JumpInstruction(),false),
    JIS(2, 20, integer -> integer == 0, new ComparativeJumpInstruction(-1),false),
    JIL(2, 21, integer -> integer == 0, new ComparativeJumpInstruction(1),false),
    JIE(2, 22, integer -> integer == 0, new ComparativeJumpInstruction(0),false),
    ;
    private static int instructions = 0;

    private static Instructions[] values = null;
    private final int instructionArgs;
    private final int instructionID;
    @Getter
    private final Function<Integer, Boolean> valueOperation;
    private final BytecodeExecutor executor;

    @Getter
    private final boolean compileOnly;

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
