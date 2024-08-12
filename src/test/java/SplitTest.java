import me.codeflusher.ravm.translator.RedstoneAssemblyTranslator;

import java.util.Arrays;

public class SplitTest {
    public static void main(String[] args){
        var test =("""
                inp 0x0000
                inp 0x0001
                out 0x0002
                set 0x0001
                run
                ptr 0
                addv 0x0000
                mulv 0x0002
                mov 0x0002
                jis 0x0FFF 0x0000
                end
                """);
        System.out.println(Arrays.toString(RedstoneAssemblyTranslator.translate(test)));
    }
}
