package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.*;
import java.util.Map;

public class FileSerializer implements Serializer {

    private final String fileName;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        try(OutputStream outputStream = new FileOutputStream(fileName)) {
            objectMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS,true);
            String jsonData = objectMapper.writeValueAsString(data);
            OutputStreamWriter streamWriter = new OutputStreamWriter(outputStream);
            streamWriter.write(jsonData);
            streamWriter.flush();
        }catch (JsonProcessingException e){
            throw new FileProcessException("Failed to process json");
        } catch (FileNotFoundException e){
            throw new FileProcessException("Failed to create file");
        } catch (IOException e){
            throw new FileProcessException("Failed to write to file");
        }
    }
}
