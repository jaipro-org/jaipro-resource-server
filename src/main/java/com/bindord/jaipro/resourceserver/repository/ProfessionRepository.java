package com.bindord.jaipro.resourceserver.repository;

import com.bindord.jaipro.resourceserver.domain.profession.Profession;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessionRepository extends ReactiveCrudRepository<Profession, Integer> {

}
