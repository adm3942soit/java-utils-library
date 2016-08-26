package com.utils.text;


import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * @author Nikolajs Arhipovs <nikolajs.arhipovs at gmail.com>
 */
public class Preconditions {

    public static void checkNotEmpty(final String reference, int code, String message) throws Exception{
        if (isNullOrEmpty(reference)) {
            throw new Exception( message);
        }
    }

    public static <T> void checkNotNull(final T reference, int code, String message) throws Exception{
        if (reference == null) {
            throw new Exception(message);
        }
    }

    public static void checkArgument(final boolean expression) throws Exception{
        checkArgument(expression, 400, "Illegal argument");
    }

    public static void checkArgument(final boolean expression, int code, String message) throws Exception{
        if (!expression) {
            throw new Exception(message);
        }
    }

    public static void checkState(final boolean expression) throws Exception{
        checkState(expression, 400, "Illegal state");
    }

    public static void checkState(final boolean expression, int code, String message) throws Exception{
        if (!expression) {
            throw new Exception(message);
        }
    }

    public static void checkAccess(final boolean expression) throws Exception{
        if (!expression) {
            throw new Exception("Forbidden");
        }
    }

}
