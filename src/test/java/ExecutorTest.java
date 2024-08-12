import me.codeflusher.ravm.machine.impl.RedstoneBytecodeExecutor;
import me.codeflusher.ravm.translator.RedstoneAssemblyTranslator;

import java.util.Arrays;

public class ExecutorTest {
    public static void main(String[] args) {
        var test = """
                inp 0x0000
                inp 0x0001
                out 0x0002
                set 0x0001
                run
                addv 0x0000
                mulv 0x0002
                mov 0x0002
                jis 0x0FFF 0x0009
                end
                """;
        var vm = new RedstoneBytecodeExecutor(32, 0);
//        vm.complie(test);

        vm.complile(test);
        vm.setInputBinding(0, 2);
        vm.setInputBinding(1, 15);
        vm.run();
//        vm.getOutputBinding(0);
        System.out.println(vm.getMainIndex());
        System.out.println(Arrays.toString(vm.getMemory()));
    }
}
