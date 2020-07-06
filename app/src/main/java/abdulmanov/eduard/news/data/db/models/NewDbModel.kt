package abdulmanov.eduard.news.data.db.models

import abdulmanov.eduard.news.data.db.models.NewDbModel.Companion.COLUMN_ID
import abdulmanov.eduard.news.data.db.models.NewDbModel.Companion.TABLE_NAME
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = TABLE_NAME, primaryKeys = [COLUMN_ID])
data class NewDbModel(
    @ColumnInfo(name = COLUMN_ID)
    val id: Long,

    @ColumnInfo(name = COLUMN_TITLE)
    val title: String,

    @ColumnInfo(name = COLUMN_LINK)
    val link: String,

    @ColumnInfo(name = COLUMN_DESCRIPTION)
    val description: String,

    @ColumnInfo(name = COLUMN_DATE)
    val date: String,

    @ColumnInfo(name = COLUMN_CATEGORY)
    val category: String,

    @ColumnInfo(name = COLUMN_IMAGE)
    val image: String,

    @ColumnInfo(name = COLUMN_FULL_DESCRIPTION)
    val fullDescription: String
) {
    companion object {
        const val TABLE_NAME = "New"

        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_LINK = "link"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_DATE = "publication_date"
        const val COLUMN_CATEGORY = "category"
        const val COLUMN_IMAGE = "image"
        const val COLUMN_FULL_DESCRIPTION = "full_description"
    }
}