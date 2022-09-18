package cc.hqido.spms.infra.base;

import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.LocalDateTime;

public class BaseWithUpdatedTimeEntity<ID extends Serializable> extends BaseEntity<ID> {

    @LastModifiedDate
    public LocalDateTime updatedTime;

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }
}
