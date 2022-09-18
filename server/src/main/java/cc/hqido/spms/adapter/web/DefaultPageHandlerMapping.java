package cc.hqido.spms.adapter.web;

import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.AbstractUrlHandlerMapping;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

/**
 * default page handler
 *
 * @author yueli
 * @since 2022/9/1
 */
public class DefaultPageHandlerMapping extends AbstractUrlHandlerMapping {

    private static final List<MediaType> MEDIA_TYPES_ALL = Collections.singletonList(MediaType.ALL);

    public DefaultPageHandlerMapping() {
        ParameterizableViewController controller = new ParameterizableViewController();
        controller.setViewName("forward:/static/index.html");
        setDefaultHandler(controller);

        setOrder(Ordered.LOWEST_PRECEDENCE);
    }

    @Override
    public Object getHandlerInternal(HttpServletRequest request) throws Exception {
        for (MediaType mediaType : getAcceptedMediaTypes(request)) {
            if (mediaType.includes(MediaType.TEXT_HTML)) {
                return super.getHandlerInternal(request);
            }
        }
        return null;
    }

    private List<MediaType> getAcceptedMediaTypes(HttpServletRequest request) {
        String acceptHeader = request.getHeader(HttpHeaders.ACCEPT);
        if (StringUtils.hasText(acceptHeader)) {
            return MediaType.parseMediaTypes(acceptHeader);
        }
        return MEDIA_TYPES_ALL;
    }

}
