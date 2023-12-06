package projects.brewers.amateur.com.brixcalc.Util

/**
 * Created by alexb_000 on 7/26/2016.
 *
 * Utility class to calculate SG and Brix values
 */
object Util {
    /**
     * Terminal gravity used in these equations, will make this configurable
     */
    private const val AVG_TERMINAL_GRAVITY = 1.015

    /**
     * Takes a double Brix value and converts it to Specific Gravity.
     * Calculation source refer to:
     * http://byo.com/stories/item/1314-refractometers-and-mash-outs-mr-wizard
     *
     * @param brix The double Brix value
     * @return double The specifc gravity
     */
    @JvmStatic
    fun brixToSG(brix: Double): Double {
        return brix / (258.6 - brix / 258.2 * 227.1) + 1
    }

    /**
     * Takes a double Specific Gravity value and converts it to Brix
     * Calculation source refer to:
     * https://en.wikipedia.org/wiki/Brix
     *
     * @param sg The double Specific Gravity value
     * @return double The Brix value
     */
    @JvmStatic
    fun sgToBrix(sg: Double): Double {
        return ((182.4601 * sg - 775.6821) * sg + 1262.7794) * sg - 669.5622
    }

    /**
     * Returns the approximate ABV given an OG and FG, using the standard equation
     *
     * @param og Double the Original Gravity
     * @param fg Double the Final Gravity
     * @return the approximated ABV
     */
    private fun approxAbvStdInternal(og: Double, fg: Double): Double {
        return (og - fg) * 131.25
    }

    /**
     * Returns the approximate ABV given an OG, using the Standard equation.  Uses
     * an average FG.
     *
     * @param og double The Original Gravity
     * @return double The approximate ABV
     */
    fun approxAbvStd(og: Double): Double {
        return approxAbvStdInternal(og, AVG_TERMINAL_GRAVITY)
    }

    /**
     * Returns the approximate ABV given an OG and FG, using the Standard equation
     * @param og The Original Gravity
     * @param fg The user provided final gravity
     * @return double The approximate ABV
     */
    fun approxAbvStd(og: Double, fg: Double): Double {
        return approxAbvStdInternal(og, fg)
    }

    /**
     * Returns the approximate ABV given an OG, using the Alternate equation.  Potentially
     * more accurate at higher ABV
     *
     * @param og double the original gravity
     * @return the approximate ABV
     */
    @JvmStatic
    fun approxAbvAlt(og: Double): Double {
        return approxAbvAltInternal(og, AVG_TERMINAL_GRAVITY)
    }

    /**
     * Returns the approximate ABV given an OG and FG, using the Alternate equation.  Potentially
     * more accurate at higher ABV
     *
     * @param og double the original gravity
     * @param fg double the user-provided final gravity
     * @return the approximate ABV
     */
    fun approxAbvAlt(og: Double, fg: Double): Double {
        return approxAbvAltInternal(og, fg)
    }

    /**
     * Returns the approximate ABV given an OG and FG, using the Alternate equation.  Potentially
     * more accurate at higher ABV
     *
     * @param og double the original gravity
     * @param fg double the final gravity
     * @return the approximate ABV
     */
    private fun approxAbvAltInternal(og: Double, fg: Double): Double {
        return 76.08 * (og - fg) / (1.775 - og) * (fg / 0.794)
    }
}
