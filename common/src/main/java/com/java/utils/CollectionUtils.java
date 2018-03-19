package com.java.utils;

import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * 集合工具类    .
 *
 * @author mali
 * @version v 1.0
 * @ClassName: CollectionUtils
 * @date: 2016年4月26日 下午4:27:28
 */
public class CollectionUtils {

    /**
     * 判断是否为空.
     *
     * @param collection the collection
     * @return boolean
     * @author mali
     * @title: isEmpty
     * @date: 2016-5-20 15:11:48
     */
    public static boolean isEmpty(Collection collection) {
        return (collection == null) || collection.isEmpty();
    }

    /**
     * 判断是否为空.
     *
     * @param collection the collection
     * @return boolean
     * @author mali
     * @title: isNotEmpty
     * @date: 2016-5-20 15:11:48
     */
    public static boolean isNotEmpty(Collection collection) {
        return (collection != null) && !(collection.isEmpty());
    }

    /**
     * 取得Collection的第一个元素，如果collection为空返回null.
     *
     * @param <T>        the generic type
     * @param collection the collection
     * @return T
     * @author mali
     * @title: getFirst
     * @date: 2016-5-20 15:11:48
     */
    public static <T> T getFirst(Collection<T> collection) {
        if (isEmpty(collection)) {
            return null;
        }

        return collection.iterator().next();
    }

    /**
     * 获取Collection的最后一个元素 ，如果collection为空返回null.
     *
     * @param <T>        the generic type
     * @param collection the collection
     * @return T
     * @author mali
     * @title: getLast
     * @date: 2016-5-20 15:11:48
     */
    public static <T> T getLast(Collection<T> collection) {
        if (isEmpty(collection)) {
            return null;
        }

        // 当类型为List时，直接取得最后一个元素 。
        if (collection instanceof List) {
            List<T> list = (List<T>) collection;
            return list.get(list.size() - 1);
        }

        // 其他类型通过iterator滚动到最后一个元素.
        Iterator<T> iterator = collection.iterator();
        while (true) {
            T current = iterator.next();
            if (!iterator.hasNext()) {
                return current;
            }
        }
    }

    /**
     * 返回a+b的新List.
     *
     * @param <T> the generic type
     * @param a   the a
     * @param b   the b
     * @return List
     * @author mali
     * @title: union
     * @date: 2016-5-20 15:11:48
     */
    public static <T> List<T> union(final Collection<T> a, final Collection<T> b) {
        if (isEmpty(a) && isEmpty(b)) {
            return new ArrayList<T>();
        }
        if (isEmpty(a)) {
            return new ArrayList<T>(b);
        }
        if (isEmpty(b)) {
            return new ArrayList<T>(a);
        }
        List<T> result = new ArrayList<T>(a);
        result.addAll(b);
        return result;
    }

    /**
     * 返回a-b的新List.
     *
     * @param <T> the generic type
     * @param a   the a
     * @param b   the b
     * @return List
     * @author mali
     * @title: subtract
     * @date: 2016-5-20 15:11:48
     */
    public static <T> List<T> subtract(final Collection<T> a, final Collection<T> b) {
        if (isEmpty(a)) {
            return new ArrayList<T>();
        }
        if (isEmpty(b)) {
            return new ArrayList<T>(a);
        }
        List<T> list = new ArrayList<T>(a);
        for (T element : b) {
            list.remove(element);
        }

        return list;
    }

    /**
     * 返回a与b的交集的新List.
     *
     * @param <T> the generic type
     * @param a   the a
     * @param b   the b
     * @return List
     * @author mali
     * @title: intersection
     * @date: 2016-5-20 15:11:48
     */
    public static <T> List<T> intersection(Collection<T> a, Collection<T> b) {
        List<T> list = new ArrayList<T>();

        for (T element : a) {
            if (b.contains(element)) {
                list.add(element);
            }
        }
        return list;
    }

    /**
     * TODO(这里用一句话描述这个方法的作用).
     *
     * @param value the value
     * @return Collection
     * @author mali
     * @title: toCollection
     * @date: 2016-5-20 15:11:48
     */
    public static Collection<?> toCollection(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Collection<?>) {
            return (Collection<?>) value;
        }
        if (value instanceof Object[]) {
            Object[] v = (Object[]) value;
            return Arrays.asList(v);
        }
        if (ClassUtils.isPrimitiveArray(value.getClass())) {
            // 基本类型数组转换成对象数组
            Object[] v = ObjectUtils.toObjectArray(value);
            return Arrays.asList(v);
        }
        return null;
    }

    /**
     * TODO(这里用一句话描述这个方法的作用).
     *
     * @param os the os
     * @return boolean
     * @author mali
     * @title: notEmpty
     * @date: 2016-5-20 15:11:48
     */
    public static boolean notEmpty(Object[] os) {
        return !empty(os);
    }

    /**
     * TODO(这里用一句话描述这个方法的作用).
     *
     * @param os the os
     * @return boolean
     * @author mali
     * @title: empty
     * @date: 2016-5-20 15:11:48
     */
    public static boolean empty(Object[] os) {
        return os == null || os.length == 0;
    }
}
