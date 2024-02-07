package com.example.pruebatecnica.api.network

import android.annotation.SuppressLint
import com.example.pruebatecnica.api.apiModel.FiguraApiModel
import com.example.pruebatecnica.api.services.SyncRetrofitService
import com.example.pruebatecnica.api.util.DataArgs
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParseException
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.google.gson.stream.MalformedJsonException
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLHandshakeException
import javax.net.ssl.SSLSession
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object WebServiceHelper {
    private val interceptor = HttpLoggingInterceptor { message: String? -> Timber.v(message) }.apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    // Corregimos la creación del OkHttpClient
    val okHttpClient: OkHttpClient = unsafeOkHttpClient.apply {
        addInterceptor(interceptor) // Quita el .build() de aquí
        connectTimeout(80, TimeUnit.SECONDS)
        readTimeout(80, TimeUnit.SECONDS)
    }.build() // Agrega el .build() aquí

    val gsonWithAdapterType: Gson = GsonBuilder()
        .registerTypeAdapter(Long::class.java, LongTypeAdapter())
        .registerTypeAdapter(Double::class.java, DoubleTypeAdapter())
        .registerTypeAdapter(Int::class.java, IntTypeAdapter())
        .create()

    private class LongTypeAdapter : TypeAdapter<Long>() {
        override fun write(out: JsonWriter?, value: Long?) {
            /*
            if (value == null) {
                out?.nullValue()
                return
            }
            */

            if (value == null) {
                out?.value(0)
                return
            }

            out?.value(value)
        }

        override fun read(`in`: JsonReader?): Long? {
            if (`in`?.peek() == JsonToken.NULL) {
                `in`.nextNull()
                return null
            }

            val stringValue: String? = `in`?.nextString()

            return try {
                stringValue?.toLongOrNull() ?: kotlin.run { return null }
            } catch (e: NumberFormatException) {
                e.printStackTrace()
                null
            }
        }
    }

    private class DoubleTypeAdapter : TypeAdapter<Double>() {
        override fun write(out: JsonWriter?, value: Double?) {
            /*
            if (value == null) {
                out?.nullValue()
                return
            }
            */

            if (value == null) {
                out?.value(0)
                return
            }

            out?.value(value)
        }

        override fun read(`in`: JsonReader?): Double? {
            if (`in`?.peek() == JsonToken.NULL) {
                `in`.nextNull()
                return null
            }

            val stringValue: String? = `in`?.nextString()

            return try {
                stringValue?.toDoubleOrNull() ?: kotlin.run { return null }
            } catch (e: NumberFormatException) {
                e.printStackTrace()
                null
            }
        }
    }

    private class IntTypeAdapter : TypeAdapter<Int>() {
        override fun write(out: JsonWriter?, value: Int?) {
            /*
            if (value == null) {
                out?.nullValue()
                return
            }
            */

            if (value == null) {
                out?.value(0)
                return
            }

            out?.value(value)
        }

        override fun read(`in`: JsonReader?): Int? {
            if (`in`?.peek() == JsonToken.NULL) {
                `in`.nextNull()
                return null
            }

            val stringValue: String? = `in`?.nextString()

            return try {
                stringValue?.toIntOrNull() ?: kotlin.run { return null }
            } catch (e: NumberFormatException) {
                e.printStackTrace()
                null
            }
        }
    }

    @JvmStatic
    val instance: SyncRetrofitService
        get() {
            val retrofit = Retrofit.Builder()
                .baseUrl(DataArgs.Network.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gsonWithAdapterType))
                .client(okHttpClient)
                .build()
            return retrofit.create(SyncRetrofitService::class.java)
        }

    suspend fun obtenerFigurasDesdeJson(): List<FiguraApiModel> {
        return try {
            instance.getSync()
        } catch (e: JsonParseException) {
            throw MalformedJsonException(e.message)
        } catch (e: IOException) {
            throw e
        }
    }

    // Create a trust manager that does not validate certificate chains
    private val unsafeOkHttpClient: OkHttpClient.Builder
        // Install the all-trusting trust manager
        // Create an ssl socket factory with our all-trusting manager
        get() = try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(
                @SuppressLint("CustomX509TrustManager")
                object : X509TrustManager {
                    @SuppressLint("TrustAllX509TrustManager")
                    override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
                    }

                    @SuppressLint("TrustAllX509TrustManager")
                    override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        return arrayOf()
                    }
                }
            )

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory
            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier { _: String?, _: SSLSession? -> true }
            builder
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    fun manageFailure(t: Throwable): String {
        return when (t) {
            is MalformedJsonException -> "Inconveniente con el servidor, vuelva a intentar."
            is SSLHandshakeException, is SocketTimeoutException -> "Verifique su conexión a internet y vuelva a intentar."
            is UnknownHostException -> "Asegúrese de estar conectado a internet."
            else -> "Inconveniente desconocido, vuelva a intentar: ${t.message}"
        }
    }
}