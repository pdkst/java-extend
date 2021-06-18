package io.github.pdkst.java.utils.extentions;

import lombok.experimental.ExtensionMethod;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 可以使用{@link ExtensionMethod} 进行导入，谨慎使用到生产代码
 * <pre>
 *    @ExtensionMethod(KotlinLikeExtension.class)
 * </pre>
 *
 * @author pdkst
 * @since 2021/6/10 13:22
 */
public class KotlinLikeExtension {
    public static <T> T defaultValue(T target, T defaultValue) {
        return target != null ? target : defaultValue;
    }

    public static <T> T defaultValue(T target, Supplier<T> defaultValueSupplier) {
        return target != null ? target : defaultValueSupplier.get();
    }

    public static <T> void let(T target, Consumer<T> letConsumer) {
        if (target != null && letConsumer != null) {
            letConsumer.accept(target);
        }
    }

    public static <T, R> R let(T target, Function<T, R> function) {
        if (target != null && function != null) {
            return function.apply(target);
        }
        return null;
    }

    public static <T> T also(T target, Consumer<T> consumer) {
        let(target, consumer);
        return target;
    }

    public static <T, R> T also(T target, Function<T, R> function) {
        if (target != null && function != null) {
            function.apply(target);
        }
        return target;
    }


    public static <T> Integer toInt(T target) {
        return toInt(target, null);
    }

    public static <T> Integer toInt(T target, Integer defaultValue) {
        if (target == null) {
            return defaultValue;
        }
        if (target instanceof Integer) {
            return (Integer) target;
        } else if (target instanceof Number) {
            return ((Number) target).intValue();
        } else {
            final String value = target.toString();
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                // ignore
            }
        }
        return defaultValue;
    }

    public static <T> Long toLong(T target) {
        return toLong(target, null);
    }

    public static <T> Long toLong(T target, Long defaultValue) {
        if (target == null) {
            return defaultValue;
        }
        if (target instanceof Long) {
            return (Long) target;
        } else if (target instanceof Number) {
            return ((Number) target).longValue();
        } else {
            final String value = target.toString();
            try {
                return Long.parseLong(value);
            } catch (NumberFormatException e) {
                //ignore
            }
        }
        return defaultValue;
    }

    public static <T> Double toDouble(T target) {
        return toDouble(target, null);
    }

    public static <T> Double toDouble(T target, Double defaultValue) {
        if (target == null) {
            return defaultValue;
        }
        if (target instanceof Double) {
            return (Double) target;
        } else if (target instanceof Number) {
            return ((Number) target).doubleValue();
        } else {
            final String value = target.toString();
            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException e) {
                //ignore
            }
        }
        return defaultValue;
    }
}
