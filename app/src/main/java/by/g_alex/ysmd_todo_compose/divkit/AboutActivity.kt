package by.g_alex.ysmd_todo_compose.divkit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.g_alex.ysmd_todo_compose.R
import by.g_alex.ysmd_todo_compose.databinding.ActivityMainPageBinding
import com.yandex.div.core.Div2Context
import com.yandex.div.core.DivConfiguration
import com.yandex.div.picasso.PicassoDivImageLoader
import com.yandex.div.rive.OkHttpDivRiveNetworkDelegate
import com.yandex.div.rive.RiveCustomViewAdapter
import com.yandex.div.zoom.DivPinchToZoomConfiguration
import com.yandex.div.zoom.DivPinchToZoomExtensionHandler
import okhttp3.OkHttpClient

class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainPageBinding
    private val assetReader = AssetReader(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title

        val divJson = assetReader.read("about.json")
        val templatesJson = divJson.optJSONObject("templates")
        val cardJson = divJson.getJSONObject("card")

        val divContext = Div2Context(
            baseContext = this,
            configuration = createDivConfiguration(),
            lifecycleOwner = this
        )

        val divView = Div2ViewFactory(divContext, templatesJson).createView(cardJson)
        binding.list.addView(divView)
    }

    private fun createDivConfiguration(): DivConfiguration {
        return DivConfiguration.Builder(PicassoDivImageLoader(this))
            .actionHandler(SampleDivActionHandler { super.onBackPressedDispatcher.onBackPressed() })
            .extension(
                DivPinchToZoomExtensionHandler(
                    DivPinchToZoomConfiguration.Builder(this).build()
                )
            )
            .divCustomContainerViewAdapter(
                RiveCustomViewAdapter.Builder(
                    this,
                    OkHttpDivRiveNetworkDelegate(OkHttpClient.Builder().build())
                ).build()
            )
            .visualErrorsEnabled(true)
            .build()
    }
}
