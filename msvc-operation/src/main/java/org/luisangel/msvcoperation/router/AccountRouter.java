package org.luisangel.msvcoperation.router;

import org.luisangel.msvcoperation.handler.AccountHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class AccountRouter {

    private static final String PATH = "cuentas";

    @Bean
    RouterFunction<ServerResponse> accountRoute(AccountHandler handler){
        return RouterFunctions.route()
                .GET(PATH, handler::getAll)
                .GET(PATH.concat("/{id}"), handler::getById)
                .POST(PATH, handler::save)
                .PUT(PATH, handler::update)
                .DELETE(PATH.concat("/{id}"), handler::delete)
                .build();
    }

}
