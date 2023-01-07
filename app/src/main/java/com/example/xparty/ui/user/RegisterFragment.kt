package com.example.xparty.ui.user

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.xparty.R
import com.example.xparty.databinding.FragmentRegisterBinding
import com.example.xparty.ui.MainActivity
import com.example.xparty.ui.main_character.PartySearchFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern


class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var mFullName: String
    private lateinit var mEmail: String
    private lateinit var mPassword: String
    private lateinit var mPasswordConfirmed: String
    private lateinit var mPhone: String
    private lateinit var img:Uri
    private lateinit var auth: FirebaseAuth
    private var mIsProducer : Boolean = false
    private val TAG : String = "RegisterFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        container?.removeAllViews()
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        auth = Firebase.auth

        _binding?.approveButton?.setOnClickListener {
            bindingRegisterData()
            if (validateFullName() && validateEmail() && validatePassword() && validatePhone()) {

                register()

                //change fragment
                val mainActivityView = (activity as MainActivity)
                val navController: NavController = Navigation.findNavController(mainActivityView,
                    R.id.nav_host_fragment)
                navController.popBackStack()
                navController.navigate(R.id.LoginFragment)
                mainActivityView.replaceFragment("Login Page")

            } else {
                Toast.makeText(context, "Failed to register", Toast.LENGTH_SHORT).show()
            }

        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun bindingRegisterData() {
        mFullName = _binding?.nameEditText?.text.toString()
        mEmail = _binding?.emailEditText?.text.toString()
        mPassword = _binding?.passwordEditText?.text.toString()
        mPasswordConfirmed = _binding?.confirmPasswordEditText?.text.toString()
        mPhone = _binding?.phoneEditText?.text.toString()
        mIsProducer = _binding?.isProducer?.isChecked == true
    }

    private fun validateFullName(): Boolean {
        return if (mFullName.isEmpty()) {
            _binding?.nameEditText?.error = "Field can not be empty"
            false
        } else if (!mFullName.all { it.isLetter() }) {
            _binding?.nameEditText?.error = "Field can not contain numbers"
            false
        } else {
            _binding?.nameEditText?.error = null
            true
        }
    }

    private fun validateEmail(): Boolean {
        return if (!mEmail.isValidEmail()) {
            _binding?.emailEditText?.error = "Email not validate please try again."
            false
        } else {
            _binding?.passwordEditText?.error = null
            true
        }
    }

    private fun CharSequence?.isValidEmail() =
        !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

    private fun validatePassword(): Boolean {

        return if (mPassword.length < 8) {
            _binding?.passwordEditText?.error = "Use 8 characters or more for your password."
            false
        } else if (!isValidPassword(mPassword)) {
            _binding?.passwordEditText?.error =
                "Password must contain at least one digit and one letter."
            false
        } else if (mPassword != mPasswordConfirmed) {
            _binding?.confirmPasswordEditText?.error = "Those passwords didn’t match. Try again."
            false
        } else {
            _binding?.passwordEditText?.error = null
            _binding?.confirmPasswordEditText?.error = null
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
        );
        return passwordREGEX.matcher(password).matches()
    }

    private fun validatePhone(): Boolean {
        return if (!Patterns.PHONE.matcher(mPhone).matches()) {
            _binding?.phoneEditText?.error = "Phone number not validate please try again."
            false
        } else {
            _binding?.phoneEditText?.error = null
            true
        }
    }

    private fun register(){
        val db = Firebase.firestore

        auth.createUserWithEmailAndPassword(mEmail, mPassword)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "createUserWithEmail:success")

                    val user = hashMapOf(
                        "fullName" to mFullName,
                        "email" to mEmail,
                        "phoneNumber" to mPhone,
                        "userId" to auth.currentUser?.uid,
                        "userType" to mIsProducer,
//                        "userImg" to img
                    )

                    db.collection("users").document(auth.currentUser?.uid.toString()).set(user)

                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        context, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

}
