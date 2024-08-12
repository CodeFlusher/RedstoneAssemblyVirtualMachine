package me.codeflusher.ravm.translator;

import lombok.experimental.UtilityClass;
import me.codeflusher.ravm.bytecode.instructions.Instructions;
import me.codeflusher.ravm.data.IBasicException;
import me.codeflusher.ravm.translator.utils.TranslationUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collector;

@UtilityClass
public class RedstoneAssemblyTranslator {

    public static int[] translate(String assemblyCode) throws IBasicException {
        var bytecodes = new ArrayList<Integer>();
        var memoryByteIndexes = new ArrayList<Integer>();

        assemblyCode = assemblyCode.strip();

        var instructionsList = assemblyCode.split("\n| ");

//        Arrays.stream(Instructions.getValues()).forEach(System.out::println);

        Instructions instruction = null;

        for(int i = 0; i < instructionsList.length; i++){
            int data;
            var currentSymbol = instructionsList[i];

            if (currentSymbol.isBlank() || currentSymbol.isEmpty()){
                continue;
            }
            int counter = 0;
//            System.out.println("Current processing symbol: " + currentSymbol);
            if (TranslationUtils.isValue(currentSymbol) ) {
                if (instruction != null && !instruction.getValueOperation().apply(counter)) {
                    memoryByteIndexes.add(i);
                }
                var base = TranslationUtils.getBase(currentSymbol);
                if (currentSymbol.length()<=2){
                    data = Integer.parseInt(currentSymbol);
                }else{
//                    System.out.println(base.getBase());
                    data = Integer.parseInt(currentSymbol.substring(2).toLowerCase(), base.getBase());
                }

                bytecodes.add(data);
                counter++;
                continue;
            }

            instruction = Instructions.valueOf(currentSymbol.toUpperCase());
            counter = 0;
            bytecodes.add(instruction.getInstructionCode());
        }

//        System.out.println(bytecodes);
//        System.out.println(memoryByteIndexes);

        memoryByteIndexes.forEach(index -> bytecodes.set(index, bytecodes.get(index)+ bytecodes.size()));

        return bytecodes.stream().mapToInt(i->i).toArray();

    }

}
