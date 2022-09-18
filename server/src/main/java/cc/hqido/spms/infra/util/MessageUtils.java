package cc.hqido.spms.infra.util;

import org.slf4j.helpers.MessageFormatter;

public class MessageUtils {

    public static String format(String pattern, Object... params) {
        return formatWithDefault(pattern, null, params);
    }

    /**
     * 格式化替换占位符
     *
     * @param pattern      模板
     * @param defaultParam placeholderReplaceChar 占位符替换字符
     * @param params       参数
     * @return
     */
    public static String formatWithDefault(String pattern, String defaultParam, Object... params) {
        String message;

        if (params == null) {
            message = pattern;
        } else {
            message = MessageFormatter.arrayFormat(pattern, params).getMessage();
        }

        if (defaultParam != null && message != null) {
            message = message.replaceAll("\\{}", defaultParam).trim();
        }

        return message;
    }

}
