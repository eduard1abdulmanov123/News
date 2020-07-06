package abdulmanov.eduard.news.presentation.news.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewPresentationModel(
    val id: Long,
    val title: String,
    val link: String,
    val description: String,
    val date: String,
    val category: String,
    val image: String,
    val fullDescription: String,
    var alreadyRead: Boolean
) : Parcelable