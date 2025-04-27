package com.rodelindev.tvshows.di

import android.content.Context
import androidx.room.Room
import com.rodelindev.tvshows.data.local.TvShowDatabase
import com.rodelindev.tvshows.data.local.converter.SeasonConverter
import com.rodelindev.tvshows.data.local.dao.TvShowDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesTvShowDatabase(@ApplicationContext context: Context): TvShowDatabase =
        Room.databaseBuilder(
            context,
            TvShowDatabase::class.java,
            TvShowDatabase.DB_NAME
        ).addTypeConverter(SeasonConverter()).build()

    @Provides
    @Singleton
    fun providesTvShowDao(tvShowDatabase: TvShowDatabase): TvShowDao {
        return tvShowDatabase.tvShowDao()
    }
}