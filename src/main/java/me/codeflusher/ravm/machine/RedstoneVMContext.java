package me.codeflusher.ravm.machine;

import me.codeflusher.ravm.data.impl.IORegistryTypes;
import me.codeflusher.ravm.machine.exception.UnallocatedMemoryAccess;
import me.codeflusher.ravm.machine.exception.VMException;

public interface RedstoneVMContext {

    int getMainIndex();
    void setMainIndex(int value);

    int getMemoryValue(int address) throws VMException;
    void setMemoryValue(int address, int value) throws VMException;

    int boundOutputRegistries();
    int boundInputRegistries();
    void bindRegistry(int memoryAddress, int id, IORegistryTypes type) throws VMException;

    void setMemoryCursor(int address);
}
