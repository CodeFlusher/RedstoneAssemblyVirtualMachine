import me.codeflusher.ravm.translator.RedstoneAssemblyTranslator;

import java.util.Arrays;

public class SplitTest {
    public static void main(String[] args){
        var test =("""
                inp 0x0000
                inp 0x0001
                out 0x0002
                run
                jil 0x0FFF 0x0015
                rd 0x0000
                add 0x0001
                mulv 0x00FA
                mov 0x0002
                jmp 0x0008
                end
                """);
        System.out.println(Arrays.toString(RedstoneAssemblyTranslator.translate(test)));
    }
}
