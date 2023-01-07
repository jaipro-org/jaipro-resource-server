package com.bindord.jaipro.resourceserver.domain.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SvcVal implements Serializable {

    private String field;
    private String fieldEs;
    private String description;
    private Integer minLen;
    private Integer maxLen;
    @JsonInclude(Include.NON_DEFAULT)
    private boolean alphanumeric;
    private String pattern;
    private Integer inputType;

    public SvcVal(){}
}
