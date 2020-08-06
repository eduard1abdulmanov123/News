package abdulmanov.eduard.news.dagger.modules

import abdulmanov.eduard.news.data.db.AppDatabase
import abdulmanov.eduard.news.data.db.dao.IdentifiersDao
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(context: Context): AppDatabase {
        return AppDatabase.getRoomDatabase(context)
    }

    @Singleton
    @Provides
    fun provideNewDao(database: AppDatabase): IdentifiersDao {
        return database.identifiersDao
    }
}