package com.online.shoppinglist.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import java.io.InputStreamReader

/**
 * This class is used to read data from file
 */
object Helper {
    private val gson: Gson = GsonBuilder().create()

    /**
     * Read data from file
     */
    fun readFileResource(filename: String) : String{
        val inputStream = Helper::class.java.getResourceAsStream(filename)
        val builder = StringBuilder()
        val reader = InputStreamReader(inputStream, "UTF-8")
        reader.readLines().forEach {
            builder.append(it)
        }
        return builder.toString()
    }

    /**
     * Read data from json and assign to data class
     */
    fun <T> fromJson(json: String?, clazz: Class<T>): T? {
        if (json == null) {
            return null
        }

        try {
            return gson.fromJson(json, clazz)
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
            return null
        }
    }

    fun <T> toJson(data: T?): String {
        return gson.toJson(data)
    }
}