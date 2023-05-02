package com.bindord.jaipro.resourceserver.domain.user;

import com.bindord.jaipro.resourceserver.domain.base.BaseDomain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Table
@Setter
@Getter
public class UserRecover extends BaseDomain implements Persistable<UUID> {

    @Id
    @Column(value = "user_recover_id")
    private UUID id;

    @Column
    private UUID userId;

    @Size(max = Byte.MAX_VALUE)
    @Column
    private String verificationCode;

    @Column
    private Boolean flagRecover;

    @FutureOrPresent
    @Column
    private LocalDateTime limitDate;

    @JsonIgnore
    @Transient
    private boolean isNew;

    public UserRecover() {
    }

    @Override
    public boolean isNew() {
        return this.isNew;
    }
}
