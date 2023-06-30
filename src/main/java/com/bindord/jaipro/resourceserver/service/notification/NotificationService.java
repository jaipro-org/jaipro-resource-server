package com.bindord.jaipro.resourceserver.service.notification;

import com.bindord.jaipro.resourceserver.domain.notification.Notification;
import com.bindord.jaipro.resourceserver.domain.notification.dto.NotificationDto;
import com.bindord.jaipro.resourceserver.domain.notification.dto.NotificationUpdateDto;
import com.bindord.jaipro.resourceserver.generic.BaseService;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface NotificationService extends BaseService<Notification, UUID, NotificationDto, NotificationUpdateDto> {

    Flux<Notification> findAllByProfileAndUserId(Integer profileType, UUID userId);
}
