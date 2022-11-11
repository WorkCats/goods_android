package com.agoines.goods.di

import androidx.browser.customtabs.CustomTabsIntent
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CustomTabsModule {
    @Provides
    @Singleton
    fun provideCustomTabs(): CustomTabsIntent = CustomTabsIntent.Builder().build()
}