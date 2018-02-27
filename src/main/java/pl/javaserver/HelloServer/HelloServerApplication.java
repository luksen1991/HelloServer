package pl.javaserver.HelloServer;

import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.ipc.netty.http.server.HttpServer;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;


public class HelloServerApplication {

	public static void main(String[] args) {

		RouterFunction route = route( GET("/"),
				request -> ServerResponse.ok().body(fromObject("Hello World Server")));
		HttpHandler httpHandler = RouterFunctions.toHttpHandler(route);
		HttpServer server = HttpServer.create("localhost",8080);
		server.startAndAwait(new ReactorHttpHandlerAdapter(httpHandler));
	}
}
