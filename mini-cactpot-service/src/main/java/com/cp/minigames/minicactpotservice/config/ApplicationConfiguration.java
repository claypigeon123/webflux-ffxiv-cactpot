package com.cp.minigames.minicactpotservice.config;

import com.cp.minigames.minicactpotservice.config.props.CleanupProperties;
import com.cp.minigames.minicactpotservice.config.props.MiniCactpotProperties;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.NonNull;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.io.IOException;
import java.time.Clock;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Configuration
@SuppressWarnings("Convert2Lambda")
@EnableConfigurationProperties({ MiniCactpotProperties.class, CleanupProperties.class })
public class ApplicationConfiguration {

    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }

    @Bean
    public SimpleModule odtModule() {
        SimpleModule module = new SimpleModule();

        JsonSerializer<OffsetDateTime> serializer = new JsonSerializer<>() {
            @Override
            public void serialize(OffsetDateTime odt, JsonGenerator jgen, SerializerProvider provider) throws IOException {
                String formatted = odt.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of(ZoneOffset.UTC.getId())));
                jgen.writeString(formatted);
            }
        };
        JsonDeserializer<OffsetDateTime> deserializer = new JsonDeserializer<>() {
            @Override
            public OffsetDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                return OffsetDateTime.parse(jsonParser.getValueAsString());
            }
        };

        module.addSerializer(OffsetDateTime.class, serializer);
        module.addDeserializer(OffsetDateTime.class, deserializer);

        return module;
    }

    @Bean
    public Converter<String, OffsetDateTime> offsetDateTimePathVariableConverter() {
        return new Converter<>() {
            @Override
            public OffsetDateTime convert(@NonNull String source) {
                return OffsetDateTime.parse(source);
            }
        };
    }
}
