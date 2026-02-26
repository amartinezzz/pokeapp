package com.amartinez.pokeapp.data.local.converters

import androidx.room.TypeConverter
import com.amartinez.pokeapp.data.local.entity.AbilityEntity
import com.amartinez.pokeapp.data.local.entity.StatEntity
import com.amartinez.pokeapp.data.local.entity.TypeEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PokemonConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromAbilityList(value: List<AbilityEntity>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toAbilityList(value: String?): List<AbilityEntity>? {
        val listType = object : TypeToken<List<AbilityEntity>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromStatList(value: List<StatEntity>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toStatList(value: String?): List<StatEntity>? {
        val listType = object : TypeToken<List<StatEntity>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromTypeList(value: List<TypeEntity>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toTypeList(value: String?): List<TypeEntity>? {
        val listType = object : TypeToken<List<TypeEntity>>() {}.type
        return gson.fromJson(value, listType)
    }
}