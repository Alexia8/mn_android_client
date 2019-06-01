package com.alexiacdura.mn_core.core.factory.interceptors

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.net.URL

internal class UrlTypeAdapter : TypeAdapter<URL>() {

    override fun write(out: JsonWriter, url: URL?) {
        if (url != null) {
            out.value(url.toString())
        } else {
            out.nullValue()
        }
    }

    override fun read(json: JsonReader): URL? {
        return if (json.peek() == JsonToken.NULL) {
            json.skipValue()
            null
        } else {
            try {
                URL(json.nextString())
            } catch (ex: IllegalArgumentException) {
                null
            }
        }
    }
}