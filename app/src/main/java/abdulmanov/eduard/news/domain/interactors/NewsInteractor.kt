package abdulmanov.eduard.news.domain.interactors

import abdulmanov.eduard.news.domain.models.New
import abdulmanov.eduard.news.domain.repositories.NewsRepository
import io.reactivex.Single

class NewsInteractor(private val newsRepository: NewsRepository) {

    fun getNews(): Single<List<New>> {
        return newsRepository.getNews()
    }

}