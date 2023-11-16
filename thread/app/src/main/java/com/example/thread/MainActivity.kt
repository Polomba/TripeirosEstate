package com.example.thread

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private class LongOperation : AsyncTask<String?, Void?, String>() {

        override fun doInBackground(vararg p0: String?): String {
            return "Executed"
        }
        override fun onPostExecute(result: String) {}
        override fun onPreExecute() {}
        override fun onProgressUpdate(vararg values: Void?) {}
    }
}