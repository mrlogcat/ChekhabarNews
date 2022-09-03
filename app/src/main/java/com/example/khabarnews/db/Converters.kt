package com.example.khabarnews.db

import androidx.room.TypeConverter
import javax.xml.transform.Source

class Converters {
    @TypeConverter
    fun fromSource(source: com.example.khabarnews.Source):String{
        return source.name
    }

    @TypeConverter
    fun toSource(name:String):com.example.khabarnews.Source{
        return com.example.khabarnews.Source(name,name)
    }
}