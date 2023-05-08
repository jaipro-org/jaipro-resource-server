package com.bindord.jaipro.resourceserver.domain.mail.dto;

import com.bindord.jaipro.resourceserver.domain.json.MailInput;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Setter
@Getter
public class MailUpdateDto {

    private Integer id;

    @NotEmpty
    private String subject;

    @NotEmpty
    private String body;

    private String description;

    private List<MailInput> inputs;

    public MailUpdateDto() {
    }
}

