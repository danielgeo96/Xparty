package com.example.xparty.data.repository.firebase

import com.example.xparty.data.models.User
import com.example.xparty.data.repository.AuthRepository
import com.example.xparty.utlis.Resource
import com.example.xparty.utlis.safeCall
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

class AuthRepositoryFirebase @Inject constructor():AuthRepository{

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val userRef = FirebaseFirestore.getInstance().collection("users")
    override suspend fun updateUser(user:User):Resource<User> {
        return withContext(Dispatchers.IO){
            safeCall {
                val firebaseUser = firebaseAuth.currentUser
                if(firebaseUser != null)
                    {
                        userRef.document(firebaseUser.uid).set(user)
                    }
                Resource.success(user)
            }
        }

    }

    override suspend fun currentUser(): Resource<User> {
        return withContext(Dispatchers.IO){
            safeCall {
                val user = userRef.document(firebaseAuth.currentUser!!.uid).get().await().toObject(User::class.java)!!
                Resource.success(user)
            }
        }
    }

    override suspend fun login(email: String, password: String): Resource<User> {
        return withContext(Dispatchers.IO){
            safeCall {
                val result = firebaseAuth.signInWithEmailAndPassword(email,password).await()
                val user = userRef.document(result.user?.uid!!).get().await().toObject(User::class.java)!!

                Resource.success(user)
            }
        }
    }

    override suspend fun createUser(
        userName: String,
        email: String,
        password: String,
        phone: String,
        isProducer: Boolean
    ) : Resource<User> {
        return withContext(Dispatchers.IO){
            safeCall{
                //add new user to fire base auth
                val registrationResult = firebaseAuth.createUserWithEmailAndPassword(email,password).await()
                val userId = registrationResult.user?.uid!!
                val user = User(userName,email,phone,isProducer,null,userId) //Fix photos
                //add new user to fire base firestore
                userRef.document(userId).set(user).await()
                Resource.success(user)
            }
        }
    }

    override fun logOut() {
        firebaseAuth.signOut()
    }
}