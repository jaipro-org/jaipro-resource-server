package com.bindord.jaipro.resourceserver.repository;

import com.bindord.jaipro.resourceserver.domain.notification.Notification;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface NotificationRepository extends ReactiveCrudRepository<Notification, UUID> {

    Flux<Notification> findAllBySpecialistIdAndToSpecialist(UUID specialistId, Boolean toSpecialist);

    Flux<Notification> findAllByCustomerIdAndToCustomer(UUID customerId, Boolean toCustomer);
}
