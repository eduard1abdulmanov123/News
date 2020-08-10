package abdulmanov.eduard.news.data.local.database

import abdulmanov.eduard.news.data.local.database.dao.IdentifiersDao
import abdulmanov.eduard.news.data.local.database.models.IdentifierDbModel
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [IdentifierDbModel::class], version = 1)

abstract class AppDatabase : RoomDatabase() {

    abstract val identifiersDao: IdentifiersDao

    companion object {
        private const val DATABASE_NAME = "news_database"

        fun getRoomDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
        }
    }
}