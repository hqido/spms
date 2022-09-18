package cc.hqido.spms.adapter.home;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * @author yueli
 * @since 2022/9/1
 */
public class AntPathUtils {

    public static final AntPathMatcher MATCHER = new AntPathMatcher();

    public static boolean match(Collection<String> patterns, HttpServletRequest request) {
        if (CollectionUtils.isEmpty(patterns)) {
            return false;
        }

        String path = request.getRequestURI();

        return patterns.stream().anyMatch(pattern -> match(pattern, path));
    }

    private static boolean match(String pattern, String path) {
        return MATCHER.match(pattern, path);
    }


}
