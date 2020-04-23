package example.pact;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;

import java.util.ArrayList;
import java.util.List;

public class PactPropertyFilter extends SimpleBeanPropertyFilter {

    private static List<String> blacklist = new ArrayList<>();

    static {
        // todo config file
        blacklist.add("foo");
    }

    @Override
    public void serializeAsField(Object pojo, JsonGenerator jgen,
                                 SerializerProvider provider,
                                 PropertyWriter writer) throws Exception {

        if (blacklist.contains(writer.getName())) {
            writer.serializeAsOmittedField(pojo, jgen, provider);
        } else {
            writer.serializeAsField(pojo, jgen, provider);
        }
    }
}
