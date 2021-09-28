package com.example.navigationexercise

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.Navigation

/**
 * This is an empty container that displays your destinations from the graph.
 */
class NavigationHostView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1,
) : FrameLayout(context, attrs, defStyleAttr), NavHost {

    private val navigationController = NavController(context)

    init {
        Navigation.setViewNavController(this, navigationController)
        val customViewNavigator = ViewNavigator(this)
        navigationController.navigatorProvider.addNavigator(customViewNavigator)
        navigationController.setGraph(R.navigation.navigation)
    }

    override val navController = navigationController
}