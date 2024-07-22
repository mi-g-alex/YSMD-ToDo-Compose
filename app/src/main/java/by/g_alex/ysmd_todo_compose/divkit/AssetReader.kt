@file:OptIn(InternalApi::class)

package by.g_alex.ysmd_todo_compose.divkit

import android.content.Context
import com.yandex.div.core.annotations.InternalApi
import com.yandex.div.internal.util.IOUtils
import org.json.JSONObject

class AssetReader(private val context: Context) {

    fun read(filename: String): JSONObject {
        val data = IOUtils.toString(context.assets.open(filename))
        return JSONObject(data)
    }
}
