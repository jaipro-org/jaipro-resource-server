package com.bindord.jaipro.resourceserver.service.profession;

import com.bindord.jaipro.resourceserver.domain.profession.Profession;
import com.bindord.jaipro.resourceserver.domain.profession.dto.ProfessionDto;
import com.bindord.jaipro.resourceserver.domain.profession.dto.ProfessionUpdateDto;
import com.bindord.jaipro.resourceserver.generic.BaseService;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProfessionService extends BaseService<Profession, Integer, ProfessionDto, ProfessionUpdateDto> {

    Flux<Profession> findAllNative();
}
