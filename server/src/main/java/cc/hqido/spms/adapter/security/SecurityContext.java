package cc.hqido.spms.adapter.security;

import com.auth0.jwt.interfaces.DecodedJWT;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Optional;
import java.util.function.Function;

public class SecurityContext {

    public static final ThreadLocal<SecurityContext> CONTEXT_HOLDER = new ThreadLocal<>();

    private final Subject subject;

    private final Instant expires;

    private final HttpServletRequest request;

    public SecurityContext(DecodedJWT decodedJWT, HttpServletRequest request) {
        if (decodedJWT != null) {
            this.subject = new Subject(decodedJWT);
            this.expires = decodedJWT.getExpiresAtAsInstant();
        } else {
            this.subject = null;
            this.expires = null;
        }

        this.request = request;
    }

    public static SecurityContext init(DecodedJWT decodedJWT, HttpServletRequest request) {
        SecurityContext securityContext = new SecurityContext(decodedJWT, request);
        CONTEXT_HOLDER.set(securityContext);
        return securityContext;
    }

    public static void clear() {
        CONTEXT_HOLDER.remove();
    }

    public static <T> Optional<T> get(Function<SecurityContext, T> function) {
        return Optional.of(getCurrentSecurityContext())
                .map(function);
    }

    public static SecurityContext getCurrentSecurityContext() {
        return CONTEXT_HOLDER.get();
    }

    public Subject getSubject() {
        return subject;
    }

    public Instant getExpires() {
        return expires;
    }

    public HttpServletRequest getRequest() {
        return request;
    }
}
