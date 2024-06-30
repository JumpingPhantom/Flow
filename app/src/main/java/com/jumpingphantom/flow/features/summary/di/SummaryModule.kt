package com.jumpingphantom.flow.features.summary.di

import com.jumpingphantom.flow.features.summary.ui.SummaryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val summaryModule = module {
    viewModel<SummaryViewModel> {
        SummaryViewModel(get())
    }
}