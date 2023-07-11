package com.bindord.jaipro.resourceserver.domain.notification;

import com.bindord.jaipro.resourceserver.domain.base.BaseDomain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Table
@Setter
@Getter
public class Notification extends BaseDomain implements Persistable<UUID> {

    @Id
    @Column(value = "notification_id")
    private UUID id;

    @Column
    private UUID specialistId;

    @Column
    private UUID customerId;

    @NotBlank
    @Size(min = 3, max = 72)
    @Column
    private String title;

    @NotBlank
    @Size(min = 3, max = 600)
    @Column
    private String message;

    @Size(min = 3, max = 72)
    @Column
    private String icon;

    private Integer type;

    @Column
    private boolean toSpecialist;

    @Column
    private boolean toCustomer;

    @Column
    private boolean read;

    @Column
    private boolean deleted;

    @JsonIgnore
    @Transient
    private boolean isNew;

    @Override
    public boolean isNew() {
        return this.isNew;
    }
}
