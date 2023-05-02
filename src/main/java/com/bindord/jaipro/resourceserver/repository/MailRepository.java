package com.bindord.jaipro.resourceserver.repository;

import com.bindord.jaipro.resourceserver.domain.mail.Mail;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailRepository extends ReactiveCrudRepository<Mail, Integer> {

}
