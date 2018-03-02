package pl.javaserver.HelloServer;

import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.ipc.netty.http.server.HttpServer;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;


public class HelloServerApplication {



    public static void main(String[] args) {
        new HelloServerApplication().serve();
    }

    private void serve(){
        RouterFunction route = route(GET("/api/time"),
                getTime());

        HttpHandler httpHandler = RouterFunctions.toHttpHandler(route);
        HttpServer server = HttpServer.create("localhost", 8080);
        server.startAndAwait(new ReactorHttpHandlerAdapter(httpHandler));
    }
    private HandlerFunction<ServerResponse> getTime() {
        return request -> {
            LocalDateTime currentTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

            return ServerResponse.ok()
                    .contentType(new MediaType(MediaType.TEXT_PLAIN, Charset.forName("utf-8")))
                    .body(fromObject(formatter.format(currentTime)));
        };
    }
}
