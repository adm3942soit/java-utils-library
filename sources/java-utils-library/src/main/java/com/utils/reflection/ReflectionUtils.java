package com.utils.reflection;

import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by oksdud on 24.03.2017.
 */
@Slf4j
public class ReflectionUtils {

    private static final Unsafe unsafe;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object getValueStaticField(Class clazz, String fieldName) {
        Field myField = null;
        try {
            myField = clazz.getDeclaredField(fieldName);
            myField.setAccessible(true);
            Object object = myField.get(null);
            return object;
        } catch (Exception e) {
            log.error("Got exception " + e.toString());
        }
        return null;
    }

    public synchronized static boolean runMethod(Class clazz, String methodName, Long argument, Object object) {
        Method[] privateMethods = clazz.getDeclaredMethods();
        if (privateMethods.length == 0) {
            log.error("There are no methods in class " + clazz.getName());
            return false;
        }
        for (int i = 0; i < privateMethods.length; i++) {
            if (methodName.equals(privateMethods[i].getName())) {
                privateMethods[i].setAccessible(true);
                try {
                    if(argument!=null) privateMethods[i].invoke(clazz.newInstance(), argument, object);//->only for Long argument of method
                    else  privateMethods[i].invoke(clazz.newInstance(), object);
                    return true;
                } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                    log.error("Got exception " + e.toString());
                }
            }
        }
        return false;
    }

    public static void setFinalStatic(Class clazz, String fieldName, Object newValue) throws NoSuchFieldException, IllegalAccessException {
        Field fieldToUpdate = clazz.getDeclaredField(fieldName);
        final Object base = unsafe.staticFieldBase(fieldToUpdate);
        final long offset = unsafe.staticFieldOffset(fieldToUpdate);
        unsafe.putObject(base, offset, newValue);
    }
}
