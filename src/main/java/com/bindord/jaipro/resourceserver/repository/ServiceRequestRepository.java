package com.bindord.jaipro.resourceserver.repository;

import com.bindord.jaipro.resourceserver.domain.service.ServiceRequest;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ServiceRequestRepository extends ReactiveCrudRepository<ServiceRequest, UUID> {
}