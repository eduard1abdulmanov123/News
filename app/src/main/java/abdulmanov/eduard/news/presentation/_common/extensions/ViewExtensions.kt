package abdulmanov.eduard.news.presentation._common.extensions

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

fun ImageView.loadImg(imageUrl: String, placeholderRes: Int? = null, errorRes: Int? = null, callback: Callback? = null) {
    if(imageUrl.isEmpty()){
        this.setImageResource(errorRes ?: 0)
        return
    }

    Picasso.get().load(imageUrl).apply {
        fit()
        centerCrop()
        placeholderRes?.let { placeholder(it) }
        errorRes?.let { error(it) }
        callback?.let { into(this@loadImg, callback) } ?: into(this@loadImg)
    }
}

fun ViewGroup.addViews(views: List<View>){
    views.forEach {
        this.addView(it)
    }
}

fun View.visibility(visible: Boolean, type:Int = View.GONE) {
    this.visibility = if (visible) View.VISIBLE else type
}
