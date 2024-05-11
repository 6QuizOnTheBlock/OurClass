package com.sixkids.core.bluetooth.di

import android.content.Context
import com.sixkids.core.bluetooth.BluetoothScanner
import com.sixkids.core.bluetooth.BluetoothServer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BluetoothModule {

    @Provides
    @Singleton
    fun provideBluetoothScanner(
        @ApplicationContext context: Context
    ): BluetoothScanner {
        return BluetoothScanner(context)
    }

    @Provides
    @Singleton
    fun provideBluetoothServer(
        @ApplicationContext context: Context
    ): BluetoothServer {
        return BluetoothServer(context)
    }

}
