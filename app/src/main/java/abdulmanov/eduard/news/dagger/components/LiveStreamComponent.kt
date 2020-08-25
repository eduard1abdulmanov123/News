package abdulmanov.eduard.news.dagger.components

import abdulmanov.eduard.news.dagger.annotations.ActivityScope
import abdulmanov.eduard.news.dagger.modules.livestream.LiveStreamDomainModule
import abdulmanov.eduard.news.dagger.modules.livestream.LiveStreamViewModelModule
import abdulmanov.eduard.news.presentation.livestream.LiveStreamActivity
import abdulmanov.eduard.news.presentation.livestream.changechanneldialog.ChangeTvChannelBottomSheetDialog
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [LiveStreamViewModelModule::class, LiveStreamDomainModule::class])
interface LiveStreamComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): LiveStreamComponent
    }

    fun inject(liveStreamActivity: LiveStreamActivity)

    fun inject(changeTvChannelBottomSheetDialog: ChangeTvChannelBottomSheetDialog)
}