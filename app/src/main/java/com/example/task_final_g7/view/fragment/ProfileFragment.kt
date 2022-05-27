package com.example.task_final_g7.view.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.task_final_g7.R
import com.example.task_final_g7.data.local.User
import com.example.task_final_g7.databinding.FragmentProfileBinding
import com.example.task_final_g7.viewmodel.ProfileViewModel
import com.example.task_final_g7.utils.ImageHelper
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModel()
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private var bitmap: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    handleCameraImage(result.data)
                }
            }

        showProfile()

        viewModel.getUserId().observe(viewLifecycleOwner) {
            viewModel.setUserId(it)
        }

        viewModel.message.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { message ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }

        binding.apply {
            ivProfile.setOnClickListener {
                openCamera()
            }
            btnUpdate.setOnClickListener {
                updateProfile()
            }
            btnLogout.setOnClickListener {
                logout()
            }
        }
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        resultLauncher.launch(cameraIntent)
    }

    private fun handleCameraImage(intent: Intent?) {
        bitmap = intent?.extras?.get("data") as Bitmap
        binding.ivProfile.setImageBitmap(bitmap)
    }

    private fun updateProfile() {
        binding.apply {
            viewModel.userData.observe(viewLifecycleOwner) {
                val user = it
                val updateUser = User(
                    id = user.id,
                    email = user.email,
                    password = user.password,
                    username = edtUsername.text.toString(),
                    fullName = edtFullName.text.toString(),
                    dateOfBirth = edtDateOfBirth.text.toString(),
                    address = edtAddress.text.toString(),
                    imageProfile = user.imageProfile
                )
                @Suppress("SENSELESS_COMPARISON")
                if (bitmap != null) {
                    updateUser.imageProfile = ImageHelper().convert(bitmap!!)
                }
                viewModel.update(updateUser)
                reset()
            }
        }
    }

    private fun logout() {
        viewModel.clearLoginStatus()
        findNavController().navigate(
            R.id.action_profileFragment_to_loginFragment,
        )
    }

    private fun reset() {
        binding.apply {
            edtUsername.clearFocus()
            edtFullName.clearFocus()
            edtDateOfBirth.clearFocus()
            edtAddress.clearFocus()
        }
    }

    private fun showProfile() {
        viewModel.userData.observe(viewLifecycleOwner) { user ->
            binding.apply {
                if (user.imageProfile != null) {
                    ivProfile.setImageBitmap(ImageHelper().convert(user.imageProfile))
                }
                edtUsername.setText(user.username)
                edtFullName.setText(user.fullName)
                edtDateOfBirth.setText(user.dateOfBirth)
                edtAddress.setText(user.address)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}