package abdulmanov.eduard.news.dagger.modules.news

import abdulmanov.eduard.news.dagger.annotations.FragmentScope
import abdulmanov.eduard.news.presentation.navigation.CiceroneConstants
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Named

@Module
class NewsNavigationModule {
    private val cicerone: Cicerone<Router> = Cicerone.create()

    @FragmentScope
    @Provides
    @Named(CiceroneConstants.NEWS_ROUTER)
    fun provideRouter(): Router {
        return cicerone.router
    }

    @FragmentScope
    @Provides
    @Named(CiceroneConstants.NEWS_NAVIGATOR_HOLDER)
    fun provideNavigationHolder(): NavigatorHolder {
        return cicerone.navigatorHolder
    }
}