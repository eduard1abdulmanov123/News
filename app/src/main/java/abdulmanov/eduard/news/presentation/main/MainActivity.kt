package abdulmanov.eduard.news.presentation.main

import abdulmanov.eduard.news.R
import abdulmanov.eduard.news.data.network.NewsProvider
import abdulmanov.eduard.news.data.network.NewsXmlParser
import abdulmanov.eduard.news.domain.models.New
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}