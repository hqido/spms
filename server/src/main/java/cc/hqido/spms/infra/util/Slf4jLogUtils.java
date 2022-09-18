package cc.hqido.spms.infra.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yueli
 * @since 2022/9/2
 */
public class Slf4jLogUtils {
    public static Logger getLogger(String name) {
        return LoggerFactory.getLogger(name);
    }

    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    /**
     * info 日志
     *
     * @param logger    logger
     * @param msg       输出信息占位符使用 如 <code>xxx, {}, xxx, {}</code>
     * @param arguments 格式化参数，和占位符长度保持一致
     */
    public static void info(Logger logger, String msg, Object... arguments) {
        if (logger == null) {
            return;
        }

        if (logger.isInfoEnabled()) {
            logger.info(msg, arguments);
        }
    }

    public static void debug(Logger logger, String msg, Object... arguments) {
        if (logger == null) {
            return;
        }

        if (logger.isDebugEnabled()) {
            logger.debug(msg, arguments);
        }
    }

    /**
     * error 日志
     *
     * @param t         异常对象
     * @param logger    logger
     * @param msg       输出信息占位符使用 如 <code>xxx, {}, xxx, {}</code>
     * @param arguments 格式化参数，和占位符长度保持一致
     */
    public static void error(Throwable t, Logger logger, String msg, Object... arguments) {
        if (logger == null) {
            return;
        }

        if (logger.isErrorEnabled()) {
            logger.error(MessageUtils.format(msg, arguments), t);
        }
    }

    /**
     * error 日志
     *
     * @param t      异常对象
     * @param logger logger
     */
    public static void error(Throwable t, Logger logger) {
        if (logger == null) {
            return;
        }

        if (logger.isErrorEnabled()) {
            logger.error("", t);
        }
    }

    /**
     * error 日志
     *
     * @param logger    Logger
     * @param msg       输出信息占位符使用 如 <code>xxx, {}, xxx, {}</code>
     * @param arguments 格式化参数，和占位符长度保持一致
     */
    public static void error(Logger logger, String msg, Object... arguments) {
        if (logger == null) {
            return;
        }

        if (logger.isErrorEnabled()) {
            logger.error(MessageUtils.format(msg, arguments));
        }
    }

    /**
     * warn 日志
     *
     * @param t         异常对象
     * @param logger    logger
     * @param msg       输出信息占位符使用 如 <code>xxx, {}, xxx, {}</code>
     * @param arguments 格式化参数，和占位符长度保持一致
     */
    public static void warn(Throwable t, Logger logger, String msg, Object... arguments) {
        if (logger == null) {
            return;
        }

        if (logger.isWarnEnabled()) {
            logger.warn(MessageUtils.format(msg, arguments), t);
        }
    }

    /**
     * warn 日志
     *
     * @param logger    logger
     * @param msg       输出信息占位符使用 如 <code>xxx, {}, xxx, {}</code>
     * @param arguments 格式化参数，和占位符长度保持一致
     */
    public static void warn(Logger logger, String msg, Object... arguments) {
        if (logger == null) {
            return;
        }

        if (logger.isWarnEnabled()) {
            logger.warn(MessageUtils.format(msg, arguments));
        }
    }
}
