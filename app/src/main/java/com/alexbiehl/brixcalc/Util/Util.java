package com.alexbiehl.brixcalc.Util;

/**
 * Created by alexb_000 on 7/26/2016.
 *
 * Utility class to calculate SG and Brix values
 */
public final class Util {

    /**
     * Terminal gravity used in these equations, will make this configurable
     */
    private static final double AVG_TERMINAL_GRAVITY = 1.015;

    /**
     * Takes a double Brix value and converts it to Specific Gravity.
     * Calculation source refer to:
     * http://byo.com/stories/item/1314-refractometers-and-mash-outs-mr-wizard
     *
     * @param brix The double Brix value
     * @return double The specifc gravity
     */
    public static double brixToSG(double brix) {
        return (( brix / ( 258.6 - (( brix / 258.2 ) * 227.1 ))) + 1 );
    }

    /**
     * Takes a double Specific Gravity value and converts it to Brix
     * Calculation source refer to:
     * https://en.wikipedia.org/wiki/Brix
     *
     * @param sg The double Specific Gravity value
     * @return double The Brix value
     */
    public static double sgToBrix(double sg) {
        return ((( 182.4601 * sg - 775.6821 ) * sg + 1262.7794 ) * sg - 669.5622 );
    }

    /**
     * Returns the approximate ABV given an OG and FG, using the standard equation
     *
     * @param og Double the Original Gravity
     * @param fg Double the Final Gravity
     * @return the approximated ABV
     */
    private static double approxAbvStdInternal(double og, double fg) {
        return (og - fg) * 131.25;
    }

    /**
     * Returns the approximate ABV given an OG, using the Standard equation.  Uses
     * an average FG.
     *
     * @param og double The Original Gravity
     * @return double The approximate ABV
     */
    public static double approxAbvStd(double og) {
        return approxAbvStdInternal(og, AVG_TERMINAL_GRAVITY);
    }

    /**
     * Returns the approximate ABV given an OG and FG, using the Standard equation
     * @param og The Original Gravity
     * @param fg The user provided final gravity
     * @return double The approximate ABV
     */
    public static double approxAbvStd(double og, double fg) {
        return approxAbvStdInternal(og, fg);
    }

    /**
     * Returns the approximate ABV given an OG, using the Alternate equation.  Potentially
     * more accurate at higher ABV
     *
     * @param og double the original gravity
     * @return the approximate ABV
     */
    public static double approxAbvAlt(double og) {
        return approxAbvAltInternal(og, AVG_TERMINAL_GRAVITY);
    }

    /**
     * Returns the approximate ABV given an OG and FG, using the Alternate equation.  Potentially
     * more accurate at higher ABV
     *
     * @param og double the original gravity
     * @param fg double the user-provided final gravity
     * @return the approximate ABV
     */
    public static double approxAbvAlt(double og, double fg) {
        return approxAbvAltInternal(og, fg);
    }

    /**
     * Returns the approximate ABV given an OG and FG, using the Alternate equation.  Potentially
     * more accurate at higher ABV
     *
     * @param og double the original gravity
     * @param fg double the final gravity
     * @return the approximate ABV
     */
    private static double approxAbvAltInternal(double og, double fg) {
        return (76.08 * (og - fg) / (1.775 - og)) * (fg / 0.794);
    }
}
