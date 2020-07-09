package br.com.dititalinnovation.restfull.config;

import br.com.dititalinnovation.restfull.enums.Raca;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.PackageVersion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class Jackson {

    @Bean
    public ObjectMapper  objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        //propriedades não mapeadas, não quebram
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //falha se alguma propriedade estiver vazia
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //serve para compatibilidade de arrays, quando tem um array com um item , caso não tenha essa config ele se perde
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        //serializa datas
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(racaModulemapper());
        return objectMapper;
    }
    public SimpleModule racaModulemapper(){
        SimpleModule dataModule= new SimpleModule("JSONRacaModule", PackageVersion.VERSION);
        dataModule.addSerializer(Raca.class, new RacaCerialize());
        dataModule.addDeserializer(Raca.class, new RacaDescerialize());
        return dataModule;
    }
    class RacaCerialize extends StdSerializer<Raca>{
        public RacaCerialize(){
            super(Raca.class);
        }

        @Override
        public void serialize(Raca raca, JsonGenerator json, SerializerProvider provider) throws IOException {

            json.writeString(raca.getValue());
        }

    }
    class RacaDescerialize extends StdDeserializer<Raca> {
        public RacaDescerialize(){
            super(Raca.class);
        }

        @Override
        public Raca deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            String value = p.getText();
            return Raca.of(p.getText());
        }
    }
}
