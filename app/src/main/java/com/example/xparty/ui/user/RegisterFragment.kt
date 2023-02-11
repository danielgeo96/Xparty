package com.example.xparty.ui.user

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.xparty.R
import com.example.xparty.databinding.FragmentRegisterBinding
import com.example.xparty.ui.MainActivity
import com.example.xparty.utlis.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern


@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private var binding: FragmentRegisterBinding by autoCleared()
    private lateinit var mFullName: String
    private lateinit var mEmail: String
    private lateinit var mPassword: String
    private lateinit var mPasswordConfirmed: String
    private lateinit var mPhone: String
    private var img: Uri? = null
    private var mIsProducer: Boolean = false
    private val viewModel: RegisterViewModel by viewModels()
    private val pickImageLauncher: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) {
            requireContext().contentResolver.takePersistableUriPermission(
                it!!,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            when (it) {
                null -> {
                    binding.registerUserImage.setImageResource(R.drawable.profile_image)
                }
            }
            Glide.with(this).load(it).into(binding.registerUserImage)
            img = it
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        container?.removeAllViews()
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding.registerAddImage.setOnClickListener {
            pickImageLauncher.launch(arrayOf("image/*"))
        }
        binding.approveButton.setOnClickListener {
            bindingRegisterData()
            if (validateFullName() && validateEmail() && validatePassword() && validatePhone()) {

                viewModel.createUser(
                    mFullName,
                    mEmail,
                    mPassword,
                    mPhone,
                    img.toString(),
                    mIsProducer
                )

            } else {
                Toast.makeText(context, "Failed to register", Toast.LENGTH_SHORT).show()
            }

        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.userRegistrationStatus.observe(viewLifecycleOwner) {
            when (it.status) {
                is Loading -> {
                    binding.approveButton.isEnabled = false
                    binding.registerProgressBar.isVisible = true
                }

                is Success -> {
                    Toast.makeText(requireContext(), "Registration successful", Toast.LENGTH_SHORT)
                        .show()
                    val mainActivityView = (activity as MainActivity)
                    val navController: NavController = Navigation.findNavController(
                        mainActivityView,
                        R.id.nav_host_fragment
                    )
                    navController.popBackStack()
                    navController.navigate(R.id.LoginFragment)
                    mainActivityView.replaceFragment("Login Page")
                }

                is Error -> {
                    binding.approveButton.isEnabled = true
                    binding.registerProgressBar.isVisible = false
                }
            }
        }
    }

    private fun bindingRegisterData() {
        mFullName = binding.nameEditText.text.toString()
        mEmail = binding.emailEditText.text.toString()
        mPassword = binding.passwordEditText.text.toString()
        mPasswordConfirmed = binding.confirmPasswordEditText.text.toString()
        mPhone = binding.phoneEditText.text.toString()
        mIsProducer = binding.isProducer.isChecked == true
        if (img == null){
            img = Uri.parse("android.resource://com.example.xparty/" + R.drawable.profile_image
            )
        }
    }

    private fun validateFullName(): Boolean {
        return if (mFullName.isEmpty()) {
            binding.nameEditText.error = "Field can not be empty"
            false
        } else if (!mFullName.all { it.isLetter() }) {
            binding.nameEditText.error = "Field can not contain numbers"
            false
        } else {
            binding.nameEditText.error = null
            true
        }
    }

    private fun validateEmail(): Boolean {
        return if (!mEmail.isValidEmail()) {
            binding.emailEditText.error = "Email not validate please try again."
            false
        } else {
            binding.passwordEditText.error = null
            true
        }
    }

    private fun CharSequence?.isValidEmail() =
        !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

    private fun validatePassword(): Boolean {

        return if (mPassword.length < 8) {
            binding.passwordEditText.error = "Use 8 characters or more for your password."
            false
        } else if (!isValidPassword(mPassword)) {
            binding.passwordEditText.error =
                "Password must contain at least one digit and one letter."
            false
        } else if (mPassword != mPasswordConfirmed) {
            binding.confirmPasswordEditText.error = "Those passwords didn't match. Try again."
            false
        } else {
            binding.passwordEditText.error = null
            binding.confirmPasswordEditText.error = null
            true
        }
    }

    private fun isValidPassword(password: String): Boolean {
        val passwordREGEX = Pattern.compile(
            "^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=\\S+$)" +           //no white spaces
                    ".{8,}" +               //at least 8 characters
                    "$"
        )
        return passwordREGEX.matcher(password).matches()
    }

    private fun validatePhone(): Boolean {
        return if (!Patterns.PHONE.matcher(mPhone).matches()) {
            binding.phoneEditText.error = "Phone number not validate please try again."
            false
        } else {
            binding.phoneEditText.error = null
            true
        }
    }

}
