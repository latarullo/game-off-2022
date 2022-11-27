package com.mygdx.game.util;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;


/**
 * https://playtaptales.com/really-big-numbers/
 **/
public class BigNumberNotation {
    private static final Map<Integer, NumberNotation> numberNotationMap;

    static {
        numberNotationMap = new HashMap<>();
        numberNotationMap.put(0,  new NumberNotation("",""));
        numberNotationMap.put(3,  new NumberNotation("Thousand", "K"));
        numberNotationMap.put(6,  new NumberNotation("Million", "M"));
        numberNotationMap.put(9,  new NumberNotation("Billion", "B"));
        numberNotationMap.put(12, new NumberNotation("Trillion", "T"));
        numberNotationMap.put(15, new NumberNotation("Quadrillion", "q"));
        numberNotationMap.put(18, new NumberNotation("Quintillion", "Q"));
        numberNotationMap.put(21, new NumberNotation("Sextillion", "s"));
        numberNotationMap.put(24, new NumberNotation("Septillion", "S"));
        numberNotationMap.put(27, new NumberNotation("Octillion", "O"));
        numberNotationMap.put(30, new NumberNotation("Nonillion", "N"));
        numberNotationMap.put(33, new NumberNotation("Decillion", "D"));
        numberNotationMap.put(36, new NumberNotation("Undecillion", "UD"));
        numberNotationMap.put(39, new NumberNotation("Duodecillion", "DD"));
        numberNotationMap.put(42, new NumberNotation("Tredecillion", "TD"));
        numberNotationMap.put(45, new NumberNotation("Quattuordecillion", "qD"));
        numberNotationMap.put(48, new NumberNotation("Quindecillion", "QD"));
        numberNotationMap.put(51, new NumberNotation("Sexdecillion", "sD"));
        numberNotationMap.put(54, new NumberNotation("Septendecillion", "SD"));
        numberNotationMap.put(57, new NumberNotation("Octodecillion", "OD"));
        numberNotationMap.put(60, new NumberNotation("Novemdecillion", "ND"));
        numberNotationMap.put(63, new NumberNotation("Vigintillion", "VD"));
    }

    public static String getPrintableValue(BigInteger value) {
        return getPrintableValue(value, false);
    }

    public static String getPrintableValue(BigInteger value, boolean showDecimal) {
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

        String numberNotation = numberNotationMap.get(index).shortNotation;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(tempValue);
        if (showDecimal) {
            stringBuilder.append(".");
            stringBuilder.append(resto.add(new BigInteger("1000")).toString().substring(1, 4));
        }
        stringBuilder.append(" ");
        stringBuilder.append(numberNotation);

        return stringBuilder.toString();
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

class NumberNotation {
    String notation;
    String shortNotation;

    NumberNotation(String notation, String shortNotation){
        this.notation = notation;
        this.shortNotation = shortNotation;
    }
}
