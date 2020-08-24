package abdulmanov.eduard.news.dagger.modules.news

import abdulmanov.eduard.news.dagger.annotations.ViewModelKey
import abdulmanov.eduard.news.presentation.detailsnew.DetailsNewViewModel
import abdulmanov.eduard.news.presentation.news.NewsViewModel
import abdulmanov.eduard.news.presentation.news.filternewsdialog.FilterNewsViewModel
import abdulmanov.eduard.news.presentation.newshostcontainer.NewsHostContainerViewModel
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class NewsViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(NewsHostContainerViewModel::class)
    abstract fun bindNewsHostContainerViewModel(viewModel: NewsHostContainerViewModel): ViewModel

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

}