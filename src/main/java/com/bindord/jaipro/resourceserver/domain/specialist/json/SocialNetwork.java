package com.bindord.jaipro.resourceserver.domain.specialist.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SocialNetwork implements Serializable {

    @NotNull
    @Min(1)
    private Integer id;

    @NotBlank
    @Size(min = 3, max = 30)
    private String name;

    @Schema(description = "Social network's username")
    private String username;

}
