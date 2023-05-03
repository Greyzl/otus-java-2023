package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.TextNode;
import ru.otus.model.Measurement;

import java.io.IOException;

public class MeasurementCustomDeserializer extends JsonDeserializer<Measurement> {
    @Override
    public Measurement deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        TreeNode node = p.getCodec().readTree(p);
        String name = ((TextNode)node.get("name")).asText();
        double value = ((DoubleNode)(node.get("value"))).doubleValue();
        return new Measurement(name, value);
    }
}
