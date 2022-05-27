package com.example.task_final_g7.di

import com.example.task_final_g7.viewmodel.HomeViewModel
import com.example.task_final_g7.viewmodel.LoginViewModel
import com.example.task_final_g7.viewmodel.ProfileViewModel
import com.example.task_final_g7.viewmodel.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LoginViewModel(get(), get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { ProfileViewModel(get(), get()) }
}