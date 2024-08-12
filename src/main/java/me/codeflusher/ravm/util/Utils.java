package me.codeflusher.ravm.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Utils {

    public static char getFirstChar(String str){
        return str.charAt(0);
    }
    public static char getNthTopLeftChar(String str, int position){
        return str.charAt(position);
    }

}
