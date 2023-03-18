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

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Table
@Setter
@Getter
public class UserInfo extends BaseDomain implements Persistable<UUID> {

    @Id
    @Column(value = "user_info_id")
    private UUID id;

    @NotNull
    @Min(value = 0)
    @Max(value = 99)
    private Integer profileType;

    @NotBlank
    @Size(min = 2, max = 36)
    private String profileName;

    public UserInfo() {
    }

    @JsonIgnore
    @Transient
    private boolean isNew;

    @Override
    public boolean isNew() {
        return this.isNew;
    }

}
