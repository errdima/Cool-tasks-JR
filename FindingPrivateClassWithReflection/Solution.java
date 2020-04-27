package com.javarush.task.task36.task3602;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/*
Найти класс по описанию
*/
public class Solution {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        System.out.println(getExpectedClass());
    }

    public static Class getExpectedClass() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class[] clazzes = Collections.class.getDeclaredClasses();
        for (Class o: clazzes) {
            if (List.class.isAssignableFrom(o)){
               // System.out.println(o);
                int classModifiers = o.getModifiers();
                if (Modifier.isPrivate(classModifiers) && Modifier.isStatic(classModifiers)){
                    //System.out.println(o);
                    Constructor[] constructors = o.getDeclaredConstructors();
                    for (Constructor con: constructors){
                        if (con.getParameterCount() == 0){
                            //System.out.println(o);
                            con.setAccessible(true);
                            Method method = o.getDeclaredMethod("get", int.class);
                            method.setAccessible(true);
                            try{
                                method.invoke(con.newInstance(), 0);
                            } catch (InvocationTargetException e) {
                                if (e.getCause().toString().contains("IndexOutOfBoundsException")) {
                                    return o;
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}
