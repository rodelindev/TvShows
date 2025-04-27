package com.rodelindev.tvshows.data.local.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rodelindev.tvshows.data.local.entities.SeasonEntity

/*@ProvidedTypeConverter
class SeasonConverter {

    private val moshi: Gson = Gson.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val seasonListAdapter: JsonAdapter<List<SeasonEntity>> =
        moshi.adapter(Types.newParameterizedType(List::class.java, SeasonEntity::class.java))

    @TypeConverter
    fun fromSeasonList(value: List<SeasonEntity>): String {
        return seasonListAdapter.toJson(value)
    }

    @TypeConverter
    fun toSeasonList(value: String?): List<SeasonEntity> {
        return value?.let { seasonListAdapter.fromJson(it) } ?: emptyList()
    }
}*/

@ProvidedTypeConverter
class SeasonConverter {

    private val gson = Gson()
    private val type = object : TypeToken<List<SeasonEntity>>() {}.type

    @TypeConverter
    fun fromSeasonList(value: List<SeasonEntity>): String {
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toSeasonList(value: String?): List<SeasonEntity> {
        return value?.let { gson.fromJson(it, type) } ?: emptyList()
    }
}