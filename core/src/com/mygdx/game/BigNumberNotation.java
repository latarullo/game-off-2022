package com.mygdx.game;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Big Number Notations Table, taken from: https://clickerheroes.fandom.com/wiki/Big_Number_Notation
 *
	1K	1,000	100,000 - 1e8	3	One Thousand
	1M	1,000K	1e8 - 1e11		6	One Million
	1B	1,000M	1e11 - 1e14		9	One Billion
	1T	1,000B	1e14 - 1e17		12	One Trillion
	1q	1,000T	1e17 - 1e20		15	One Quadrillion
	1Q	1,000q	1e20 - 1e23		18	One Quintillion
	1s	1,000Q	1e23 ~ 1e26		21	One Sextillion
	1S	1,000s	1e26 - 1e29		24	One Septillion
	1O	1,000S	1e29 - 1e32		27	One Octillion
	1N	1,000O	1e32 - 1e35		30	One Nonillion
	1d	1,000N	1e35 - 1e38		33	One Decillion
	1U	1,000d	1e38 - 1e41		36	One Undecillion
	1D	1,000U	1e41 - 1e44		39	One Duodecillion
	1!	1,000D	1e44 - 1e47		42	One Tredecillion
	1@	1,000!	1e47 - 1e50		45	One Quattuordecillion
	1#	1,000@	1e50 - 1e53		48	One Quindecillion
	1$	1,000#	1e53 - 1e56		51	One Sexdecillion
	1%	1,000$	1e56 - 1e59		54	One Septendecillion
	1^	1,000%	1e59 - 1e62		57	One Octodecillion
	1&	1,000^	1e62 - 1e65		60	One Novemdecillion
	1*	1,000&	1e65 - 1e68		63	One Vigintillion
 **/
public class BigNumberNotation {

    static BigInteger MIL = BigInteger.valueOf(1000);

    private static final Map<Integer, String> numberNotationMap;

    static {
        numberNotationMap = new HashMap<>();
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

        int dividedByThousandCounter = 0;

        while (tempValue.divide(BigInteger.TEN).compareTo(BigInteger.ZERO) > 0) {
            tempValue = tempValue.divide(BigInteger.TEN);
            dividedByThousandCounter ++ ;
        }
        int index = (dividedByThousandCounter/ (int) 3) * 3;
        String numberNotation = numberNotationMap.get(index);

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        String valor = df.format(tempValue);

        return String.format("O %s %s", valor, numberNotation);
    }
}
