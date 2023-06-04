package com.bindord.jaipro.resourceserver.domain.notification.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class NotificationUpdateDto {

    private UUID id;

    private boolean read;

    private boolean deleted;
}
