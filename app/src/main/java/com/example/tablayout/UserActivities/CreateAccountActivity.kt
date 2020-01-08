@file:Suppress("DEPRECATION")

package com.example.tablayout.UserActivities

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils.isEmpty
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.tablayout.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.create_user_activity.*
import java.util.*

class CreateAccountActivity : AppCompatActivity() {

    private var etName: EditText? = null
    private var etEmail: EditText? = null
    private var etPassword: EditText? = null
    private var btnCreateAccount: Button? = null
    private var btnSignIn:Button? = null
    private var mProgressBar: ProgressDialog? = null
    private var etPicture: ImageView?=null

    //firebase
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    private val TAG = "CreateAccountActivity"
    //global variables
    private var name: String? = null
    private var email: String? = null
    private var password: String? = null
    private var picture:String?=null
    private var picUrl:String?=null

        companion object{
        val TAG1 ="CreateAccountActivity"
   }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_user_activity)

        initialise()

                profilePic.setOnClickListener {
            Log.d(TAG, "Try to show photo selector")

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
    }

    private fun initialise() {
        etName = findViewById<View>(R.id.txtUsername) as EditText
        etEmail = findViewById<View>(R.id.txtEmail) as EditText
        etPassword = this.findViewById<View>(R.id.txtPassword) as EditText
        etPicture = findViewById<View>(R.id.profilePic) as ImageView
        btnCreateAccount = findViewById<View>(R.id.signUpBtn) as Button
        btnSignIn = findViewById<View>(R.id.signUpRedirect) as Button
        mProgressBar = ProgressDialog(this)
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference.child("Users")
        mAuth = FirebaseAuth.getInstance()



        btnCreateAccount!!.setOnClickListener {

           // createNewAccount(picUrl.toString())
            uploadImageToFirebaseStorage()

        }

        btnSignIn!!.setOnClickListener {
            startActivity(Intent(this@CreateAccountActivity, LoginActivity::class.java))
        }
    }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            // proceed and check what the selected image was....
            Log.d(TAG, "Photo was selected")

            pictureUrl = data.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, pictureUrl)

            selectphoto_imageview_register.setImageBitmap(bitmap)

            profilePic.alpha = 0f
        }
    }

    private fun createNewAccount(ProfileImage:String){

        name = etName?.text.toString()
        email = etEmail?.text.toString()
        password = etPassword?.text.toString()
        picture = etPicture?.toString()

        if (!isEmpty(name) && !isEmpty(email) && !isEmpty(password) && !isEmpty(picture)) {

            mProgressBar!!.setMessage("Registering User...")
            mProgressBar!!.show()

            mAuth!!
                .createUserWithEmailAndPassword(email!!, password!!)
                .addOnCompleteListener(this) { task ->
                    mProgressBar!!.hide()
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val userId = mAuth!!.currentUser!!.uid
                        //Verify Email
                        verifyEmail()


                        val ref = FirebaseDatabase.getInstance().getReference("/Users/$userId")


                        val userM = Users(email.toString(),name.toString(),ProfileImage)
                        ref.setValue(userM)


                        updateUserInfoAndUI()
                        Toast.makeText(this,"Successfully Registered!!",Toast.LENGTH_SHORT).show()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(this@CreateAccountActivity, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }


        } else {
            Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show()
        }


    }

        var pictureUrl: Uri? = null


    private fun uploadImageToFirebaseStorage() {
        if (pictureUrl == null) return

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/pictures/$filename")

        ref.putFile(pictureUrl!!)
            .addOnSuccessListener {
                Log.d(TAG, "Successfully uploaded image: ${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener{
                    Log.d(TAG, "File Location: $it")


                    createNewAccount(it.toString())
                }

            }
            .addOnFailureListener {
                Log.d(TAG, "Failed to upload image to storage: ${it.message}")
            }
    }

    private fun updateUserInfoAndUI() {
        //start next activity
        val intent = Intent(this@CreateAccountActivity, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun verifyEmail() {
        val mUser = mAuth!!.currentUser
        mUser!!.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@CreateAccountActivity,
                        "Verification email sent to " + mUser.getEmail(),
                        Toast.LENGTH_SHORT).show()
                } else {
                    Log.e(TAG, "sendEmailVerification", task.exception)
                    Toast.makeText(this@CreateAccountActivity,
                        "Failed to send verification email.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}
