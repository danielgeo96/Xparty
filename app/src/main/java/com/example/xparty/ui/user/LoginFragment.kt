package com.example.xparty.ui.user

import android.content.Context
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.xparty.R
import com.example.xparty.databinding.FragmentLoginBinding
import com.example.xparty.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginFragment:Fragment() {

    private val TAG : String = "LoginFragment"
    private var _binding : FragmentLoginBinding? =null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var mEmail : String
    private lateinit var mPassword : String
    private var editor: Editor? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        container?.removeAllViews()
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        auth = Firebase.auth
        val db = Firebase.firestore

        _binding?.loginBtn?.setOnClickListener {
            bindingLoginData()

            auth.signInWithEmailAndPassword(mEmail,mPassword).addOnCompleteListener { task->
                if(task.isSuccessful){
                    Log.d(TAG,"signInWithEmail:success")
                    val userId  = auth.currentUser?.uid
                    if (userId != null) {
                        db.collection("users").document(userId).get().addOnCompleteListener {
                            if(it.isSuccessful){
                                Log.d(TAG, "DocumentSnapshot data")
                                val document = it.result
                                editor = context?.getSharedPreferences("pref", Context.MODE_PRIVATE)?.edit()
                                editor?.putString("fullName", document.get("fullName").toString())
                                editor?.putString("email", document.getString("email").toString())
                                editor?.putString("userId", userId)
                                editor?.putString("img", document.getString("img").toString())
                                document.getBoolean("userType")
                                    ?.let { it1 -> editor?.putBoolean("userType", it1) }
                                editor?.putBoolean("isLogin", true)
                                editor?.commit()

                                //change fragment
                                val mainActivityView = (activity as MainActivity)
                                val navController:NavController = Navigation.findNavController(mainActivityView,
                                    R.id.nav_host_fragment)
                                navController.popBackStack()
                                navController.navigate(R.id.SearchFragment)
                                mainActivityView.replaceFragment("Main Page")

                            }
                        }
                    }
                }else{
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(context, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun bindingLoginData() {
        mEmail = _binding?.emailEditText?.text.toString()
        mPassword = _binding?.passwordEditText?.text.toString()
    }



}