package com.bindord.jaipro.resourceserver.repository;

import com.bindord.jaipro.resourceserver.domain.specialist.Specialist;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistPublicInformationDto;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistResultSearchDTO;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface SpecialistRepository extends ReactiveCrudRepository<Specialist, UUID> {

    Mono<Boolean> existsSpecialistByDocument(String document);

    @Query(value = "SELECT * from jaipro.search_specialists(:#{[0]}, :#{[1]}, :#{[2]}, :#{[3]}, :#{[4]})")
    Flux<SpecialistResultSearchDTO> searchSpecialist(String idCategories, String idSpecializations, String idUbigeums, int pageNumber, int pageSize);

    @Query(value = "SELECT * from jaipro.get_specialist_public_information(:#{[0]})")
    Mono<SpecialistPublicInformationDto> getPublicInformationById(UUID specialistId);
}