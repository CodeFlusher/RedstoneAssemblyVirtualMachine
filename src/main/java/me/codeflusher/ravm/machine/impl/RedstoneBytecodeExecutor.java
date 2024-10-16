package me.codeflusher.ravm.machine.impl;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import me.codeflusher.ravm.bytecode.instructions.Instructions;
import me.codeflusher.ravm.data.impl.IORegistryTypes;
import me.codeflusher.ravm.machine.RedstoneVM;
import me.codeflusher.ravm.machine.RedstoneVMContext;
import me.codeflusher.ravm.machine.exception.*;
import me.codeflusher.ravm.machine.utils.VMUtils;
import me.codeflusher.ravm.translator.RedstoneAssemblyTranslator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


public class RedstoneBytecodeExecutor implements RedstoneVMContext, RedstoneVM {

    @Getter
    @Setter
    private int mainIndex = 0;

    @Getter
    @Setter
    private int memoryCursor;

    public final int memoryBoundaries;
    public final int staticMemoryBoundaries;
    public final int maxStack;

    @Getter
    private int[] memory;
    @Getter
    private int[] staticMemory;

    @Getter
    List<Object> messages;

    private List<Runnable> beforeRunErrands = new ArrayList<>();

    public RedstoneBytecodeExecutor(int memoryBoundaries, int staticMemoryBoundaries, int maxStack) {
        this.memoryBoundaries = memoryBoundaries;
        this.staticMemoryBoundaries = staticMemoryBoundaries;
        this.maxStack = maxStack;
        memory = new int[memoryBoundaries];
        staticMemory = new int[staticMemoryBoundaries];
        messages = new ArrayList<>();
    }

    private List<Integer> outputMemoryIndexes = new ArrayList<>();
    private List<Integer> inputMemoryIndexes = new ArrayList<>();
    private List<Integer> memoryPointers = new ArrayList<>();

    private void processor(Function<Integer, Boolean> booleanPredicate) {
        preRegisterPointers();
        beforeRunErrands.forEach(Runnable::run);
        int currentInstruction = memory[memoryCursor];
        Instructions instruction = null;
        int stack = 0;
        while (booleanPredicate.apply(currentInstruction)) {
            stack++;

            if (stack > maxStack) {
                throw new VMStackOverflow("Stack overflow exception");
            }
            currentInstruction = memory[memoryCursor];
            memoryCursor ++;

            if (VMUtils.isInstruction(currentInstruction)) {
                instruction = Instructions.getValues()[VMUtils.getInstructionID(currentInstruction)];

                if (instruction.getExecutor() == null){
                    continue;
                }

                if (instruction.isCompileOnly()){
                    continue;
                }

                if (instruction.getInstructionArgumentLen() == 0){
                    instruction.getExecutor().run(null, this);
                    continue;
                }

                int[] args = new int[instruction.getInstructionArgumentLen()];

                for(int i = 0; i < instruction.getInstructionArgumentLen(); i ++){
                    var memVal = memory[memoryCursor + i];
                    if (VMUtils.isInstruction(memVal)){
                        throw new ArgumentExpected("Received instruction code as a argument. Memory Pointer: %d, Value: %d, Instruction ID: %d".formatted(memoryCursor+i, memVal, VMUtils.getInstructionID(memVal)));
                    }
                    args[i] = memVal;
                }

                instruction.getExecutor().run(args, this);
            }


        }
    }

    @Override
    public void run() throws VMException {
        processor(currendInstruction -> !(VMUtils.isInstruction(currendInstruction) && Instructions.getValues()[VMUtils.getInstructionID(currendInstruction)] == Instructions.END));
    }

    @Override
    public void complile(String code) throws VMException {
        var translatedCode = RedstoneAssemblyTranslator.translate(code);
        if (translatedCode.length > memoryBoundaries) {
            throw new VMMemoryOutOfBounds("Translated code is larger than allocated memory for VM");
        }
        System.arraycopy(translatedCode, 0, memory, 0, translatedCode.length);
        processor(currentInstruction -> !(VMUtils.isInstruction(currentInstruction) && Instructions.getValues()[VMUtils.getInstructionID(currentInstruction)] == Instructions.RUN));
    }

    @Override
    public void complieAndRun(String code) throws VMException {
        complile(code);
        run();
    }

    private void preRegisterPointers() {
        for (int i = 0; i < memory.length; i++) {
            if (memory[i] != Instructions.PTR.getInstructionCode()) {
                continue;
            }
            this.registerPointer(i, memory[i + 1]);
        }
        System.out.println(memoryPointers);
    }

    @Override
    public int getStaticMemoryValue(int address) {
        if (staticMemoryBoundaries < address) {
            throw new UnallocatedMemoryAccess("Program tried to ask for unallocated memory");
        }
        return memory[address];
    }

    @Override
    public void setStaticMemoryValue(int address, int value) {
        if (staticMemoryBoundaries < address) {
            throw new UnallocatedMemoryAccess("Program tried to ask for unallocated memory");
        }
        memory[address] = value;
    }

    @Override
    public int getMemoryValue(int address) throws VMException {
        if (memoryBoundaries < address) {
            throw new UnallocatedMemoryAccess("Program tried to ask for unallocated memory");
        }
        return memory[address];
    }

    @Override
    public void setMemoryValue(int address, int value) throws VMException {
        if (memoryBoundaries < address) {
            throw new UnallocatedMemoryAccess("Cannot set value out of allocated memory");
        }
        memory[address] = value;
    }

    @Override
    public int boundOutputRegistries() {
        return outputMemoryIndexes.size();
    }

    @Override
    public int boundInputRegistries() {
        return inputMemoryIndexes.size();
    }

    @Override
    public void bindRegistry(int memoryAddress, int id, @NonNull IORegistryTypes type) throws VMException, IllegalArgumentException {
        switch (type) {
            case INPUT -> inputMemoryIndexes.add(id, memoryAddress);
            case OUTPUT -> outputMemoryIndexes.add(id, memoryAddress);
            case null, default -> throw new IllegalArgumentException("Illegal Argument for binding registry provided");
        }
    }

    @Override
    public int getMemoryPointer(int id) throws VMException {
        if (memoryPointers.size() <= id) {
            throw new NoMemoryPointerRegistryFound("Requiered id (%d) is larger than registered pointers (%d)".formatted(id, memoryPointers.size()));
        }
        return memoryPointers.get(id);
    }

    @Override
    public void registerPointer(int memoryAddress, int id) throws VMException {
//        memoryPointers.add(id, memoryAddress);
        System.out.printf("Registering pointer: %d%nMemAddress: %d%n", id, memoryAddress);
        for(int i = memoryPointers.size(); i <= id; i++){
            memoryPointers.add(0);
        }
        memoryPointers.set(id, memoryAddress);
    }

    @Override
    public void pushMessage(Object object) {
        this.messages.add(object);
    }

    @Override
    public RedstoneVMContext getContext() {
        return this;
    }

    @Override
    public void setInputBindingBeforeRun(int id, int value) {
        beforeRunErrands.add(() -> {
//            System.out.println("Input binding prepare");
            if (id >= inputMemoryIndexes.size()) {
                throw new NoIORegistryFound("Cannot access %d input binding, only %d registered".formatted(id, inputMemoryIndexes.size()));
            }
            memory[inputMemoryIndexes.get(id)] = value;
        });
    }

    @Override
    public void setInputBinding(int id, int value) throws VMException {
        if (id >= inputMemoryIndexes.size()) {
            throw new NoIORegistryFound("Cannot access %d input binding, only %d registered".formatted(id, inputMemoryIndexes.size()));
        }
        memory[inputMemoryIndexes.get(id)] = value;
    }

    @Override
    public int getOutputBinding(int id) {
        if (id >= outputMemoryIndexes.size()) {
            throw new NoIORegistryFound("Cannot access %d output binding, only %d registered".formatted(id, outputMemoryIndexes.size()));
        }
        return memory[outputMemoryIndexes.get(id)];
    }
}
