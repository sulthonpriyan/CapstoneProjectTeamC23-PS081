//package com.dicoding.capstoneproject.model
//
//import android.app.Activity.RESULT_OK
//import com.firebase.ui.auth.AuthUI
//import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
//import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
//import com.google.firebase.auth.FirebaseAuth
//
//// See: https://developer.android.com/training/basics/intents/result
//private val signInLauncher = registerForActivityResult(
//    FirebaseAuthUIActivityResultContract(),
//) { res ->
//    this.onSignInResult(res)
//}
//
//// Choose authentication providers
//val providers = arrayListOf(
//    AuthUI.IdpConfig.EmailBuilder().build(),
////    AuthUI.IdpConfig.PhoneBuilder().build(),
////    AuthUI.IdpConfig.GoogleBuilder().build(),
////    AuthUI.IdpConfig.FacebookBuilder().build(),
////    AuthUI.IdpConfig.TwitterBuilder().build(),
//)
//
//// Create and launch sign-in intent
//val signInIntent = AuthUI.getInstance()
//    .createSignInIntentBuilder()
//    .setAvailableProviders(providers)
//    .build()
//signInLauncher.launch(signInIntent)
//
//private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
//    val response = result.idpResponse
//    if (result.resultCode == RESULT_OK) {
//        // Successfully signed in
//        val user = FirebaseAuth.getInstance().currentUser
//        // ...
//    } else {
//        // Sign in failed. If response is null the user canceled the
//        // sign-in flow using the back button. Otherwise check
//        // response.getError().getErrorCode() and handle the error.
//        // ...
//    }
//}