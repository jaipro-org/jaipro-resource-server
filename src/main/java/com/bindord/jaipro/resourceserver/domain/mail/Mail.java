package com.bindord.jaipro.resourceserver.domain.mail;

import com.bindord.jaipro.resourceserver.annotation.JsonClassName;
import com.bindord.jaipro.resourceserver.domain.json.MailInput;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.r2dbc.postgresql.codec.Json;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
public class Mail implements Persistable<Integer> {

    @Id
    @Column(value = "mail_id")
    private Integer id;

    @NotBlank
    @Column
    private String subject;

    @NotBlank
    @Column
    @Size(max = 2000)
    private String body;

    @Column
    private String description;

    @JsonClassName(name = MailInput.class, type = "List")
    @Column
    private Json inputs;

    @JsonIgnore
    @Transient
    private boolean isNew;

    public Mail() {
    }

    @Override
    public boolean isNew() {
        return this.isNew;
    }
}

