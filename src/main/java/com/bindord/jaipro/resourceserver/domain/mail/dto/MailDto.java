package com.bindord.jaipro.resourceserver.domain.mail.dto;

import com.bindord.jaipro.resourceserver.domain.json.MailInput;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Setter
@Getter
public class MailDto {

    private Integer id;

    @NotBlank
    private String subject;

    @NotBlank
    private String body;

    private String description;

    private List<MailInput> inputs;

    public MailDto() {
    }
}

