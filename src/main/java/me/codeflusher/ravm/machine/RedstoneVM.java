package me.codeflusher.ravm.machine;

import me.codeflusher.ravm.machine.exception.VMException;

public interface RedstoneVM {
    void run() throws VMException;
    void complile(String code) throws VMException;
    void complieAndRun(String code) throws VMException;
    RedstoneVMContext getContext();

    void setInputBindingBeforeRun(int id, int value) throws VMException;
    void setInputBinding(int id, int value) throws VMException;

    int getOutputBinding(int id) throws VMException;
    int[] getMemory();
}
