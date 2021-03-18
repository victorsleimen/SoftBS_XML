package com.softlynx.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class UtilNumber {

	final static Logger logger = LoggerFactory.getLogger(UtilNumber.class);
    public static String module = UtilNumber.class.getName();

    // ICU4J rule sets for the en_US locale. To add more rules, expand this string.
    // For reference, see the RbnfSampleRuleSets.java file distributed with ICU4J
    private static final String ruleSet_en_US =
        /*
         * These rules format a number in one of the two styles often used
         * on checks.  %dollars-and-hundredths formats cents as hundredths of
         * a dollar (23.40 comes out as "twenty-three and 40/100 dollars").
         * %dollars-and-cents formats in dollars and cents (23.40 comes out as
         * "twenty-three dollars and forty cents")
         */
        "%dollars-and-cents:\n"
        + "    x.0: << [and >%%cents>];\n"
        + "    0.x: >%%cents>;\n"
        + "    0: zero dollars; one dollar; =%%main= dollars;\n"
        + "%%main:\n"
        + "    zero; one; two; three; four; five; six; seven; eight; nine;\n"
        + "    ten; eleven; twelve; thirteen; fourteen; fifteen; sixteen;\n"
        + "        seventeen; eighteen; nineteen;\n"
        + "    20: twenty[->>];\n"
        + "    30: thirty[->>];\n"
        + "    40: forty[->>];\n"
        + "    50: fifty[->>];\n"
        + "    60: sixty[->>];\n"
        + "    70: seventy[->>];\n"
        + "    80: eighty[->>];\n"
        + "    90: ninety[->>];\n"
        + "    100: << hundred[ >>];\n"
        + "    1000: << thousand[ >>];\n"
        + "    1,000,000: << million[ >>];\n"
        + "    1,000,000,000: << billion[ >>];\n"
        + "    1,000,000,000,000: << trillion[ >>];\n"
        + "    1,000,000,000,000,000: =#,##0=;\n"
        + "%%cents:\n"
        + "    100: <%%main< cent[s];\n"
        + "%dollars-and-hundredths:\n"
        + "    x.0: <%%main< and >%%hundredths>/100;\n" // this used to end in 'dollars' but that should be added later
        + "%%hundredths:\n"
        + "    100: <00<;\n";

    // ICU4J rule sets for the th_TH locale. To add more rules, expand this string.
    // For reference, see the RbnfSampleRuleSets.java file distributed with ICU4J
    private static final String ruleSet_th_TH =
        /*
         * These rules format a number in one of the two styles often used
         * on checks.  %bahts-and-hundredths formats stangs as hundredths of
         * a baht (23.40 comes out as "twenty-three and 40/100 bahts").
         * %bahts-and-stangs formats in bahts and stangs (23.40 comes out as
         * "twenty-three bahts and forty stangs")
         */
        "%bahts-and-stangs:\n"
        + "    x.0: << [and >%%stangs>];\n"
        + "    0.x: >%%stangs>;\n"
        + "    0: zero bahts; one baht; =%%main= bahts;\n"
        + "%%main:\n"
        + "    zero; one; two; three; four; five; six; seven; eight; nine;\n"
        + "    ten; eleven; twelve; thirteen; fourteen; fifteen; sixteen;\n"
        + "        seventeen; eighteen; nineteen;\n"
        + "    20: twenty[->>];\n"
        + "    30: thirty[->>];\n"
        + "    40: forty[->>];\n"
        + "    50: fifty[->>];\n"
        + "    60: sixty[->>];\n"
        + "    70: seventy[->>];\n"
        + "    80: eighty[->>];\n"
        + "    90: ninety[->>];\n"
        + "    100: << hundred[ >>];\n"
        + "    1000: << thousand[ >>];\n"
        + "    1,000,000: << million[ >>];\n"
        + "    1,000,000,000: << billion[ >>];\n"
        + "    1,000,000,000,000: << trillion[ >>];\n"
        + "    1,000,000,000,000,000: =#,##0=;\n"
        + "%%stangs:\n"
        + "    100: <%%main< stang[s];\n"
        + "%bahts-and-hundredths:\n"
        + "    x.0: <%%main< and >%%hundredths>/100;\n" // this used to end in 'bahts' but that should be added later
        + "%%hundredths:\n"
        + "    100: <00<;\n";

    // hash map to store ICU4J rule sets keyed to Locale
    private static HashMap<Locale, String> rbnfRuleSets;
    static {
        rbnfRuleSets = new HashMap<Locale, String>();
        rbnfRuleSets.put(Locale.US, ruleSet_en_US);
        rbnfRuleSets.put(new Locale("th"), ruleSet_th_TH);
    }

    private UtilNumber() {}

    /**
     * Method to get the BigDecimal rounding mode int value from a string name.
     * @param   value - The name of the mode (e.g., "ROUND_HALF_UP")
     * @return  int - The int value of the mode (e.g, BigDecimal.ROUND_HALF_UP) or -1 if the input was bad.
     */
    public static RoundingMode roundingModeFromString(String value) {
        value = (value != null) ? value.trim() : null;
        if ("ROUND_HALF_UP".equals(value)) return RoundingMode.HALF_UP;
        else if ("ROUND_HALF_DOWN".equals(value)) return RoundingMode.HALF_DOWN;
        else if ("ROUND_HALF_EVEN".equals(value)) return RoundingMode.HALF_EVEN;
        else if ("ROUND_UP".equals(value)) return RoundingMode.UP;
        else if ("ROUND_DOWN".equals(value)) return RoundingMode.DOWN;
        else if ("ROUND_CEILING".equals(value)) return RoundingMode.CEILING; 
        else if ("ROUND_FLOOR".equals(value)) return RoundingMode.FLOOR;
        else if ("ROUND_UNNECCESSARY".equals(value)) return RoundingMode.UNNECESSARY;
        else return null;
    }

    /**
     * Method to turn a number such as "0.9853" into a nicely formatted percent, "98.53%".
     *
     * @param number    The number object to format
     * @param scale     How many places after the decimal to include
     * @return          The formatted string or "" if there were errors.
     */
    public static String toPercentString(Number number, int scale) {
        // convert to BigDecimal
        if (!(number instanceof BigDecimal)) {
            number = new BigDecimal(number.doubleValue());
        }

        // cast it so we can use BigDecimal methods
        BigDecimal bd = (BigDecimal) number;

        // multiply by 100 and set the scale
        bd = bd.multiply(new BigDecimal(100.0)).setScale(scale);

        return (bd.toString() + "%");
    }

    /**
     * A null-aware method for adding BigDecimal, but only for the right operand.
     *
     * @param left      The number to add to
     * @param right     The number being added; if null, then nothing will be added
     * @return          The result of the addition, or left if right is null.
     */
    public static BigDecimal safeAdd(BigDecimal left, BigDecimal right) {
        return right != null ? left.add(right) : left;
    }
}
