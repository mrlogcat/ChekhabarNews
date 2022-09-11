package com.example.khabarnews.db

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromSource(source: com.example.khabarnews.models.Source):String{
        return source.name!!
    }

    @TypeConverter
    fun toSource(name:String): com.example.khabarnews.models.Source {
        return com.example.khabarnews.models.Source(name, name)
    }
}