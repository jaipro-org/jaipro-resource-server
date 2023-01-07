package com.bindord.jaipro.resourceserver.domain.specialist.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Experience implements Serializable {

    @NotNull
    private UUID professionId;

    @NotBlank
    @Size(min = 3, max = 50)
    private String professionName;

    @Schema(description = "Experience time in a profession expressed in months")
    private Integer time;

    @FutureOrPresent
    private LocalDateTime date;
}
