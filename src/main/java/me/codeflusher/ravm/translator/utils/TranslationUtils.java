package me.codeflusher.ravm.translator.utils;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import me.codeflusher.ravm.data.impl.SupportedBases;
import me.codeflusher.ravm.util.Utils;

import java.util.Arrays;
import java.util.List;

@UtilityClass
public class TranslationUtils {

    @Getter
    private static final List<Character> digits = List.of('1','2','3','4','5','6','7','8','9','0');

    public static SupportedBases getBase(String str){
        char symb = 'a';
        try{
            symb = Utils.getNthTopLeftChar(str, 1);
        }catch (Exception e){
            return SupportedBases.DECIMAL;
        }
        char finalSymb = symb;
//        System.out.println(symb);
        var baseOptional = Arrays.stream(SupportedBases.values()).filter(it -> it.getPrefix() == finalSymb).findAny();
        return baseOptional.orElse(SupportedBases.DECIMAL);

    }

    public static boolean isValue(String string){
        return digits.contains((Character) Utils.getFirstChar(string));
    }
}
