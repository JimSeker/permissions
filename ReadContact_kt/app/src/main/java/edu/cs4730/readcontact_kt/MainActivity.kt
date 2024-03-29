package edu.cs4730.readcontact_kt


import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import edu.cs4730.readcontact_kt.databinding.ActivityMainBinding


/**
 * This is an example to read the contacts lists on the phone.
 *
 *
 * We need the check permissions as well.  READ_CONTACTS
 */
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var TAG = "MainActivity"
    lateinit var rpl: ActivityResultLauncher<Array<String>>
    private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.READ_CONTACTS)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.getRoot())

        //this allows us to check with multiple permissions, but in this case (currently) only need 1.
        rpl = registerForActivityResult<Array<String>, Map<String, Boolean>>(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { isGranted ->
            var granted = true
            for ((key, value) in isGranted) {
                logthis("$key is $value")
                if (!value) granted = false
            }
            if (granted) fetchContacts()
        }
        if (!allPermissionsGranted()) rpl.launch(REQUIRED_PERMISSIONS) else {
            logthis("All permissions have been granted already.")
            fetchContacts()
        }
    }

    fun logthis(msg: String) {
        binding.logger.append(msg + "\n")
        Log.d(TAG, msg)
    }

    //ask for permissions when we start.
    private fun allPermissionsGranted(): Boolean {
        for (permission in REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    @SuppressLint("Range")
    fun fetchContacts() {
        var phoneNumber: String
        var email: String
        val CONTENT_URI = ContactsContract.Contacts.CONTENT_URI
        val _ID = ContactsContract.Contacts._ID
        val DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME
        val HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER
        val PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID
        val NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER
        val EmailCONTENT_URI = ContactsContract.CommonDataKinds.Email.CONTENT_URI
        val EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID
        val DATA = ContactsContract.CommonDataKinds.Email.DATA
        val contentResolver = contentResolver
        val cursor = contentResolver.query(CONTENT_URI, null, null, null, null) ?: return

        // Loop for every contact in the phone
        if (cursor.count > 0) {
            while (cursor.moveToNext()) {
                val contact_id = cursor.getString(cursor.getColumnIndex(_ID))
                logthis("\nID:$contact_id")
                val name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME))
                val hasPhoneNumber =
                    cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)).toInt()
                if (hasPhoneNumber > 0) {
                    logthis("Name:$name")
                    // Query and loop for every phone number of the contact
                    val phoneCursor = contentResolver.query(
                        PhoneCONTENT_URI, null,
                        "$Phone_CONTACT_ID = ?", arrayOf(contact_id), null
                    )
                    while (phoneCursor!!.moveToNext()) {
                        phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER))
                        logthis("Phone number:$phoneNumber")
                    }
                    phoneCursor.close()

                    // Query and loop for every email of the contact
                    val emailCursor = contentResolver.query(
                        EmailCONTENT_URI, null,
                        "$EmailCONTACT_ID = ?", arrayOf(contact_id), null
                    )
                    while (emailCursor!!.moveToNext()) {
                        email = emailCursor.getString(emailCursor.getColumnIndex(DATA))
                        logthis("Email:$email")
                    }
                    emailCursor.close()
                }
            }
        }
        cursor.close()
    }
}

