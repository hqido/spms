package cc.hqido.spms.adapter.api;

import cc.hqido.spms.adapter.form.LoginForm;
import cc.hqido.spms.adapter.home.RequestUtils;
import cc.hqido.spms.adapter.home.Root;
import cc.hqido.spms.adapter.security.SecurityConfig;
import cc.hqido.spms.adapter.security.SecurityContext;
import cc.hqido.spms.adapter.security.Subject;
import cc.hqido.spms.adapter.security.constant.SecurityConsts;
import cc.hqido.spms.application.Authenticator;
import cc.hqido.spms.core.ResultBuilderConsts;
import cc.hqido.spms.core.model.User;
import cc.hqido.spms.core.service.UserService;
import cc.hqido.spms.infra.result.Result;
import com.auth0.jwt.JWT;
import com.google.common.net.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.UUID;

@RestController
@RequestMapping(AuthController.ROOT)
public class AuthController {

    protected static final String ROOT = Root.API_ROOT + "/auth";

    private static final String CONTENT_TYPE_ALL = HttpHeaders.CONTENT_TYPE + ":*";

    @Resource
    private Authenticator authenticator;

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public Result login(@Valid @RequestBody LoginForm loginForm, HttpServletResponse response) {
        response.addCookie(genCookie(authenticator.auth(loginForm.getUsername(), loginForm.getPassword())));

        return ResultBuilderConsts.SUCCESS.r();
    }

    @PostMapping(value = "/logout", headers = {CONTENT_TYPE_ALL})
    public Result logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = RequestUtils.getCookie(request, SecurityConsts.LOGIN_JWT_NAME);

        if (cookie != null) {
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }

        return ResultBuilderConsts.SUCCESS.r();
    }

    @GetMapping("/getCurrentUser")
    public Result getCurrentUser() {
        Long uid = SecurityContext.get(SecurityContext::getSubject)
                .map(Subject::getUserId)
                .orElseThrow(ResultBuilderConsts.LOGIN_INVALID::e);

        User user = userService.get(uid);

        if (user == null) {

        }

        return null;
    }

    private Cookie genCookie(User user) {
        SecurityConfig securityConfig = authenticator.securityConfig();

        LocalDateTime exp = LocalDateTime.now()
                .plusDays(securityConfig.getExp());

        Cookie cookie = new Cookie(SecurityConsts.LOGIN_JWT_NAME, JWT.create()
                .withIssuer(authenticator.getIssuer())
                .withAudience(user.getId().toString())
                .withExpiresAt(exp.atZone(ZoneId.systemDefault()).toInstant())
                .withSubject(authenticator.getSubject())
                .withJWTId(user.getId() + "_" + UUID.randomUUID().toString().replaceAll("-", ""))
                .withClaim(SecurityConsts.JWT_VERSION_NAME, user.version())
                .sign(authenticator.getAlgorithm()));

        cookie.setHttpOnly(securityConfig.isHttpOnly());
        cookie.setSecure(securityConfig.isSecure());
        cookie.setDomain("." + securityConfig.getDomain());
        cookie.setPath("/");
        cookie.setMaxAge(Long.valueOf(exp.toEpochSecond(OffsetDateTime.now().getOffset())).intValue());

        return cookie;
    }


}
