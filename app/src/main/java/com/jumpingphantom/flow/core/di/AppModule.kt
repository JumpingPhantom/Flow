package com.jumpingphantom.flow.core.di

import androidx.room.Room
import com.jumpingphantom.flow.core.data.ApplicationDatabase
import com.jumpingphantom.flow.core.data.dao.TransactionDao
import com.jumpingphantom.flow.core.data.repository.local.LocalDataSource
import com.jumpingphantom.flow.core.ui.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<TransactionDao> {
        Room.databaseBuilder(androidContext(), ApplicationDatabase::class.java, "transactions_db")
            .build()
            .transactionDao()
    }

    single<LocalDataSource> { LocalDataSource(get()) }
    viewModel<MainViewModel> { MainViewModel(get()) }
}