package com.example.khabarnews.db

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromSource(source: com.example.khabarnews.network.dataModel.Source):String{
        return source.name!!
    }

    @TypeConverter
    fun toSource(name:String): com.example.khabarnews.network.dataModel.Source {
        return com.example.khabarnews.network.dataModel.Source(name, name)
    }
}