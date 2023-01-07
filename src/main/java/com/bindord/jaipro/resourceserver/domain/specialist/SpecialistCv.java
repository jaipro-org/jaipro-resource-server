package com.bindord.jaipro.resourceserver.domain.specialist;

import com.bindord.jaipro.resourceserver.annotation.JsonClassName;
import com.bindord.jaipro.resourceserver.domain.base.BaseDomain;
import com.bindord.jaipro.resourceserver.domain.json.Photo;
import com.bindord.jaipro.resourceserver.domain.specialist.json.Experience;
import com.bindord.jaipro.resourceserver.domain.specialist.json.SocialNetwork;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.r2dbc.postgresql.codec.Json;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Setter
@Getter
@Table
public class SpecialistCv extends BaseDomain implements Persistable<UUID> {

    @Id
    @Column(value = "specialist_id")
    private UUID id;

    @Size(min = 30, max = 1300)
    @Column
    private String about;

    @JsonClassName(name = SocialNetwork.class, type = "List")
    @Column
    private Json socialNetworks;

    @JsonClassName(name = Photo.class, type = "List")
    @Column
    private Json gallery;

    @JsonClassName(name = Photo.class)
    @Column
    private Json profilePhoto;

    @NotNull
    @JsonClassName(name = Experience.class, type = "List")
    @Column
    private Json experienceTimes;

    @JsonIgnore
    @Transient
    private boolean isNew;

    @Override
    public boolean isNew() {
        return this.isNew;
    }
}
