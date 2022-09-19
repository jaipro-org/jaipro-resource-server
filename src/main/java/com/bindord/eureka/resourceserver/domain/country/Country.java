package com.bindord.eureka.resourceserver.domain.country;

import com.bindord.eureka.resourceserver.domain.base.BaseDomain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@Table
public class Country extends BaseDomain implements Persistable<String> {

    @Id
    @Size(min = 3, max = 3)
    @Schema(description = "Codes from ISO 3166-1, Alpha-3 code")
    @Column(value = "country_id")
    private String id;

    @Size(min = 2, max = 60)
    private String name;

    @Schema(description = "Numeric code from ISO 3166-1")
    @NotBlank
    @Size(min = 3, max = 3)
    private String numericCode;

    @JsonIgnore
    @Transient
    private boolean isNew;

    public Country() {
    }

    @Override
    public boolean isNew() {
        return this.isNew;
    }

}
