package cc.hqido.spms.infra.entity;

import cc.hqido.spms.core.model.enums.StatusEnum;
import cc.hqido.spms.infra.base.BaseWithUpdatedTimeEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class UserEntity extends BaseWithUpdatedTimeEntity<Long> {

    @Column(length = 16, nullable = false, unique = true)
    private String username;

    @Column(length = 16, nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.ORDINAL)
    private StatusEnum status;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum statusE) {
        this.status = statusE;
    }
}
