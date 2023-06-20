package com.nikitagorbatko.testfoods

import android.app.Application
import com.nikitagorbatko.account.AccountViewModel
import com.nikitagorbatko.cart.CartViewModel
import com.nikitagorbatko.cart_dishes.CartDishesRepositoryImpl
import com.nikitagorbatko.cart_dishes_use_case.AddCartDishUseCase
import com.nikitagorbatko.cart_dishes_use_case.GetCartDishesUseCase
import com.nikitagorbatko.cart_dishes_use_case.RemoveCartDishUseCase
import com.nikitagorbatko.categories.CategoriesRepositoryImpl
import com.nikitagorbatko.categories_use_case.GetCategoriesUseCase
import com.nikitagorbatko.category.DishesViewModel
import com.nikitagorbatko.category.ProductViewModel
import com.nikitagorbatko.database_entities.CartDishDatabase
import com.nikitagorbatko.dishes.DishesRepositoryImpl
import com.nikitagorbatko.dishes_use_case.GetDishesUseCase
import com.nikitagorbatko.main.MainViewModel
import com.nikitagorbatko.main.SharedViewModel
import com.nikitagorbatko.network.Retrofit
import com.nikitagorbatko.repositories.CartDishesRepository
import com.nikitagorbatko.repositories.CategoriesRepository
import com.nikitagorbatko.repositories.DishesRepository
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
        viewModel { CartViewModel(get(), get(), get()) }
        viewModel { DishesViewModel(get()) }
        viewModel { MainViewModel(get()) }
        viewModel { SearchViewModel() }
        viewModel { ProductViewModel(get()) }
        single { SharedViewModel() }
    }

    private val domain = module {
        factory { GetCartDishesUseCase(get()) }
        factory { AddCartDishUseCase(get()) }
        factory { RemoveCartDishUseCase(get()) }
        factory { GetCategoriesUseCase(get()) }
        factory { GetDishesUseCase(get()) }
    }

    private val data = module {
        single<DishesRepository> { DishesRepositoryImpl(get()) }
        single<CategoriesRepository> { CategoriesRepositoryImpl(get()) }
        single<CartDishesRepository> { CartDishesRepositoryImpl(get()) }
    }

    private val database = module {
        single { CartDishDatabase.getCartDishDao(androidContext()) }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                database,
                domain,
                features,
                data,
                network
            )
        }
    }
}