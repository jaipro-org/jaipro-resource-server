package com.bindord.eureka.resourceserver.repository;

import com.bindord.eureka.resourceserver.domain.country.Country;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends ReactiveCrudRepository<Country, String> {

}
