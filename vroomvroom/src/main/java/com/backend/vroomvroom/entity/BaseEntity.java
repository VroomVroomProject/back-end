package com.backend.vroomvroom.entity;

import lombok.*;
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
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseEntity {
    @Column(name = "crt_user_id")
    private Long createUserId;
    @Column(name = "updt_user_id")
    private Long updateUserId;
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

    /**
     * 모든 삭제기능은 useYn을 N으로 "업데이트"를 한다.
     * @Date 2023-03-12
     * @Author 임성현
     */
    public void delete() {
        this.useYn = "N";
    }
}
