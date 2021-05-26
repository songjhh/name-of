package cc.ldsd.common.namof.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * Created by @author songjhh
 */
public final class NameOfCore {

    private static final Map<Class<?>, Object> STORE_MAP = new ConcurrentHashMap<>();

    private NameOfCore() {
    }

    @SuppressWarnings("unchecked")
    public static <T> String nameOfField(Class<?> clazz, Function<? super T, ?> bridge) {
        T extractor = (T) STORE_MAP.computeIfAbsent(clazz, NameOfFieldProxy::getInstance);
        bridge.apply(extractor);
        return FieldNameExtractorInterceptor.extractMethodName();
    }

}
