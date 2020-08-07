package abdulmanov.eduard.news.presentation._common.extensions

import android.widget.ImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

fun ImageView.loadImg(imageUrl: String, placeholderRes: Int? = null, callback: Callback? = null) {
    Picasso.get().load(imageUrl).apply {
        fit()
        centerCrop()
        placeholderRes?.let { placeholder(it) }
        callback?.let { into(this@loadImg, callback) } ?: into(this@loadImg)
    }
}
