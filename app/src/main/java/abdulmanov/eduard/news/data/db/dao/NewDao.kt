package abdulmanov.eduard.news.data.db.dao

import abdulmanov.eduard.news.data.db.models.NewDbModel
import androidx.room.*

@Dao
abstract class NewDao {

    @Transaction
    open fun getNewsByPage(page:Int): List<NewDbModel>{
        val offset = (page - 1)* ITEMS_PER_PAGE
        return getNews(offset)
    }

    @Query("SELECT * FROM ${NewDbModel.TABLE_NAME} ORDER BY ${NewDbModel.COLUMN_DATE} DESC LIMIT $ITEMS_PER_PAGE OFFSET :offset")
    abstract fun getNews(offset: Int): List<NewDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertNews(news: List<NewDbModel>)

    @Query("DELETE FROM ${NewDbModel.TABLE_NAME} WHERE date(${NewDbModel.COLUMN_DATE})<=date('now','localtime','-${NUMBER_DAYS_AFTER_WHICH_NEWS_CONSIDERED_OLD} day')")
    abstract fun deleteOldNews()

    companion object{
        const val ITEMS_PER_PAGE = 50
        const val NUMBER_DAYS_AFTER_WHICH_NEWS_CONSIDERED_OLD = 7
    }
}