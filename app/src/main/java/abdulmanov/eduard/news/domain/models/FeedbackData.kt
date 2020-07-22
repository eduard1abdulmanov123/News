package abdulmanov.eduard.news.domain.models

import javax.security.auth.Subject

data class FeedbackData(
    val androidVersion:Int,
    val modelPhone:String,
    val applicationVersion:String
)