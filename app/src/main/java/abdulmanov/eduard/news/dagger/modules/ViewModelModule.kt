package abdulmanov.eduard.news.dagger.modules

import abdulmanov.eduard.news.dagger.annotations.ViewModelKey
import abdulmanov.eduard.news.presentation._common.base.ViewModelFactory
import abdulmanov.eduard.news.presentation.detailsnew.DetailsNewViewModel
import abdulmanov.eduard.news.presentation.news.NewsViewModel
import abdulmanov.eduard.news.presentation.news.filternewsdialog.FilterNewsViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(NewsViewModel::class)
    abstract fun bindNewsViewModel(viewModel: NewsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FilterNewsViewModel::class)
    abstract fun bindFilterNewsViewModel(viewModel: FilterNewsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsNewViewModel::class)
    abstract fun bindDetailsNewViewModel(viewModel: DetailsNewViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}