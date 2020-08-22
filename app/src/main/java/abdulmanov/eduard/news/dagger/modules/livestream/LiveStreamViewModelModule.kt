package abdulmanov.eduard.news.dagger.modules.livestream

import abdulmanov.eduard.news.dagger.annotations.ViewModelKey
import abdulmanov.eduard.news.presentation.livestream.LiveStreamViewModel
import abdulmanov.eduard.news.presentation.livestream.changechanneldialog.ChangeTvChannelViewModel
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class LiveStreamViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LiveStreamViewModel::class)
    abstract fun bindLiveStreamViewModel(viewModelLive: LiveStreamViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChangeTvChannelViewModel::class)
    abstract fun bindChangeTvChannelViewModel(viewModel: ChangeTvChannelViewModel): ViewModel

}