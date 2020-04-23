package example.pact.micronaut;

import example.pact.PactInteraction;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpResponse;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class PactInteractionService {

    private static final Map<String, PactInteraction> interactions = new HashMap<>();

    PactInteraction insertRequest(HttpRequest<?> request) {
        PactInteraction pactInteraction = new PactInteraction();
        pactInteraction.request.method = request.getMethodName();
        pactInteraction.request.path = request.getPath();
        pactInteraction.request.headers = flatten(request.getHeaders().asMap());
        if (request.getBody().isPresent()) {
            pactInteraction.request.body = request.getBody().get();
        }
        interactions.put(requestKey(request), pactInteraction);
        return pactInteraction;
    }

    PactInteraction addResponse(MutableHttpResponse<?> response, HttpRequest<?> request) {
        PactInteraction pactInteraction = interactions.get(requestKey(request));
        if (pactInteraction == null) {
            return null;
        }
        pactInteraction.response.status = response.getStatus().getCode();
        pactInteraction.response.headers = flatten(response.getHeaders().asMap());
        if (response.getBody().isPresent()) {
            pactInteraction.response.body = response.getBody().get();
        }
        return pactInteraction;
    }

    private Map<String, String> flatten(Map<String, List<String>> stringListMap) {
        Map<String, String> flattened = new HashMap<>();
        stringListMap.forEach((key, value) -> flattened.put(key, value.stream().findFirst().orElse("")));
        return flattened;
    }

    private String requestKey(HttpRequest<?> request) {
        return request.getMethod() + request.getPath();
    }

}

