package com.example.task_final_g7.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegisterViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener {
            register()
        }
    }

    private fun register() {
        binding.apply {
            val username = edtUsername.text.toString()
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            val confirmPassword = edtConfirmPassword.text.toString()

            viewModel.insert(username, email, password, confirmPassword)

            viewModel.message.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { message ->
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
            }

            viewModel.success.observe(viewLifecycleOwner) { success ->
                if (success) {
                    reset()
                }
            }
        }
    }

    private fun reset() {
        binding.apply {
            edtUsername.setText("")
            edtUsername.clearFocus()
            edtEmail.setText("")
            edtEmail.clearFocus()
            edtPassword.setText("")
            edtPassword.clearFocus()
            edtConfirmPassword.setText("")
            edtConfirmPassword.clearFocus()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}