package com.bindord.eureka.resourceserver.repository;

import com.bindord.eureka.resourceserver.domain.country.District;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictRepository extends ReactiveCrudRepository<District, Integer> {

}
