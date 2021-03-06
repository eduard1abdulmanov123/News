package abdulmanov.eduard.news.presentation.navigation

import abdulmanov.eduard.news.R
import abdulmanov.eduard.news.domain.models.feedback.FeedbackData
import abdulmanov.eduard.news.presentation.detailsnew.DetailsNewFragment
import abdulmanov.eduard.news.presentation.livestream.LiveStreamActivity
import abdulmanov.eduard.news.presentation.news.NewsFragment
import abdulmanov.eduard.news.presentation.news.models.NewPresentationModel
import abdulmanov.eduard.news.presentation.newshostcontainer.NewsHostContainerFragment
import abdulmanov.eduard.news.presentation.setting.SettingFragment
import android.content.Context
import android.content.Intent
import android.net.Uri
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object NewsHostContainer : SupportAppScreen() {
        override fun getFragment() = NewsHostContainerFragment.newInstance()
    }

    object News : SupportAppScreen() {
        override fun getFragment() = NewsFragment.newInstance()
    }

    data class DetailsNew(val new: NewPresentationModel) : SupportAppScreen() {
        override fun getFragment() = DetailsNewFragment.newInstance(new)
    }

    data class LiveStream(val context: Context) : SupportAppScreen() {
        override fun getActivityIntent(context: Context) = LiveStreamActivity.getIntent(context)
    }

    object Setting : SupportAppScreen() {
        override fun getFragment() = SettingFragment.newInstance()
    }

    data class Feedback(val data: FeedbackData) : SupportAppScreen() {

        override fun getActivityIntent(context: Context): Intent? {
            val addresses = listOf(context.getString(R.string.feedback_address)).toTypedArray()
            val subject = context.getString(R.string.feedback_subject)
            val body = context.getString(R.string.feedback_body, data.androidVersion, data.modelPhone, data.applicationVersion)

            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:")).apply {
                putExtra(Intent.EXTRA_EMAIL, addresses)
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, body)
            }

            return Intent.createChooser(intent, "Email:")
        }
    }

    data class Website(val url: String) : SupportAppScreen() {
        override fun getActivityIntent(context: Context) = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    }

    data class SendData(val data: String) : SupportAppScreen() {

        override fun getActivityIntent(context: Context): Intent? {
            return Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, this@SendData.data)
            }
        }
    }
}