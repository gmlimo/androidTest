package org.bedu.mvp.addcontact

import android.app.Activity
import android.content.Intent
import org.bedu.mvp.model.Contact

class AddContactPresenter {

    val contact = Contact()

    fun updateName(name: String) {
        contact.name = name
    }

    fun updatePhone(phone: String) {
        contact.phone = phone
    }

    fun addContact(activity: Activity) {
        val returnIntent = Intent()
        returnIntent.putExtra("new_contact", contact)
        activity.setResult(Activity.RESULT_OK, returnIntent)
        activity.finish()
    }

    interface View {
      fun addContact()
    }
}