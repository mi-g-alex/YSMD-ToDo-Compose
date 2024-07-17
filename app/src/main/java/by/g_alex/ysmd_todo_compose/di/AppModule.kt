package by.g_alex.ysmd_todo_compose.di

import android.content.Context
import android.content.res.Resources.Theme
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import by.g_alex.ysmd_todo_compose.common.Constants
import by.g_alex.ysmd_todo_compose.data.repository.FakeFakeToDoRepositoryImpl
import by.g_alex.ysmd_todo_compose.domain.repository.FakeToDoRepository
import by.g_alex.ysmd_todo_compose.domain.use_case.theme_setting.ThemeEnum
import by.g_alex.ysmd_todo_compose.domain.use_case.theme_setting.toMyString
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideToDoRepository(): FakeToDoRepository {
        return FakeFakeToDoRepositoryImpl()
    }

    @Singleton
    class MyPreference @Inject constructor(@ApplicationContext context: Context) {

        private val themeUpdateFlow = MutableSharedFlow<ThemeEnum>()

        fun getThemeUpdate(): SharedFlow<ThemeEnum> = themeUpdateFlow

        suspend fun publishThemeUpdate(theme: ThemeEnum) = themeUpdateFlow.emit(theme)

        private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        private val encryptedPref = EncryptedSharedPreferences.create(
            /* fileName = */ Constants.ENC_SHARED_PREF_TO_DO,
            /* masterKeyAlias = */
            masterKeyAlias,
            /* context = */
            context,
            /* prefKeyEncryptionScheme = */
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            /* prefValueEncryptionScheme = */
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        private val prefs =
            context.getSharedPreferences(Constants.SHARED_PREF_TO_DO, Context.MODE_PRIVATE)

        fun setToken(token: String?, isBearer: Boolean?) {
            encryptedPref.edit().remove(Constants.SHARED_REVISION).apply()
            if (token == null || isBearer == null) {
                encryptedPref.edit()
                    .remove(Constants.SHARED_BEARER_TOKEN)
                    .remove(Constants.SHARED_AUTH2_TOKEN)
                    .apply()
            } else {
                if (isBearer == true) {
                    encryptedPref.edit()
                        .putString(Constants.SHARED_BEARER_TOKEN, token)
                        .remove(Constants.SHARED_AUTH2_TOKEN)
                        .apply()
                } else {
                    encryptedPref.edit()
                        .putString(Constants.SHARED_AUTH2_TOKEN, token)
                        .remove(Constants.SHARED_BEARER_TOKEN)
                        .apply()
                }
            }
        }

        /** @return
         *
         * True if auth use bearer
         *
         * False if auth use oauth2
         *
         * Null if not auth yet **/
        fun getAuthType(): Boolean? {
            val bearer = encryptedPref.getString(Constants.SHARED_BEARER_TOKEN, null)
            val auth2 = encryptedPref.getString(Constants.SHARED_AUTH2_TOKEN, null)

            if (bearer != null) return true
            if (auth2 != null) return false
            return null
        }

        /** Return token or empty string if not auth yet **/
        fun getToken(): String {
            val bearer = encryptedPref.getString(Constants.SHARED_BEARER_TOKEN, "")
            val auth2 = encryptedPref.getString(Constants.SHARED_AUTH2_TOKEN, "")
            return bearer + auth2
        }

        /**
         * Save last revision
         * @param rev New revision version
         */
        fun setRevision(rev: Int) {
            prefs.edit().putInt(Constants.SHARED_REVISION, rev).apply()
        }

        /**
         * @return Last saved revision
         */
        fun getRevision(): Int =
            prefs.getInt(Constants.SHARED_REVISION, 0)

        /**
         * @param has Set offline changes flag
         */
        fun setHasOfflineChanges(has: Boolean) {
            prefs.edit().putBoolean(Constants.SHARED_OFFLINE_CHANGES, has).apply()
        }

        /**
         * @return offline changes flag
         */
        fun getHasOfflineChanges(): Boolean =
            prefs.getBoolean(Constants.SHARED_OFFLINE_CHANGES, false)

        fun getTheme(): String =
            prefs.getString(Constants.SHARED_THEME, "") ?: ""

        fun setTheme(theme: ThemeEnum) {
            prefs.edit().putString(Constants.SHARED_THEME, theme.toMyString()).apply()
        }

    }

}