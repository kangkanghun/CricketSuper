package com.moingay.cricketsuper.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.moingay.cricketsuper.data.datastore.DataStoreManager
import com.moingay.cricketsuper.data.model.response.DatumAttributes
import com.moingay.cricketsuper.data.model.response.Inning
import com.moingay.cricketsuper.data.model.response.LiveInning
import com.moingay.cricketsuper.data.model.response.Player
import com.moingay.cricketsuper.data.model.response.ScoreMatchResponse
import com.moingay.cricketsuper.data.remote.ICricketApi
import com.moingay.cricketsuper.data.remote.ISportApi
import com.moingay.cricketsuper.data.remote.SeriesMatchDataSource
import com.moingay.cricketsuper.data.remote.SportDataSource
import com.moingay.cricketsuper.data.repository.IRepository
import com.moingay.cricketsuper.domain.interactors.GetIsFirstTimeUseCase
import com.moingay.cricketsuper.domain.interactors.GetPostBySlugUseCase
import com.moingay.cricketsuper.domain.interactors.GetMatchDataUseCase
import com.moingay.cricketsuper.domain.interactors.GetLiveCompetitionUseCase
import com.moingay.cricketsuper.domain.interactors.GetScoreMatchDetailUseCase
import com.moingay.cricketsuper.domain.interactors.GetTrendingSeriesUseCase
import com.moingay.cricketsuper.domain.interactors.GetLiveMatchDetailsUseCase
import com.moingay.cricketsuper.domain.interactors.SetIsFirstTimeUseCase
import com.moingay.cricketsuper.domain.repository.RepositoryImpl
import com.moingay.cricketsuper.presentation.ui.home.HomeViewModel
import com.moingay.cricketsuper.presentation.ui.livematchdetail.LiveMatchDetailViewModel
import com.moingay.cricketsuper.presentation.ui.matches.MatchesViewModel
import com.moingay.cricketsuper.presentation.ui.news.NewsViewModel
import com.moingay.cricketsuper.presentation.ui.news.newsdetail.NewsDetailViewModel
import com.moingay.cricketsuper.presentation.ui.series.SeriesViewModel
import com.moingay.cricketsuper.presentation.ui.series.seriesdetail.SeriesDetailViewModel
import com.moingay.cricketsuper.utils.Constants
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val viewModelModule = module {
    factoryOf(::HomeViewModel)
    factoryOf(::LiveMatchDetailViewModel)
    factoryOf(::SeriesViewModel)
    factoryOf(::MatchesViewModel)
    factoryOf(::NewsViewModel)
    factoryOf(::NewsDetailViewModel)
    factoryOf(::SeriesDetailViewModel)
}

val repositoryModule = module {
    single<IRepository> { RepositoryImpl(get(), get(), get(), get()) }
}

val useCaseModule = module {
    factoryOf(::GetLiveCompetitionUseCase)
    factoryOf(::GetPostBySlugUseCase)
    factoryOf(::GetScoreMatchDetailUseCase)
    factoryOf(::GetTrendingSeriesUseCase)
    factoryOf(::GetMatchDataUseCase)
    factoryOf(::GetLiveMatchDetailsUseCase)
    factoryOf(::GetIsFirstTimeUseCase)
    factoryOf(::SetIsFirstTimeUseCase)
}

val dispatcherModule = module {
    factory { Dispatchers.Default }
}

val networkModule = module {
    factory { AuthInterceptor(Constants.MatchAPI.BEARER_TOKEN) }
    singleOf(::provideOkHttpClient)
    singleOf(::provideConverterFactory)
    singleOf(::provideGson)
    single(named(Constants.MatchAPI.NAME)) {
        provideRetrofit(get(), get(), Constants.MatchAPI.URL)
    }
    single(named(Constants.SportAPI.NAME)) {
        provideRetrofit(get(), get(), Constants.SportAPI.URL)
    }
    single { provideMatchServiceApi(get(named(Constants.MatchAPI.NAME))) }
    single { provideNewsServiceApi(get(named(Constants.SportAPI.NAME))) }
    factory { SportDataSource(get()) }
}

fun provideGson(): Gson {
    return GsonBuilder()
        .registerTypeAdapter(LiveInning::class.java, LiveInningDeserializer())
        .registerTypeAdapter(Inning::class.java, InningDeserializer())
        .registerTypeAdapter(Player::class.java, PlayerDeserializer())
        .registerTypeAdapter(ScoreMatchResponse::class.java, ScoreMatchResponseDeserializer())
        .registerTypeAdapter(DatumAttributes::class.java, DatumAttributesDeserializer())
        .registerTypeAdapterFactory(MatchDataTypeAdapterFactory())
        .create()
}

fun provideConverterFactory(): GsonConverterFactory =
    GsonConverterFactory.create(provideGson())

fun provideRetrofit(
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory,
    urlApi: String,
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(urlApi)
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .build()
}

fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
    // Create a logging interceptor
    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    return OkHttpClient().newBuilder().followRedirects(true).followSslRedirects(true)
        .addInterceptor(logging).addInterceptor(authInterceptor).build()
}

fun provideMatchServiceApi(retrofit: Retrofit): ICricketApi =
    retrofit.create(ICricketApi::class.java)

fun provideNewsServiceApi(retrofit: Retrofit): ISportApi =
    retrofit.create(ISportApi::class.java)

val dataStoreModule = module {
    single { DataStoreManager(get()) }
}