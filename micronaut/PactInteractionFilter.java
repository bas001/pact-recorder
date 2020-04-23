package example.pact.micronaut;

import example.pact.PactInteraction;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.HttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Publisher;

import javax.inject.Inject;

import static example.pact.PactInteractionSerializer.writeValueAsString;


@Filter({"/**"})
public class PactInteractionFilter implements HttpServerFilter {

    @Inject
    PactInteractionService service;

    @Override
    public Publisher<MutableHttpResponse<?>> doFilter(HttpRequest<?> request, ServerFilterChain chain) {
        return wrappedServiceCall(request)
                .switchMap(aBoolean -> chain.proceed(request))
                .doOnNext(res -> {
                    PactInteraction interaction = service.addResponse(res, request);
                    String json = writeValueAsString(interaction);
                    System.out.println(json);
                });
    }

    private Flowable<?> wrappedServiceCall(HttpRequest<?> request) {
        return Flowable.fromCallable(() -> {
            service.insertRequest(request);
            return true;
        }).subscribeOn(Schedulers.io());
    }

}
