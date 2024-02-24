package org.luisangel.msvcoperation.router;

import org.luisangel.msvcoperation.handler.MovementHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class MovementRouter {

    private static final String PATH = "movimientos";

    @Bean
    RouterFunction<ServerResponse> movementRoute(MovementHandler handler) {
        return RouterFunctions.route()
                .GET(PATH, handler::getAll)
                .POST(PATH, handler::save)
                .PUT(PATH, handler::update)
                .DELETE(PATH.concat("/{id}"), handler::delete)
                .GET(PATH.concat("/filter"), handler::listByDateRangeAndClientId)
                .build();
    }

}
