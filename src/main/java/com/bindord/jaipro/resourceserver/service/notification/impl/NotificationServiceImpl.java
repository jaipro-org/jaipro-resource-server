package com.bindord.jaipro.resourceserver.service.notification.impl;

import com.bindord.jaipro.resourceserver.advice.CustomValidationException;
import com.bindord.jaipro.resourceserver.advice.NotFoundValidationException;
import com.bindord.jaipro.resourceserver.domain.notification.Notification;
import com.bindord.jaipro.resourceserver.domain.notification.dto.NotificationDto;
import com.bindord.jaipro.resourceserver.domain.notification.dto.NotificationUpdateDto;
import com.bindord.jaipro.resourceserver.repository.NotificationRepository;
import com.bindord.jaipro.resourceserver.service.notification.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static com.bindord.jaipro.resourceserver.utils.Utilitarios.getNullPropertyNames;

@AllArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repository;

    @Override
    public Mono<Notification> save(NotificationDto entity) throws NotFoundValidationException, CustomValidationException {
        return repository.save(convertToEntityForNewCase(entity));
    }

    @Override
    public Mono<Notification> update(NotificationUpdateDto entity) throws NotFoundValidationException, CustomValidationException {
        Mono<Notification> qNotification = repository.findById(entity.getId());
        return qNotification.flatMap(qCus -> repository.save(convertToEntity(entity, qCus)));
    }

    @Override
    public Mono<Notification> findOne(UUID id) throws NotFoundValidationException {
        return repository.findById(id);
    }

    @Override
    public Mono<Void> delete(UUID id) {
        return repository.deleteById(id);
    }

    @Override
    public Mono<Void> delete() {
        return repository.deleteAll();
    }

    @Override
    public Flux<Notification> findAll() {
        return repository.findAll();
    }

    private Notification convertToEntity(NotificationUpdateDto obj, Notification notification) {
        BeanUtils.copyProperties(obj, notification, getNullPropertyNames(obj));
        return notification;
    }

    private Notification convertToEntityForNewCase(NotificationDto obj) {
        var notification = new Notification();
        BeanUtils.copyProperties(obj, notification);
        notification.setNew(true);
        return notification;
    }
}
