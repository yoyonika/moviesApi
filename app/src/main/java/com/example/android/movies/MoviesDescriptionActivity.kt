package com.example.android.movies

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_movies_description.*

class MoviesDescriptionActivity : AppCompatActivity() {


    companion object {
        const val KEY_TITLE = "title"
        const val KEY_DESCRIPTION = "overview"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_description)

        // get data from intent
          var title = ""
        var description= ""

        if (intent.hasExtra(KEY_TITLE)){
            title = intent.extras.getString(KEY_TITLE)
        }

        if(intent.hasExtra(KEY_DESCRIPTION)){
            description= intent.extras.getString(KEY_DESCRIPTION)
        }

        setTitle(title)
        movie_description.text = description
    }
}
