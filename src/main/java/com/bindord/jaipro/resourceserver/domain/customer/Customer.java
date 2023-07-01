package com.bindord.jaipro.resourceserver.domain.customer;

import com.bindord.jaipro.resourceserver.annotation.JsonClassName;
import com.bindord.jaipro.resourceserver.domain.base.BaseDomain;
import com.bindord.jaipro.resourceserver.domain.json.Photo;
import com.bindord.jaipro.resourceserver.validation.ExtendedEmailValidator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.r2dbc.postgresql.codec.Json;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.UUID;

@Setter
@Getter
@Table
public class Customer extends BaseDomain implements Persistable<UUID> {

    @Id
    @Column(value = "customer_id")
    private UUID id;

    @NotBlank
    @Size(min = 2, max = 36)
    @Column
    private String name;

    @NotBlank
    @Size(min = 2, max = 36)
    @Column
    private String lastName;

    @Min(value = 0)
    @Column
    private Integer identity;

    @Min(value = 1)
    @Max(value = 2)
    @Column
    private Integer gender;

    @Size(min = 2, max = Byte.MAX_VALUE)
    @Column
    private String address;

    @ExtendedEmailValidator
    @NotBlank
    @Size(min = 7, max = 60)
    @Column
    private String email;

    @Null
    @Size(min = 8, max = 12)
    @Column
    private String document;

    @Size(min = 9, max = 15)
    @Column
    private String phone;

    @JsonClassName(name = Photo.class)
    @Column
    private Json profilePhoto;

    @Size(min = 7, max = Byte.MAX_VALUE)
    @Column
    private String publicUrl;

    @Column
    private boolean verifiedEmail;

    @Column
    private Integer districtId;

    @JsonIgnore
    @Transient
    private boolean isNew;

    @Override
    public boolean isNew() {
        return this.isNew;
    }

}
