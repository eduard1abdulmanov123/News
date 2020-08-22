package abdulmanov.eduard.news.dagger

import abdulmanov.eduard.news.dagger.components.AppComponent
import abdulmanov.eduard.news.dagger.components.LiveStreamComponent

class ScopeManager(private val appComponent: AppComponent) {

    private var liveStreamComponent: LiveStreamComponent? = null

    fun attachLiveStreamComponent(): LiveStreamComponent {
        if(liveStreamComponent == null){
            liveStreamComponent = appComponent.liveStreamComponent().create()
        }
        return liveStreamComponent!!
    }

    fun detachLiveStreamComponent() {
        liveStreamComponent = null
    }
}