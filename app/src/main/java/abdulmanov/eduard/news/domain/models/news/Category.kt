package abdulmanov.eduard.news.domain.models.news

data class Category(
    val name: String,
    var selected: Boolean = false
)