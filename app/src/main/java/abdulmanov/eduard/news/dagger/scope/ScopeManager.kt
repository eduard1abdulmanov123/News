package abdulmanov.eduard.news.dagger.scope

import abdulmanov.eduard.news.dagger.components.AppComponent
import abdulmanov.eduard.news.dagger.components.LiveStreamComponent
import abdulmanov.eduard.news.dagger.components.SettingComponent

class ScopeManager(private val appComponent: AppComponent) {

    private var liveStreamComponent: LiveStreamComponent? = null

    private var settingComponent: SettingComponent? = null

    fun attachLiveStreamComponent(): LiveStreamComponent {
        if(liveStreamComponent == null){
            liveStreamComponent = appComponent.liveStreamComponent().create()
        }
        return liveStreamComponent!!
    }

    fun detachLiveStreamComponent() {
        liveStreamComponent = null
    }

    fun attachSettingComponent(): SettingComponent {
        if(settingComponent == null){
            settingComponent = appComponent.settingComponent().create()
        }
        return settingComponent!!
    }

    fun detachSettingComponent() {
        settingComponent = null
    }
}