package com.bindord.jaipro.resourceserver.domain.notification.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Setter
@Getter
public class NotificationDto {

    private UUID id;

    private UUID specialistId;

    private UUID customerId;

    @NotBlank
    @Size(min = 3, max = 72)
    private String title;

    @NotBlank
    @Size(min = 3, max = 600)
    private String message;

    @Size(min = 3, max = 72)
    private String icon;

    private Integer type;

    private boolean toSpecialist;

    private boolean toCustomer;

    private boolean read;

    private boolean deleted;
}
