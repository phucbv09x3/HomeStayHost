package open.your.eyes.miichibaseapp.data.api

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import open.your.eyes.miichibaseapp.BuildConfig
import open.your.eyes.miichibaseapp.utils.printLog
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by OpenYourEyes on 4/4/21
 */
@Singleton
class ApiService : IApiService {
    companion object {
        const val timeOut = 30L
        val level = HttpLoggingInterceptor.Level.NONE
    }

    private lateinit var retrofitCoroutines: Retrofit
    private val retrofitWithoutAuthenticator: Retrofit by lazy {
        val baseUrl = BuildConfig.API_HOST
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(createHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun createHttpClient(token: String? = null): OkHttpClient {
        val logging = HttpLoggingInterceptor(ApiLogger())
        logging.level = level
        return token?.let {
            /**
             * create Http Client with authenticator token
             */
            OkHttpClient.Builder()
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .addInterceptor { chain: Interceptor.Chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Authorization", "Bearer $token")
                        .build()
                    chain.proceed(request)
                }.build()
        } ?: kotlin.run {
            /**
             * create Http Client without authenticator token
             */
            OkHttpClient.Builder()
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .addInterceptor { chain: Interceptor.Chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .build()
                    chain.proceed(request)
                }.build()
        }
    }

    override fun apiWithoutAuthenticator(): ApiCoroutines {
        return retrofitWithoutAuthenticator.create(ApiCoroutines::class.java)
    }

    override fun apiWithAuthenticator(): ApiCoroutines {
        return retrofitCoroutines.create(ApiCoroutines::class.java)
    }


    /**
     * call after login or register account
     */
    override fun createApiCoroutines(token: String) {
        retrofitCoroutines = Retrofit.Builder()
            .baseUrl(BuildConfig.API_HOST)
            .client(createHttpClient(token))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}

class ApiLogger : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        if (message.startsWith("{") || message.startsWith("[")) {
            try {
                val prettyPrintJson = GsonBuilder().setPrettyPrinting()
                    .create().toJson(JsonParser().parse(message))
                printLog(message, prefix = " - ApiLogger")
            } catch (m: JsonSyntaxException) {
                printLog(message, prefix = " - ApiLogger")
            }
        } else {
            printLog(message, prefix = " - ApiLogger")
        }
    }
}