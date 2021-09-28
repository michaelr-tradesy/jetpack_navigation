package com.example.navigationexercise

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import java.util.*
import android.R

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.view.children
import androidx.core.view.get
import java.util.Collections.rotate


/**
 * The controller manages the navigation within the NavHost.
 * Also swaps the destinations content in the NavHost while the user navigates through the app
 */
@Navigator.Name("screen_view")
class ViewNavigator(private val container: ViewGroup) : Navigator<ViewDestination>() {

    private val viewStack: Deque<Pair<Int, Int>> = LinkedList()
    private val navigationHost = container as NavigationHostView

    override fun navigate(
        destination: ViewDestination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Extras?
    ) = destination.apply {
        viewStack.push(Pair(destination.id, destination.layoutId))
        replaceView(FirstView(navigationHost.context), false)
    }

    private fun replaceView(view: View?, isPopping: Boolean = true) {
        view?.let { newView ->
            val newResourceId = if(isPopping) com.example.navigationexercise.R.anim.slide_in_right else com.example.navigationexercise.R.anim.slide_in_left
            val prevResourceId = if(!isPopping) com.example.navigationexercise.R.anim.slide_out_right else com.example.navigationexercise.R.anim.slide_out_left
            val customViewFadeIn: Animation = AnimationUtils.loadAnimation(
                newView.context,
                newResourceId
            )
            newView.startAnimation(customViewFadeIn)

            if(container.childCount > 0) {
                val prevView = container[0]
                val customViewFadeOut: Animation = AnimationUtils.loadAnimation(
                    newView.context,
                    prevResourceId
                )
                customViewFadeOut.setAnimationListener(object: Animation.AnimationListener {
                    override fun onAnimationStart(p0: Animation?) {

                    }

                    override fun onAnimationEnd(p0: Animation?) {
                        container.removeView(prevView)
                    }

                    override fun onAnimationRepeat(p0: Animation?) {

                    }
                })
                prevView.startAnimation(customViewFadeOut)
            }

            container.addView(newView, 0)
        }
    }

    override fun createDestination(): ViewDestination = ViewDestination(this)

    override fun popBackStack(): Boolean = when {
        viewStack.isNotEmpty() -> {
            viewStack.pop()
            viewStack.peekLast()?.let {
                replaceView(FirstView(navigationHost.context), true)
            }
            true
        }
        else -> false
    }
}