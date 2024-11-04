package com.github.gsantosc18.authorizer.infrastructure.serializer

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.github.gsantosc18.authorizer.domain.vo.ResponseCode

class ResponseCodeSerializer: StdSerializer<ResponseCode>(ResponseCode::class.java) {
    override fun serialize(value: ResponseCode?, jgen: JsonGenerator?, provider: SerializerProvider?) {
        value?.code?.also{ jgen?.writeString(it) }
    }
}