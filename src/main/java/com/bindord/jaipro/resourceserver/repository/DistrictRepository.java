package com.bindord.jaipro.resourceserver.repository;

import com.bindord.jaipro.resourceserver.domain.country.District;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictRepository extends ReactiveCrudRepository<District, Integer> {

}
