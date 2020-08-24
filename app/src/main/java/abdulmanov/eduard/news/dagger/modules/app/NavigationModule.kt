package abdulmanov.eduard.news.dagger.modules.app

import abdulmanov.eduard.news.presentation.navigation.NavigationConstants
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Named
import javax.inject.Singleton

@Module
class NavigationModule {

    private val cicerone: Cicerone<Router> = Cicerone.create()

    @Singleton
    @Provides
    @Named(NavigationConstants.MAIN_ROUTER)
    fun provideRouter(): Router {
        return cicerone.router
    }

    @Singleton
    @Provides
    @Named(NavigationConstants.MAIN_NAVIGATOR_HOLDER)
    fun provideNavigationHolder(): NavigatorHolder {
        return cicerone.navigatorHolder
    }

}