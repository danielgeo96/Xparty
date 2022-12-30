package com.example.xparty.ui.user

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.xparty.R
import com.example.xparty.databinding.FragmentLoginBinding
import com.example.xparty.ui.MainActivity
import com.example.xparty.ui.main_character.PartySearchFragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginFragment:Fragment() {
    private var _binding : FragmentLoginBinding? =null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var mEmail : String
    private lateinit var mPassword : String

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
                    db.collection("users").get().addOnSuccessListener { result->
                        for (document in result){
                            if(auth.currentUser?.uid?.equals(document.get("userId")) == true){
                                var editor:SharedPreferences.Editor? = context?.getSharedPreferences("pref",Context.MODE_PRIVATE)?.edit()
                                editor?.putString("fullName",document.get("fullName").toString())
                                editor?.putString("email",document.get("email").toString())
                                editor?.putBoolean("isLogin",true)
                                editor?.putBoolean("type", document.get("type") as Boolean)
                                editor?.apply()

                                val mainActivityView = (activity as MainActivity)
                                mainActivityView.replaceFragment(PartySearchFragment(),getString(R.string.MainPage))
                            }else{
                                continue
                            }
                        }
                    }.addOnFailureListener { exception ->
                        Log.w("Tag","Error getting documents.", exception)
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

    private fun setDrawerMenuItems(type:Boolean,view: View) {
        var navigationView: NavigationView = view.findViewById(R.id.navView)
        var menu: Menu = navigationView.menu

        if(type){
            menu.setGroupVisible(R.id.member_main,true)
            menu.setGroupVisible(R.id.producer_main,true)
            menu.setGroupVisible(R.id.guest,false)
            menu.setGroupVisible(R.id.member,true)
        }else{
            menu.setGroupVisible(R.id.member_main,true)
            menu.setGroupVisible(R.id.producer_main,false)
            menu.setGroupVisible(R.id.guest,false)
            menu.setGroupVisible(R.id.member,true)
        }
    }


}