package com.example.whatthefish.di

import android.app.Application
import com.example.fish_interactors.FishInteractors
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FishInteractorsModule {

    @Provides
    @Singleton
    @Named("fishAndroidSqlDriver") // in case you had another SQL delight db
    fun provideAndroidDriver(app: Application): SqlDriver {
        return AndroidSqliteDriver(
            schema = FishInteractors.schema,
            context = app,
            name = FishInteractors.dbName
        )
    }

    @Provides
    @Singleton
    fun provideFishInteractors(
        @Named("fishAndroidSqlDriver") sqlDriver: SqlDriver
    ): FishInteractors {
        return FishInteractors.build(sqlDriver = sqlDriver)
    }
}