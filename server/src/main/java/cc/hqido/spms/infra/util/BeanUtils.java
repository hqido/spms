package cc.hqido.spms.infra.util;

import com.google.common.collect.Maps;
import org.springframework.cglib.beans.BeanCopier;

import java.util.Map;
import java.util.function.Consumer;

public class BeanUtils {

    private static final Map<String, BeanCopier> BEAN_COPIER_CACHE = Maps.newConcurrentMap();

    /**
     * 拷贝（带有缓存）
     *
     * @param source 拷贝对象
     * @param target 目标对象
     * @param <S>    拷贝对象类型
     * @param <T>    目标对象类型
     * @return target or null
     */
    public static <S, T> T copy(S source, T target) {
        if (source == null || target == null) {
            return null;
        }

        BeanCopier beanCopier = BEAN_COPIER_CACHE.computeIfAbsent(getCacheName(source, target), key -> BeanCopier.create(source.getClass(), target.getClass(), false));

        beanCopier.copy(source, target, null);

        return target;
    }

    /**
     * 拷贝并转化（带有缓存）
     *
     * @param source  拷贝对象
     * @param target  目标对象
     * @param convert 后置转化器
     * @param <S>     拷贝对象类型
     * @param <T>     目标对象类型
     * @return target or null
     */
    public static <S, T> T copy(S source, T target, Consumer<T> convert) {
        target = copy(source, target);

        if (target == null) {
            return null;
        }

        convert.accept(target);

        return target;
    }


    private static <S, T> String getCacheName(S source, T target) {
        return source.getClass().getName() + "&" + target.getClass().getName();
    }
}
