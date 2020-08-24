package abdulmanov.eduard.news.presentation._common.extensions

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment

fun Fragment.addOnBackPressedCallback(handlerOnBackPressed: () -> Unit) {
    requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            handlerOnBackPressed.invoke()
        }
    })
}