package com.example.currencyratesapp

import org.json.JSONObject

class Parser {
    fun parseJSON(jsonData: String): List<String> {
        val json = JSONObject(jsonData)
        val rates = json.getJSONObject("rates")
        val currencyList = mutableListOf<String>()

        rates.keys().forEach { key ->
            val rate = rates.getDouble(key)
            currencyList.add("$key - $rate")
        }

        return currencyList
    }
}
