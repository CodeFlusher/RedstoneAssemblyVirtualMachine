package me.codeflusher.ravm.bytecode.impl;

import lombok.AllArgsConstructor;
import me.codeflusher.ravm.bytecode.BytecodeExecutor;
import me.codeflusher.ravm.data.impl.IORegistryTypes;
import me.codeflusher.ravm.machine.RedstoneVMContext;



@AllArgsConstructor
public class RegistryBytecodeExecutor implements BytecodeExecutor {

    public final IORegistryTypes registry;

    /**
     *
     * @param arguments Provided arguments for vm instruction. Must be array of one: MEMORY_ADRESS
     * @return operationResult
     * */

    @Override
    public void run(int[] arguments, RedstoneVMContext context) {
        switch (registry){
            case INPUT -> context.bindRegistry(arguments[0], context.boundInputRegistries(), IORegistryTypes.INPUT);
            case OUTPUT -> context.bindRegistry(arguments[0], context.boundOutputRegistries(), IORegistryTypes.OUTPUT);
        }

    }
}
