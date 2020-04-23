package example.pact;

import java.util.HashMap;
import java.util.Map;

public class PactRequest {
    public String method;
    public String path;
    public Map<String, String> headers = new HashMap<>();
    public Object body;
}
