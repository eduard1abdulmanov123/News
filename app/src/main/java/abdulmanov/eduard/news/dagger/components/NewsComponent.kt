package abdulmanov.eduard.news.dagger.components

import abdulmanov.eduard.news.dagger.annotations.FragmentScope
import abdulmanov.eduard.news.dagger.modules.news.NewsDomainModule
import abdulmanov.eduard.news.dagger.modules.news.NewsNavigationModule
import abdulmanov.eduard.news.dagger.modules.news.NewsViewModelModule
import abdulmanov.eduard.news.presentation.detailsnew.DetailsNewFragment
import abdulmanov.eduard.news.presentation.news.NewsFragment
import abdulmanov.eduard.news.presentation.news.filternewsdialog.FilterNewsBottomSheetDialog
import abdulmanov.eduard.news.presentation.newshostcontainer.NewsHostContainerFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [NewsViewModelModule::class, NewsDomainModule:: class, NewsNavigationModule::class])
interface NewsComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create():NewsComponent
    }

    fun inject(newsHostContainerFragment: NewsHostContainerFragment)

    fun inject(newsFragment: NewsFragment)

    fun inject(filterNewsBottomSheetDialog: FilterNewsBottomSheetDialog)

    fun inject(detailsNewFragment: DetailsNewFragment)
}