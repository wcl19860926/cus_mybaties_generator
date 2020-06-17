package com.study.generator.util;

public final class EqualsUtil {
    private EqualsUtil() {}

    public static boolean areEqual(boolean b1, boolean b2) {
        return b1 == b2;
    }

    public static boolean areEqual(char c1, char c2) {
        return c1 == c2;
    }

    public static boolean areEqual(long l1, long l2) {
        /*
         * Implementation Note Note that byte, short, and int are handled by
         * this method, through implicit conversion.
         */
        return l1 == l2;
    }

    public static boolean areEqual(float f1, float f2) {
        return Float.floatToIntBits(f1) == Float.floatToIntBits(f2);
    }

    public static boolean areEqual(double d1, double d2) {
        return Double.doubleToLongBits(d1) == Double.doubleToLongBits(d2);
    }

    /**
     * Possibly-null object field.
     * 
     * <p>Includes type-safe enumerations and collections, but does not include arrays. See class comment.
     *
     * @param o1
     *            the first object
     * @param o2
     *            the second object
     * @return true, if the objects are equals (meaning that the equals() method returns true)
     */
    public static boolean areEqual(Object o1, Object o2) {
        return o1 == null ? o2 == null : o1.equals(o2);
    }
}
