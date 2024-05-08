package com.sixkids.data.util

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson

class UnitJsonAdapter : JsonAdapter<Unit>() {
    @ToJson
    override fun toJson(writer: JsonWriter, value: Unit?) {
        writer.nullValue()
    }

    @FromJson
    override fun fromJson(reader: JsonReader): Unit? {
        reader.skipValue()
        return Unit
    }

}
