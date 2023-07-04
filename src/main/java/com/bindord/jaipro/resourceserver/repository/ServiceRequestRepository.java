package com.bindord.jaipro.resourceserver.repository;

import com.bindord.jaipro.resourceserver.domain.service.ServiceRequest;
import com.bindord.jaipro.resourceserver.domain.service.dto.ServiceRequestListDto;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface ServiceRequestRepository extends ReactiveCrudRepository<ServiceRequest, UUID> {

    @Query(value = "SELECT * FROM jaipro.get_list_service_request_by_customer(:#{[0]}, :#{[1]}, :#{[2]})")
    Flux<ServiceRequestListDto> getListServiceRequestByCustomerId(UUID customerId, int pageNumber, int pageSize);

    @Query(value = "SELECT * from jaipro.get_total_rows_list_service_request_by_customer(:#{[0]})")
    Mono<Integer> getTotalRowsInListServiceRequestByCustomerId(UUID customerId);
}