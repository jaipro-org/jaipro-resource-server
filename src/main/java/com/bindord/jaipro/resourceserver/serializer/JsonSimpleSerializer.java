package com.bindord.jaipro.resourceserver.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import io.r2dbc.postgresql.codec.Json;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class JsonSimpleSerializer extends JsonSerializer<Json> {

    @Override
    public void serialize(Json json, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeRawValue(json.asString());
    }

}
