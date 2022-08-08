package cc.ldsd.common.namof.core;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.ClassFileVersion;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by @author songjhh
 */
public final class NameOfFieldProxy {

    private NameOfFieldProxy() {
    }

    @SuppressWarnings("unchecked")
    public static <T> T getInstance(Class<T> type) {
        DynamicType.Builder<?> builder = new ByteBuddy(ClassFileVersion.JAVA_V8)
                .subclass(type.isInterface() ? Object.class : type);

        if (type.isInterface()) {
            builder = builder.implement(type);
        }

        Class<?> proxyType = builder
                .method(ElementMatchers.any())
                .intercept(MethodDelegation.to(FieldNameExtractorInterceptor.class))
                .make()
                .load(NameOfFieldProxy.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded();

        try {
            Constructor<?> constructor = proxyType.getConstructor();
            return (T) constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException |
                 NoSuchMethodException | InvocationTargetException e) {
            throw new InstantiateProxyException("Couldn't instantiate proxy", e);
        }
    }
}