package com.example.moviemasalahub

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.moviemasalahub.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        fetchData("pathaan")
        SearchMovie()


    }
    private fun SearchMovie() {
        val searchView = binding.search
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    fetchData(query)
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun fetchData(moviename:String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.omdbapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiInterface::class.java)
        val response = retrofit.getMovieData("//Your Api Key", moviename)

        response.enqueue(object : Callback<MovieApp?> {
            override fun onResponse(call: Call<MovieApp?>, response: Response<MovieApp?>) {
                   val responceBody=  response.body()

                if (response.isSuccessful && responceBody != null) {

                    val name = responceBody.Title
                    binding.title.text = name

                    handleApiResponse(responceBody)


                    val relese = responceBody.Released
                    binding.relese.text = "Relased Date :$relese"

                    val type = responceBody.Genre
                    binding.type.text = "Type :$type"

                    val actor = responceBody.Actors
                    binding.actor.text = "Cast :$actor"

                    val plot = responceBody.Plot
                    binding.plot.text = "Plot :$plot"

                    val lang = responceBody.Language
                    binding.langauge.text = "Langauge :$lang"

                    val contry = responceBody.Country
                    binding.contry.text = "Contry :$contry"

                    val awads = responceBody.Awards
                    binding.awards.text = "Awards :$awads"

                    val rate = responceBody.imdbRating
                    binding.rating.text = "IMDB Rating :$rate"



                }
            }

            override fun onFailure(call: Call<MovieApp?>, t: Throwable) {
                Log.d("Main Activity", "OnFailure " + t.message)
            }
        })

    }
    fun handleApiResponse(movieApp: MovieApp) {
        Picasso.get().load(movieApp.Poster).into(binding.poster)
    }
}


