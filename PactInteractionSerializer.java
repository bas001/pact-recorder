package example.pact;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import java.io.File;
import java.io.IOException;

public class PactInteractionSerializer {

    private static final ObjectWriter writer;
    static {
        ObjectMapper objectMapper = new ObjectMapper();
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("pactPropertyFilter", new PactPropertyFilter());
        objectMapper.setFilterProvider(filterProvider);
        objectMapper.addMixIn(Object.class, PactPropertyFilterMixin.class);
        writer = objectMapper.writer();
    }

    public static String writeValueAsString(PactInteraction pactInteraction) {
        try {
            return writer.writeValueAsString(pactInteraction);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void writeValueToFile(PactInteraction pactInteraction, String path) {
        try {
            writer.withDefaultPrettyPrinter().writeValue(new File(path), pactInteraction);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
