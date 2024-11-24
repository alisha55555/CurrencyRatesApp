package com.example.currencyratesapp

import android.os.AsyncTask
import org.json.JSONObject
import java.net.URL

class DataLoader(private val callback: (List<String>) -> Unit) : AsyncTask<String, Void, List<String>>() {

    override fun doInBackground(vararg params: String?): List<String> {
        return try {
            val url = params[0] ?: throw Exception("URL not provided")
            val result = URL(url).readText()
            val parser = Parser()
            parser.parseJSON(result)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    override fun onPostExecute(result: List<String>) {
        super.onPostExecute(result)
        callback(result)
    }
}
