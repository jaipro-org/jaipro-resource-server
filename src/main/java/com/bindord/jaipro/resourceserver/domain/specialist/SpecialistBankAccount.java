package com.bindord.jaipro.resourceserver.domain.specialist;

import com.bindord.jaipro.resourceserver.domain.base.BaseDomain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.*;
import java.util.UUID;

@Setter
@Getter
@Table
public class SpecialistBankAccount extends BaseDomain implements Persistable<UUID> {

    @Id
    @Column(value = "specialist_bank_account_id")
    private UUID id;

    @Size(min = 12, max = 24)
    @Column
    private String accountNumber;

    @NotBlank
    @Size(min = 12, max = 30)
    @Column
    private String cci;

    @NotNull
    @Min(1)
    @Max(13)
    @Column
    private Integer currency;

    @Column
    private boolean preferred;

    @Column
    private UUID specialistId;

    @Column
    private Integer bankId;

    @JsonIgnore
    @Transient
    private boolean isNew;

    @Override
    public boolean isNew() {
        return this.isNew;
    }
}
