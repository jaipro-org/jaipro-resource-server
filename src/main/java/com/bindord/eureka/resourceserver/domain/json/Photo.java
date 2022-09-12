package com.bindord.eureka.resourceserver.domain.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
public class Photo implements Serializable {

    @Schema(description = "Optional description of the photo")
    @Size(min = 3, max = 50)
    private String name;

    @Schema(description = "Image size expressed in bytes")
    @NotNull
    @Min(Byte.MAX_VALUE * 40)
    @Max(4194304)
    private Integer size;

    @NotBlank
    @Size(min = 36, max = 200)
    private String url;

    @FutureOrPresent
    private LocalDateTime date;
}
