package com.example.xparty.ui.user

import android.content.Context
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.xparty.R
import com.example.xparty.data.repository.firebase.AuthRepositoryFirebase
import com.example.xparty.databinding.FragmentLoginBinding
import com.example.xparty.ui.MainActivity
import com.example.xparty.utlis.Loading
import com.example.xparty.utlis.Success
import com.example.xparty.utlis.autoCleared
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

class LoginFragment : Fragment() {

    private val TAG: String = "LoginFragment"
    private var binding: FragmentLoginBinding by autoCleared()
    private lateinit var mEmail: String
    private lateinit var mPassword: String
    private var editor: Editor? = null
    private val viewModel: LoginViewModel by viewModels(){
        LoginViewModel.LoginViewModelFactory(AuthRepositoryFirebase())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        container?.removeAllViews()
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding?.loginBtn?.setOnClickListener {
            bindingLoginData()
            viewModel.signIn(mEmail, mPassword)

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.userSignInStatus.observe(viewLifecycleOwner) {
            when (it.status) {
                is Loading -> {
                    binding.loginBtn.isEnabled = false
                    binding.loginProgressBar.isVisible = true
                }

                is Success -> {
                    Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT).show()

                    //save user detailes to shared pref
                    editor = context?.getSharedPreferences("pref", Context.MODE_PRIVATE)?.edit()
                    editor?.putString("fullName", it.status.data?.name.toString())
                    editor?.putString("email", it.status.data?.email.toString())
                    editor?.putString("phone", it.status.data?.phone.toString())
                    editor?.putString("userId", it.status.data?.userId.toString())
                    editor?.putString("img", it.status.data?.photo.toString())
                    editor?.putBoolean("producer",it.status.data!!.producer)
                    editor?.putBoolean("isLogin", true)
                    editor?.commit()

                    //navigate to new fragment
                    val mainActivityView = (activity as MainActivity)
                    val navController: NavController = Navigation.findNavController(
                        mainActivityView,
                        R.id.nav_host_fragment
                    )
                    navController.popBackStack()
                    navController.navigate(R.id.SearchFragment)
                    mainActivityView.replaceFragment("Main Page")
                }

                is com.example.xparty.utlis.Error -> {
                    binding.loginBtn.isEnabled = true
                    binding.loginProgressBar.isVisible = false
                }
            }
        }

    }


    private fun bindingLoginData() {
        mEmail = binding?.emailEditText?.text.toString()
        mPassword = binding?.passwordEditText?.text.toString()
    }

}