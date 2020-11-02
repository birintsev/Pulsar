package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers;

import io.javalin.http.Context;
import org.apache.log4j.Logger;
import org.eclipse.jetty.http.HttpStatus;
import org.jetbrains.annotations.NotNull;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.JsonHttpResponseException;

/**
 * This is a class for catching unmapped exceptions
 * happening during a request handling
 * */
public class ExceptionHandler
implements io.javalin.http.ExceptionHandler<Exception> {

    private static final Logger LOGGER = Logger.getLogger(
        ExceptionHandler.class
    );

    @Override
    public void handle(@NotNull Exception exception, @NotNull Context ctx) {
        LOGGER.error(exception);
        throw new JsonHttpResponseException(
            HttpStatus.Code.INTERNAL_SERVER_ERROR.getCode(),
            "Internal server error,"
                + " please contact your administration"
        );
    }
}
