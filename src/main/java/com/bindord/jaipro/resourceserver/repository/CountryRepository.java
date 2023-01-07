package com.bindord.jaipro.resourceserver.repository;

import com.bindord.jaipro.resourceserver.domain.country.Country;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends ReactiveCrudRepository<Country, String> {

}
