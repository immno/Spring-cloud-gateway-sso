package com.github.mno.auth.exts;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * oauth2
 *
 * @author mno
 * @date 2019/2/2 15:38
 */
class CustomOauthExceptionSerializer extends StdSerializer<CustomOauthException> {

    private static final long serialVersionUID = 8237906895781075653L;

    protected CustomOauthExceptionSerializer() {
        super(CustomOauthException.class);
    }

    @Override
    public void serialize(CustomOauthException value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeObject(value.getResultDTO());
    }
}
