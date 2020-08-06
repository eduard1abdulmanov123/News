package abdulmanov.eduard.news.presentation._common.extensions

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.notifyObserver(){
    this.value = this.value
}