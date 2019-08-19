package com.example.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 
 * @author Rafael Vieira Rangel
 * @author Bruno Saraiva
 */
public class StringUtils {
    /***************
    ** Constants
    ***************/
    private static final int SUBSTRING_GET_LENGTH = 3;
    
    private static final int SUBSTRING_IS_LENGTH = 2;
    
    private static final String TO_STRING_SEPARATOR = ";";
    
    private static final String TO_STRING_EXCEPTION = "getClass";
    
    private static final String MASK_DATE = "dd/MM/yyyy";
    
    
    /***************
    ** Methods
    ***************/
    private StringUtils() {
        // EMPTY
    }
    
    public static String toString(final Class<?> objClass, final Object objToString) {
        final StringBuffer sBuffer = new StringBuffer(objClass.getName());
        sBuffer.append(" {");
        
        final Method[] classMethods = objClass.getMethods();
        Method tempMethod = null;
        
        for(int x = 0; x < classMethods.length; x++) {
            tempMethod = classMethods[x];
            
            final boolean getMethod = tempMethod.getName().startsWith("get");
            final boolean isMethod = tempMethod.getName().startsWith("is");
            final boolean isException = tempMethod.getName().startsWith(TO_STRING_EXCEPTION);
            
            if((tempMethod.getParameterTypes().length == 0) && (getMethod || isMethod)
                    && (!isException)) {
                
                try {
                    final Object tempObject = tempMethod.invoke(objToString, new Object[] {});
                    
                    if(getMethod) {
                        if(tempObject != null) {
                            if(tempObject.getClass().equals(GregorianCalendar.class)) {
                                sBuffer.append(tempMethod.getName().substring(SUBSTRING_GET_LENGTH))
                                        .append(" = ")
                                        .append(formatDateToString((GregorianCalendar) tempObject));
                            }
                            else {
                                sBuffer.append(tempMethod.getName().substring(SUBSTRING_GET_LENGTH))
                                        .append(" = ")
                                        .append(tempObject);
                            }
                        }
                    }
                    else {
                        sBuffer.append(tempMethod.getName().substring(SUBSTRING_IS_LENGTH))
                                .append(" = ")
                                .append(tempObject);
                    }
                    
                    if(x < classMethods.length) {
                        sBuffer.append(TO_STRING_SEPARATOR)
                                .append(" ");
                    }
                }
                catch(InvocationTargetException | IllegalArgumentException | IllegalAccessException ex) {
                    System.err.println(ex.getMessage() + ex);
                }
            }
        }
        
        sBuffer.append("}");
        return sBuffer.toString();
    }
    
    private static Object formatDateToString(final Calendar date) {
        String formatedDate = null;
        
        if(date != null) {
            DateFormat dateFormat = new SimpleDateFormat(MASK_DATE);
            Date temp = date.getTime();
            formatedDate = dateFormat.format(temp);
        }
        
        return formatedDate;
    }
}
