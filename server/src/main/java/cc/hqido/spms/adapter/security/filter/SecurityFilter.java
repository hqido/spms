package cc.hqido.spms.adapter.security.filter;

import cc.hqido.spms.adapter.home.AntPathUtils;
import cc.hqido.spms.adapter.home.RequestUtils;
import cc.hqido.spms.adapter.home.ResponseUtils;
import cc.hqido.spms.adapter.security.SecurityContext;
import cc.hqido.spms.adapter.security.constant.SecurityConsts;
import cc.hqido.spms.application.Authenticator;
import cc.hqido.spms.core.ResultBuilderConsts;
import cc.hqido.spms.infra.result.std.ResultBuilder;
import cc.hqido.spms.infra.util.Slf4jLogUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;

@Component
@WebFilter(urlPatterns = "/*")
public class SecurityFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = Slf4jLogUtils.getLogger(SecurityFilter.class);

    private final JWTVerifier jwtVerifier;

    private final Set<String> whitelist = Sets.newHashSet();

    private final Set<String> resourcePathPatterns = Sets.newHashSet();

    private final Authenticator authenticator;

    public SecurityFilter(WebMvcProperties mvcProperties, Authenticator authenticator) {
        this.authenticator = authenticator;

        // init resource path
        Optional.ofNullable(mvcProperties.getStaticPathPattern())
                .ifPresent(resourcePathPatterns::add);

        resourcePathPatterns.add("/webjars/**");

        // init jwt verifier
        this.jwtVerifier = JWT.require(authenticator.getAlgorithm())
                .withIssuer(authenticator.getIssuer())
                .withSubject(authenticator.getSubject())
                .build();

        // init whitelist
        Optional.ofNullable(authenticator.securityConfig().getWhitelist())
                .ifPresent(this.whitelist::addAll);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // skip resource and whitelist path
        if (isResourceRequest(request) || isWhitelist(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String jwt = getJWT(request);
            if (StringUtils.isBlank(jwt)) {
                handleError(ResultBuilderConsts.NOT_LOGIN, response);
                return;
            }

            SecurityContext securityContext = SecurityContext.init(jwtVerifier.verify(jwt), request);

            // verify subject
            authenticator.verify(securityContext.getSubject());

        } catch (Exception e) {
            // ignore jwt verify exception
            if (e instanceof JWTVerificationException) {
                handleError(ResultBuilderConsts.INVALID_LOGIN, response);
            } else {
                Slf4jLogUtils.error(e, LOGGER, "SecurityFilter:doFilterInternal:init");
                handleError(ResultBuilderConsts.SYSTEM_ERROR, response);
            }
        } finally {
            SecurityContext.clear();
        }

    }

    private void handleError(ResultBuilder error, HttpServletResponse response) {
        ResponseUtils.writeJSON(response, error.r());
    }

    private void handleError(ResultBuilder error, HttpServletResponse response, String location) {
        ResponseUtils.writeJSON(response, error.re(location));
    }

    private boolean isWhitelist(HttpServletRequest request) {
        return AntPathUtils.match(whitelist, request);
    }

    private boolean isResourceRequest(HttpServletRequest request) {
        return AntPathUtils.match(resourcePathPatterns, request);
    }

    private String getJWT(HttpServletRequest request) {
        return Optional.ofNullable(RequestUtils.getCookie(request, SecurityConsts.LOGIN_JWT_NAME))
                .map(Cookie::getValue)
                .orElse(null);
    }

}
