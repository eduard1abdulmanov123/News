package abdulmanov.eduard.news.data.db

import abdulmanov.eduard.news.data.db.dao.NewDao
import abdulmanov.eduard.news.data.db.models.NewDbModel
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NewDbModel::class], version = 1)

abstract class AppDatabase : RoomDatabase() {

    abstract val newDao: NewDao

    companion object {
        private const val DATABASE_NAME = "news_database"

        fun getRoomDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
        }
    }
}