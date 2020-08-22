package abdulmanov.eduard.news.domain.models.streams

data class TvChannel(
    val id: Long = -1,
    val name: String = "",
    val title: String = "",
    val urlLiveStream: String = "",
    var isSelected: Boolean = false
)