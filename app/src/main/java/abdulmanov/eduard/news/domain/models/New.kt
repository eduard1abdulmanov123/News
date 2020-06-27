package abdulmanov.eduard.news.domain.models

data class New(
    val title: String,
    val link: String,
    val description: String,
    val date: String,
    val category: String,
    val image: String,
    val fullDescription: String
)