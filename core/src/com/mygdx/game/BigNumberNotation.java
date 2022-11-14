package com.mygdx.game;

import org.graalvm.compiler.hotspot.stubs.DivisionByZeroExceptionStub;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * https://playtaptales.com/really-big-numbers/
 **/
public class BigNumberNotation {
    private static final Map<Integer, String> numberNotationMap;

    static {
        numberNotationMap = new HashMap<>();
        numberNotationMap.put(0,  "");
        numberNotationMap.put(3,  "Thousand");
        numberNotationMap.put(6,  "Million");
        numberNotationMap.put(9,  "Billion");
        numberNotationMap.put(12, "Trillion");
        numberNotationMap.put(15, "Quadrillion");
        numberNotationMap.put(18, "Quintillion");
        numberNotationMap.put(21, "Sextillion");
        numberNotationMap.put(24, "Septillion");
        numberNotationMap.put(27, "Octillion");
        numberNotationMap.put(30, "Nonillion");
        numberNotationMap.put(33, "Decillion");
        numberNotationMap.put(36, "Undecillion");
        numberNotationMap.put(39, "Duodecillion");
        numberNotationMap.put(42, "Tredecillion");
        numberNotationMap.put(45, "Quattuordecillion");
        numberNotationMap.put(48, "Quindecillion");
        numberNotationMap.put(51, "Sexdecillion");
        numberNotationMap.put(54, "Septendecillion");
        numberNotationMap.put(57, "Octodecillion");
        numberNotationMap.put(60, "Novemdecillion");
        numberNotationMap.put(63, "Vigintillion");
    }

    public static CharSequence getPrintableValue(BigInteger value) {
        BigInteger tempValue = value;
        int digitCount = getDigitCount(value);

        Integer index = ((digitCount-1) / (int) 3) * 3;

        Double d =  Math.pow(10, index);
        BigInteger bigInteger = new BigInteger(String.valueOf(d.longValue()));
        tempValue = value.divide(bigInteger);



        BigInteger mod = value.mod(bigInteger);
        int digitCountResto = getDigitCount(mod);
        Double r = Math.pow(10, digitCountResto-3);
        BigInteger resto = BigInteger.ZERO;

        try {
            resto = mod.divide(new BigInteger(String.valueOf(r.longValue())));
        }catch (Exception e){
        }

        //DecimalFormat df = new DecimalFormat();
        //String valor = df.format(tempValue);

        String numberNotation = numberNotationMap.get(index);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0 ");
        stringBuilder.append(tempValue);
        stringBuilder.append(".");
        stringBuilder.append(resto.add(new BigInteger("1000")).toString().substring(1, 4));
        stringBuilder.append(" ");
        stringBuilder.append(numberNotation);

        return stringBuilder.toString();

        //return String.format("O %s.%03d %s", valor, resto, numberNotation);
    }


    private static int getDigitCount(BigInteger number) {
        double factor = Math.log(2) / Math.log(10);
        int digitCount = (int) (factor * number.bitLength() + 1);
        if (BigInteger.TEN.pow(digitCount - 1).compareTo(number) > 0) {
            return digitCount - 1;
        }
        return digitCount;
    }
}
