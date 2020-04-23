package example.pact;

import java.util.HashMap;
import java.util.Map;

public class PactResponse {
    public Integer status;
    public Map<String, String> headers = new HashMap<>();
    public Object body;

}
