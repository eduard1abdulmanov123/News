package abdulmanov.eduard.news.dagger.modules.app

import abdulmanov.eduard.news.presentation._common.base.ViewModelFactory
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}