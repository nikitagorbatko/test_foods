package com.nikitagorbatko.testfoods

import android.app.Application
import com.nikitagorbatko.account.AccountViewModel
import com.nikitagorbatko.cart.CartViewModel
import com.nikitagorbatko.categories.CategoriesRepository
import com.nikitagorbatko.categories.CategoriesRepositoryImpl
import com.nikitagorbatko.category.DishesViewModel
import com.nikitagorbatko.dishes.DishesRepository
import com.nikitagorbatko.dishes.DishesRepositoryImpl
import com.nikitagorbatko.main.MainViewModel
import com.nikitagorbatko.network.Retrofit
import com.nikitagorbatko.product.ProductViewModel
import com.nikitagorbatko.search.SearchViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {
    private val network = module {
        single { Retrofit() }
        single { get<Retrofit>().getCategoriesService() }
        single { get<Retrofit>().getDishesService() }
    }

    private val features = module {
        viewModel { AccountViewModel() }
        viewModel { CartViewModel() }
        viewModel { DishesViewModel(get()) }
        viewModel { MainViewModel(get()) }
        viewModel { ProductViewModel() }
        viewModel { SearchViewModel() }
    }

    private val data = module {
        single<DishesRepository> { DishesRepositoryImpl(get()) }
        single<CategoriesRepository> { CategoriesRepositoryImpl(get()) }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                features,
                data,
                network
            )
        }
    }
}