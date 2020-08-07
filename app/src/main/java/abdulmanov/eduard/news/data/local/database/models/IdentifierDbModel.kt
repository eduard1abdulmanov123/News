package abdulmanov.eduard.news.data.local.database.models

import abdulmanov.eduard.news.data.local.database.models.IdentifierDbModel.Companion.COLUMN_ID
import abdulmanov.eduard.news.data.local.database.models.IdentifierDbModel.Companion.TABLE_NAME
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = TABLE_NAME, primaryKeys = [COLUMN_ID])
data class IdentifierDbModel(
    @ColumnInfo(name = COLUMN_ID)
    val id: Long
) {
    companion object {
        const val TABLE_NAME = "Identifier"

        const val COLUMN_ID = "id"
    }
}