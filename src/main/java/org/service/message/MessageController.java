package org.service.message;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.http.annotation.Error;
import io.micronaut.validation.Validated;
import jakarta.inject.Inject;
import org.service.message.dto.MessageSNS;

import javax.validation.Valid;
import java.io.IOException;

@Controller("/hello")
@Validated
public class MessageController {
    @Get
    @Produces(MediaType.APPLICATION_JSON)
    public String index(){
        return "It is working";
    }

    @Inject
    SnsPublisherService snsPublisherService;

    @Post
    @Consumes(MediaType.APPLICATION_JSON)
    public HttpResponse<String> echo(@Body MessageSNS message) throws IOException {
        return HttpResponse.ok(snsPublisherService.sendMessage(message));
    }

    @Error(global = true, exception = IOException.class)
    @Status(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleIOException(IOException e) {
        return e.getMessage();
    }

    @Error(global = true, exception = IllegalArgumentException.class)
    @Status(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleIllegalArgumentException(IllegalArgumentException e) {
        return e.getMessage();
    }
}
