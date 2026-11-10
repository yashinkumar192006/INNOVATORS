package com.trinetra.mobile.android.di

import android.app.Application
import androidx.datastore.core.Serialization
import androidx.datastore.preference.GenericPreferenceSerializer
import androidx.datastore.preferencePreferenceManager
import com.trinetra.mobile.android.core.datastore.AppPreferencesDataStore
import com.trinetra.mobile.android.core.database.TRINETRADatabase
import com.trinetra.mobile.android.core.network.service.TRINETRANetworkClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object TRINETRAModule {

    @Provides
    @Singleton
    fun provideApplication(@ApplicationContext context: Application): Application {
        return context
    }

    @Provides
    @Singleton
    fun provideNetworkClient(): TRINETRANetworkClient {
        return TRINETRANetworkClient()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Application): TRINETRADatabase {
        return TRINETRADatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun providePreferencesDataStore(@ApplicationContext context: Application): AppPreferencesDataStore {
        return AppPreferencesDataStore()
    }
}