package com.bindord.jaipro.resourceserver.domain.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
public class Rating implements Serializable {

    @NotNull
    @Min(1)
    @Digits(integer = 1, fraction = 1)
    private Float calification;

    @Size(min = 9, max = 250)
    private String comment;

    @NotBlank
    @Size(min = 9, max = 72)
    private String clientName;

    @NotNull
    @Size(min = 36)
    private String serviceId;

    @FutureOrPresent
    private LocalDateTime date;
}
