package cc.hqido.spms.adapter.home;

import cc.hqido.spms.infra.util.JacksonUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author yueli
 * @since 2022/9/2
 */
public class ResponseUtils {

    public static void writeJSON(HttpServletResponse response, Object object) {
        if (response.isCommitted()) {
            return;
        }

        response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");

        response.setStatus(HttpStatus.OK.value());
        if (object != null) {
            try (PrintWriter writer = response.getWriter()) {
                writer.write(JacksonUtils.json(object));
                writer.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
