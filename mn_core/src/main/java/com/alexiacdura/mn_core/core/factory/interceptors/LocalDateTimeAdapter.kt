package com.alexiacdura.mn_core.core.factory.interceptors

import com.alexiacdura.mn_core.resources.Constants
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

internal class LocalDateTimeTypeAdapter : TypeAdapter<LocalDateTime>() {

    override fun write(out: JsonWriter, dateTime: LocalDateTime?) {
        if (dateTime != null) {
            out.value(dateTime.toString())
        } else {
            out.nullValue()
        }
    }

    override fun read(json: JsonReader): LocalDateTime? {
        val dateTimeFormatter = DateTimeFormatter.ofPattern(Constants.DATE_TIME_PATTERN)
        return if (json.peek() == JsonToken.NULL) {
            json.skipValue()
            null
        } else {
            try {
                LocalDateTime.parse(json.nextString(), dateTimeFormatter)
            } catch (ex: IllegalArgumentException) {
                null
            }
        }
    }
}