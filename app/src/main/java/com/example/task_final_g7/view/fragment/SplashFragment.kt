package com.example.task_final_g7.view.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.UserManager
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.task_final_g7.R
import com.example.task_final_g7.databinding.FragmentSplashBinding
import com.example.task_final_g7.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val viewModel: LoginViewModel by viewModel()
    private var fragmentSplashBinding: FragmentSplashBinding? = null

    private lateinit var UserManager: UserManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentSplashBinding.bind(view)
        fragmentSplashBinding = binding
        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.getLoginStatus().observe(viewLifecycleOwner) { isLogin ->
                if (isLogin == true) {
                    findNavController().navigate(R.id.action_splashFragment3_to_homeFragment)
                } else {

                    findNavController().navigate(R.id.action_splashFragment3_to_loginFragment)
                }
            }
        }, 4000)
    }

}