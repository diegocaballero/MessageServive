package org.service.message.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.validation.Validated;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import jakarta.validation.constraints.NotBlank;


@Introspected
@Getter
@Setter
@Serdeable.Serializable
@Validated
public class MessageSNS {
    private String description;
    @NotBlank(message = "Name must not be blank")
    @NotNull
    private String type;
    private String status;
    private String message;
    private String date;
}
