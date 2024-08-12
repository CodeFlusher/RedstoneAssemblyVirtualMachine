package me.codeflusher.ravm.machine.impl;

import lombok.*;
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
import java.util.function.Predicate;


public class RedstoneBytecodeExecutor implements RedstoneVMContext, RedstoneVM {

    @Getter
    @Setter
    private int mainIndex = 0;

    @Getter
    @Setter
    private int memoryCursor;

    public final int memoryBoundaries;
    public final int staticMemoryBoundaries;

    @Getter
    private int[] memory;

    private List<Runnable> beforeRunErrands = new ArrayList<>();

    public RedstoneBytecodeExecutor(int memoryBoundaries, int staticMemoryBoundaries) {
        this.memoryBoundaries = memoryBoundaries;
        this.staticMemoryBoundaries = staticMemoryBoundaries;
        memory = new int[memoryBoundaries];
    }

    private List<Integer> outputMemoryIndexes = new ArrayList<>();
    private List<Integer> inputMemoryIndexes = new ArrayList<>();

    private void processor(Function<Integer, Boolean> booleanPredicate){
        beforeRunErrands.forEach(Runnable::run);
        int currendInstruction = memory[memoryCursor];
        int awaitsArguments = 0;
        int[] argsCollector = new int[0];
        int reqArgs = 0;
        Instructions instruction = null;
        while(booleanPredicate.apply(currendInstruction)){
//            if (VMUtils.isInstruction(currendInstruction))
//                System.out.println(Instructions.getValues()[VMUtils.getInstructionID(currendInstruction)].name());
//            else
//                System.out.println(currendInstruction);
            memoryCursor++;
            if(awaitsArguments > 0 && VMUtils.isInstruction(currendInstruction)){
                throw new ArgumentExpected("Argument expected after instruction");
            }
            if(awaitsArguments > 0){
                argsCollector[reqArgs - awaitsArguments] = currendInstruction;
                awaitsArguments --;
            }
            if (reqArgs > 0 && awaitsArguments == 0){
//                System.out.println("Running task! " + memoryCursor);
                instruction.getExecutor().run(argsCollector, this);
                reqArgs = 0;
//                currendInstruction = memory[memoryCursor];
//                continue;
//                reqArgs = 0;
//                awaitsArguments = 0;
            }
            if (VMUtils.isInstruction(currendInstruction)){
                instruction = Instructions.getValues()[VMUtils.getInstructionID(currendInstruction)];
                if (instruction.getInstructionArgumentLen() == 0){
                    if (instruction.getExecutor() == null){
                        currendInstruction = memory[memoryCursor];
                        continue;
                    }
                    instruction.getExecutor().run(null, this);
                }else{
                    argsCollector = new int[instruction.getInstructionArgumentLen()];
                    reqArgs = instruction.getInstructionArgumentLen();
                    awaitsArguments = instruction.getInstructionArgumentLen();
                }
            }
            currendInstruction = memory[memoryCursor];
        }
    }

    @Override
    public void run() throws VMException{
        processor(currendInstruction -> !(VMUtils.isInstruction(currendInstruction) && Instructions.getValues()[VMUtils.getInstructionID(currendInstruction)] == Instructions.END));
    }

    @Override
    public void complile(String code) throws VMException {
        var translatedCode = RedstoneAssemblyTranslator.translate(code);
        if (translatedCode.length > memoryBoundaries){
            throw new VMMemoryOutOfBounds("Translated code is larger than allocated memory for VM");
        }
        System.arraycopy(translatedCode, 0, memory, 0, translatedCode.length);
        processor(currentInstruction->!(VMUtils.isInstruction(currentInstruction) && Instructions.getValues()[VMUtils.getInstructionID(currentInstruction)] == Instructions.RUN));
    }

    @Override
    public void complieAndRun(String code) throws VMException{
        complile(code);
        run();
    }


    @Override
    public int getMemoryValue(int address) throws VMException {
        if (memoryBoundaries < address){
            throw new UnallocatedMemoryAccess("Program tried to ask for unallocated memory");
        }
        return memory[address];
    }

    @Override
    public void setMemoryValue(int address, int value) throws VMException{
        if (memoryBoundaries < address){
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
        switch (type){
            case INPUT -> inputMemoryIndexes.add(id,memoryAddress);
            case OUTPUT -> outputMemoryIndexes.add(id, memoryAddress);
            case null, default -> throw new IllegalArgumentException("Illegal Argument for binding registry provided");
        }
    }

    @Override
    public RedstoneVMContext getContext() {
        return this;
    }

    @Override
    public void setInputBindingBeforeRun(int id, int value) {
        beforeRunErrands.add(()->{
//            System.out.println("Input binding prepare");
            if (id >= inputMemoryIndexes.size()){
                throw new NoIORegistryFound("Cannot access %d input binding, only %d registered".formatted( id, inputMemoryIndexes.size()));
            }
            memory[inputMemoryIndexes.get(id)] = value;
        });
    }

    @Override
    public void setInputBinding(int id, int value) throws VMException {
        if (id >= inputMemoryIndexes.size()){
            throw new NoIORegistryFound("Cannot access %d input binding, only %d registered".formatted( id, inputMemoryIndexes.size()));
        }
        memory[inputMemoryIndexes.get(id)] = value;
    }

    @Override
    public int getOutputBinding(int id) {
        if (id >= outputMemoryIndexes.size()){
            throw new NoIORegistryFound("Cannot access %d output binding, only %d registered".formatted( id, outputMemoryIndexes.size()));
        }
        return memory[outputMemoryIndexes.get(id)];
    }
}
