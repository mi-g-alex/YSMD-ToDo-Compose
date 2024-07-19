package by.g_alex.ysmd_todo_compose.divkit

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.yandex.div.core.DivActionHandler
import com.yandex.div.core.DivViewFacade
import com.yandex.div.json.expressions.ExpressionResolver
import com.yandex.div2.DivAction

class SampleDivActionHandler (
    private val onBackPressed: () -> Unit
) : DivActionHandler() {
    override fun handleAction(
        action: DivAction,
        view: DivViewFacade,
        resolver: ExpressionResolver
    ): Boolean {
        val url =
            action.url?.evaluate(resolver) ?: return super.handleAction(action, view, resolver)

        return if (url.scheme == SCHEME_SAMPLE && handleGoBack(url, view.view.context)) {
            true
        } else {
            super.handleAction(action, view, resolver)
        }
    }

    private fun handleGoBack(action: Uri, context: Context): Boolean {
        return when (action.host) {
            "go-back" -> {
                onBackPressed()
                return true
            }
            "go-to" -> {
                val i = Intent(Intent.ACTION_VIEW)
                val link = action.query
                i.data = Uri.parse(link)
                context.startActivity(i)
                return true
            }
            else -> false
        }
    }

    companion object {
        const val SCHEME_SAMPLE = "my-action"
    }
}
