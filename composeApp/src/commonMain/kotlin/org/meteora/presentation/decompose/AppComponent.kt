package org.meteora.presentation.decompose

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.navigate
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import kotlinx.serialization.Serializable
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.meteora.domain.entity.LocationInfo
import org.meteora.presentation.decompose.AppComponent.Child

interface AppComponent : BackHandlerOwner {
    val stack: Value<ChildStack<*, Child>>

    fun onBackClicked()
    fun onBackClicked(toIndex: Int)

    sealed class Child {
        class Locations(val component: DefaultLocationsComponent) : Child()
        class LocationSearch(val component: DefaultLocationSearchComponent) : Child()
        class LocationWeather(val component: DefaultLocationWeatherComponent) : Child()
    }
}

class DefaultAppComponent(
    componentContext: ComponentContext,
) : AppComponent, KoinComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, Child>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = Config.Locations,
            handleBackButton = true,
            childFactory = ::child,
        )

    private fun child(config: Config, componentContext: ComponentContext): Child =
        when (config) {
            is Config.Locations ->
                Child.Locations(
                    component = DefaultLocationsComponent(
                        componentContext = componentContext,
                        weatherApiRepository = get(),
                        weatherLocalRepository = get(),
                        onNavigateToLocationSearch = { navigation.push(Config.LocationSearch) },
                        onNavigateToLocationWeather = {
                            navigation.push(Config.LocationWeather(locationInfo = it))
                        },
                    )
                )

            is Config.LocationSearch ->
                Child.LocationSearch(
                    component = DefaultLocationSearchComponent(
                        componentContext = componentContext,
                        weatherApiRepository = get(),
                        onNavigateBack = navigation::pop,
                        onNavigateBackTo = navigation::popTo
                    )
                )

            is Config.LocationWeather ->
                Child.LocationWeather(
                    component = DefaultLocationWeatherComponent(
                        componentContext = componentContext,
                        initialLocation = config.locationInfo,
                        weatherApiRepository = get(),
                    )
                )
        }

    override fun onBackClicked() {
        navigation.pop()
    }

    override fun onBackClicked(toIndex: Int) {
        navigation.navigate { it.take(toIndex + 1) }
    }

    @Serializable
    sealed interface Config {
        @Serializable
        data object Locations : Config

        @Serializable
        data object LocationSearch : Config

        @Serializable
        data class LocationWeather(val locationInfo: LocationInfo) : Config
    }
}
