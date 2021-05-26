package cc.ldsd.common.namof.core;

import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;

import java.lang.reflect.Method;

/**
 * Created by @author songjhh
 */
public class FieldNameExtractorInterceptor {

    private FieldNameExtractorInterceptor() {
    }

    private static final ThreadLocal<String> CURRENT_EXTRACTED_METHOD_NAME = new ThreadLocal<>();

    @RuntimeType
    public static Object intercept(@Origin Method method) {
        CURRENT_EXTRACTED_METHOD_NAME.set(getPropertyName(method));
        if (method.getReturnType() == byte.class) {
            return (byte) 0;
        }
        if (method.getReturnType() == int.class) {
            return 0;
        }
        if (method.getReturnType() == long.class) {
            return (long) 0;
        }
        if (method.getReturnType() == char.class) {
            return (char) 0;
        }
        if (method.getReturnType() == short.class) {
            return (short) 0;
        }
        if (method.getReturnType() == boolean.class) {
            return false;
        }
        if (method.getReturnType() == float.class) {
            return (float) 0;
        }
        if (method.getReturnType() == double.class) {
            return (double) 0;
        }
        return null;
    }

    private static String getPropertyName(Method method) {
        final String get = "get";
        final String is = "is";
        if (method.getParameterTypes().length == 0
                && method.getReturnType() != Void.TYPE) {
            String name = method.getName();
            if (name.startsWith(get)) {
                return name.substring(3, 4).toLowerCase() + name.substring(4);
            } else if (name.startsWith(is)) {
                return name.substring(2, 3).toLowerCase() + name.substring(3);
            }
        }
        throw new GetPropertyNameException("Only property getter methods are expected to be passed");
    }

    public static String extractMethodName() {
        String methodName = CURRENT_EXTRACTED_METHOD_NAME.get();
        CURRENT_EXTRACTED_METHOD_NAME.remove();
        return methodName;
    }
}
