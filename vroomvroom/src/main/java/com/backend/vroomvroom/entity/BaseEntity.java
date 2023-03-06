package com.backend.vroomvroom.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseEntity {
    @Column(name = "crt_user_id")
    private String createUserId;
    @Column(name = "updt_user_id")
    private String updateUserId;
    @CreatedDate
    @Column(name = "crt_dt", nullable = false)
    private LocalDateTime createDateTime;
    @LastModifiedDate
    @Column(name = "updt_dt")
    private LocalDateTime updateDateTime;
    @Column(name = "use_yn", nullable = false)
    private String useYn;

    public BaseEntity(String useYn) {
        this.useYn = useYn;
    }
}
