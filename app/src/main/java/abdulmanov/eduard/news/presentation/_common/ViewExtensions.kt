package abdulmanov.eduard.news.presentation._common

import android.content.Context
import android.graphics.Point
import android.view.WindowManager
import android.widget.ImageView
import com.squareup.picasso.Picasso

fun Context.getScreenSize(): Point {
    val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    return Point().apply {
        wm.defaultDisplay.getSize(this)
    }
}

fun ImageView.loadImg(imageUrl: String, placeholderRes: Int? = null) {
    Picasso.get().load(imageUrl).apply {
        placeholderRes?.let { placeholder(it) }
        fit().into(this@loadImg)
    }
}