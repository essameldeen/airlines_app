package com.vodeg.airlines_app.presentation.splash

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.vodeg.airlines_app.R
import com.vodeg.airlines_app.presentation.base.BaseFragment
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SplashFragment : BaseFragment(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_splash, container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       launch {
           delay(1000)
           findNavController().navigate(SplashFragmentDirections.navigateToHomeFragment())
       }


    }
}