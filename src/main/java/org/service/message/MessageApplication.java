package org.service.message;

import io.micronaut.runtime.Micronaut;
import io.micronaut.serde.annotation.SerdeImport;
import org.service.message.dto.MessageSNS;

@SerdeImport(MessageSNS.class)
public class MessageApplication {
    public static void main(String[] args) {
        Micronaut.run(MessageApplication.class, args);
    }
}