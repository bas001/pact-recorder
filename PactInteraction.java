package example.pact;

import java.util.Objects;

public class PactInteraction {
    public PactRequest request = new PactRequest();
    public PactResponse response = new PactResponse();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PactInteraction)) return false;
        PactInteraction that = (PactInteraction) o;
        return Objects.equals(request, that.request) &&
                Objects.equals(response, that.response);
    }

    @Override
    public int hashCode() {

        return Objects.hash(request, response);
    }
}

