import me.codeflusher.ravm.bytecode.utils.Decompiler;
import me.codeflusher.ravm.machine.impl.RedstoneBytecodeExecutor;
import me.codeflusher.ravm.translator.RedstoneAssemblyTranslator;

import java.util.Arrays;

public class ExecutorTest {
    public static void main(String[] args) {
        var test = """
                inp 0x0000
                inp 0x0001
                out 0x0002
                set 1984
                mov 0x000F
                set 0x0002
                run
                addv 0x0000
                ptr 0
                mulv 0x00FF
                mov 0x0002
                jis 0x0FFF 0
                jmp 2
                ptr 1
                end
                ptr 2
                mov 0x0005
                set 1984
                mov 0x0006
                rd 0x0005
                jmp 1
                """;
        var vm = new RedstoneBytecodeExecutor(80, 0, 128);
//        vm.complie(test);

        vm.complile(test);
        vm.setInputBinding(0, 2);
        vm.setInputBinding(1, 15);
        vm.run();
//        vm.getOutputBinding(0);
        System.out.println(vm.getMainIndex());
        System.out.println(Arrays.toString(vm.getMemory()));
        System.out.println(Arrays.toString(Decompiler.decompile(vm.getMemory())));
    }
}
