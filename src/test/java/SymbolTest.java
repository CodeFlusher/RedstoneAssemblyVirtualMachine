import me.codeflusher.ravm.translator.utils.TranslationUtils;
import me.codeflusher.ravm.util.Utils;

import java.util.Arrays;

public class SymbolTest {
    public static void main(String[] args){
        Arrays.stream("""
                inp 0x0000
                """.split(" ")).forEach(it -> System.out.println(TranslationUtils.isValue(it)));
        System.out.println(Utils.getFirstChar("abcd"));
    }
}
