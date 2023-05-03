package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import ru.otus.model.Measurement;

import java.io.*;
import java.util.List;
import java.util.Optional;

public class ResourcesFileLoader implements Loader {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String fileName;
    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
        try (InputStream resourceStream = getClass().getClassLoader().getResourceAsStream(fileName)){
            Optional<InputStream> mayBeStream = Optional.ofNullable(resourceStream);
            InputStreamReader reader = new InputStreamReader(mayBeStream.orElseThrow());
            SimpleModule module = new SimpleModule();
            JavaType measurementClassList = objectMapper.getTypeFactory().constructCollectionType(List.class, Measurement.class);
            module.addDeserializer(Measurement.class, new MeasurementCustomDeserializer());
            objectMapper.registerModule(module);
            return objectMapper.readValue(reader, measurementClassList);
        } catch (IOException e){
            throw new FileProcessException(e);
        }
    }
}
