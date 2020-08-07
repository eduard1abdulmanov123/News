package abdulmanov.eduard.news.data.local.database.dao

import abdulmanov.eduard.news.data.local.database.models.IdentifierDbModel
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
abstract class IdentifiersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertIdentifier(identifier: IdentifierDbModel)

    @Query("SELECT EXISTS (SELECT * FROM ${IdentifierDbModel.TABLE_NAME} WHERE ${IdentifierDbModel.COLUMN_ID} = :id)")
    abstract fun isIdentifierExist(id: Long): Boolean

    @Query("DELETE FROM ${IdentifierDbModel.TABLE_NAME} WHERE ${IdentifierDbModel.COLUMN_ID} not in (:ids)")
    abstract fun deleteIdentifiersThatAreMissing(ids:List<Long>)
}