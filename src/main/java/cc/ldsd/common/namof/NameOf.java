package cc.ldsd.common.namof;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static cc.ldsd.common.namof.core.NameOfCore.nameOfField;

/**
 * Created by @author songjhh
 */
public final class NameOf<T> {
    private static final String ID = "id";
    private static final String CHANGE_ID = "_id";
    private final List<String> list;
    private final Class<T> currentClass;
    private final boolean changeIdField;

    private NameOf(Class<T> currentClass, List<String> list, boolean changeIdField) {
        this.currentClass = currentClass;
        this.list = list;
        this.changeIdField = changeIdField;
    }

    /**
     * 初始化
     * 默认转化 id 为 _id
     *
     * @param clazz 处理类
     */
    public static <T> NameOf<T> mInit(Class<T> clazz) {
        return new NameOf<>(clazz, new ArrayList<>(), true);
    }

    /**
     * 初始化
     * 默认转化 id 为 _id
     *
     * @param clazz 处理类
     */
    public static <T> NameOf<T> init(Class<T> clazz) {
        return new NameOf<>(clazz, new ArrayList<>(), false);
    }

    /**
     * 初始化
     *
     * @param clazz         处理类
     * @param changeIdField 是否转化 id 为 _id
     */
    public static <T> NameOf<T> init(Class<T> clazz, boolean changeIdField) {
        return new NameOf<>(clazz, new ArrayList<>(), changeIdField);
    }

    /**
     * 解析字段
     */
    public NameOf<T> field(Function<T, ?> bridge) {
        this.list.add(nameOfField(this.currentClass, bridge));
        return this;
    }

    /**
     * 解析字段
     */
    public <K> NameOf<T> field(Function<K, ?> bridge, Class<K> kClass) {
        this.list.add(nameOfField(kClass, bridge));
        return this;
    }

    /**
     * 添加任意字符串
     */
    public NameOf<T> any(String s) {
        this.list.add(s);
        return this;
    }

    /**
     * 获取字符串
     */
    public String str() {
        return this.list.stream().map(a -> {
            if (this.changeIdField && a.equals(ID)) {
                return CHANGE_ID;
            } else {
                return a;
            }
        }).reduce((a, b) -> a + b).orElse(null);
    }

    /**
     * 获取字符数组
     */
    public String[] array() {
        return this.list.stream().map(a -> {
            if (this.changeIdField && a.equals(ID)) {
                return CHANGE_ID;
            } else {
                return a;
            }
        }).toArray(String[]::new);
    }
}
