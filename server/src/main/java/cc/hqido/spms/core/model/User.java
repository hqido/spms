package cc.hqido.spms.core.model;

import cc.hqido.spms.infra.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.OffsetDateTime;

public class User extends UserEntity {

    public Long version() {
        return super.getUpdatedTime().toEpochSecond(OffsetDateTime.now().getOffset());
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return super.getPassword();
    }
}
