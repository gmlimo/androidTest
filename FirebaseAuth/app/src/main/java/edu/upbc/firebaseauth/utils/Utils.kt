package edu.upbc.firebaseauth.utils

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

//Toast message
fun showMessage(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

//Set Fragment
fun AppCompatActivity.setFragment(view: Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction().apply {
        replace(view, fragment)
        commit()
    }
}

fun Fragment.changeFragment(view: Int, fragment: Fragment) {
    val fragmentTransaction = fragmentManager?.beginTransaction()
    fragmentTransaction?.replace(view, fragment)
    fragmentTransaction?.addToBackStack(null)
    fragmentTransaction?.commit()
}