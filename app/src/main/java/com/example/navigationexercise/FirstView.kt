package com.example.navigationexercise

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.screen_view_first.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FirstView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var index = 0

    init {
        LayoutInflater.from(context).inflate(R.layout.screen_view_first, this)

        // I tried to resolve rendering issues inside the design view... I failed... :`(
        if (!isInEditMode) {
            // Because I'm retrieving information from the bundle,
            // which was injected into the nav controller,
            // and that nav controller has not yet been loaded...
            // I must delay this process.
            GlobalScope.launch(Dispatchers.IO) {
                delay(50)
                val navController = findNavController()
                val arguments = navController.currentBackStackEntry?.arguments
                index = arguments?.getInt("INDEX", 0) ?: 0
                GlobalScope.launch(Dispatchers.Main) {
                    textView.text = "This is Screen # $index"
                }
            }

            btn_second_screen.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("INDEX", index + 1)
                try {
                    findNavController().navigate(
                        R.id.action_first_screen_view_to_second_screen_view,
                        bundle
                    )
                } catch (t: Throwable) {
                    t.printStackTrace()
                }
            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }
}