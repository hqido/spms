package cc.hqido.spms.infra.util;

import cc.hqido.spms.infra.result.std.ResultBuilder;

import java.util.Objects;

public class Asserts {

    public static void isNull(Object o, ResultBuilder rb, Object... params) {
        if (Objects.nonNull(o)) {
            throw rb.e(params);
        }
    }

    public static void nonNull(Object o, ResultBuilder rb, Object... params) {
        if (Objects.isNull(o)) {
            throw rb.e(params);
        }
    }

    public static void isTrue(Boolean expression, ResultBuilder rb, Object... params) {
        if (expression == null || !expression) {
            throw rb.e(params);
        }
    }

    public static void isFalse(Boolean expression, ResultBuilder rb, Object... params) {
        if (expression == null || expression) {
            throw rb.e(params);
        }
    }

}
