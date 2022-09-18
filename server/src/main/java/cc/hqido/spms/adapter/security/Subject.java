package cc.hqido.spms.adapter.security;

import cc.hqido.spms.adapter.security.constant.SecurityConsts;
import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * @author yueli
 * @since 2022/9/2
 */
public class Subject {

    private final String uid;

    private final Long version;

    public Subject(DecodedJWT decodedJWT) {
        this.uid = decodedJWT.getAudience().get(0);
        this.version = decodedJWT.getClaim(SecurityConsts.JWT_VERSION_NAME).asLong();
    }

    public String getUid() {
        return uid;
    }

    public Long getVersion() {
        return version;
    }

    public Long getUserId() {
        return Long.valueOf(uid);
    }

}
