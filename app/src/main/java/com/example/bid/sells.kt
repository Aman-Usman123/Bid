package com.example.bid

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage


class sells : Fragment() {
    private lateinit var uri: Uri
    private var storageReference = Firebase.storage
    private lateinit var progressBar: ProgressBar
    private var isProgressBarVisible = false
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val v= inflater.inflate(R.layout.fragment_sells, container, false)
        val imageview = v.findViewById<ImageView>(R.id.imageo)
        val product = v.findViewById<EditText>(R.id.editTextTextPersonName5)
        val catagory = v.findViewById<EditText>(R.id.editTextTextPersonName6)
        val sizes = v.findViewById<EditText>(R.id.editTextTextPersonName7)
        val weight = v.findViewById<EditText>(R.id.editTextTextPersonName8)
        val select2 =v. findViewById<Button>(R.id.uplod)
        val save = v.findViewById<Button>(R.id.save)
        progressBar=v.findViewById(R.id.progressBar)
        storageReference = FirebaseStorage.getInstance()
        progressBar.visibility = if (isProgressBarVisible) View.VISIBLE else View.GONE
        val imageGallery = registerForActivityResult(

            ActivityResultContracts.GetContent(),
            ActivityResultCallback { imagePath ->
                imageview.setImageURI(imagePath)
                uri = imagePath!!;
            })
        select2.setOnClickListener {
            imageGallery.launch("image/*")
        }
        save.setOnClickListener {
            isProgressBarVisible = true // Set the visibility of the progress bar to true
            progressBar.visibility = View.VISIBLE
            storageReference.getReference("Images").child(System.currentTimeMillis().toString())
                .putFile(uri)

                .addOnSuccessListener { task ->
                    task.metadata!!.reference!!.downloadUrl.addOnSuccessListener { downlaodUrl ->

                        val uid = FirebaseAuth.getInstance().currentUser!!.uid
                        val imageMap1 = mapOf(
                            "idofproduct" to uid,
                            "ImageUrl" to downlaodUrl.toString(),
                            "product" to product.text.toString(),
                            "catagory" to catagory.text.toString(),
                            "size" to sizes.text.toString(),
                            "weight" to weight.text.toString(),

                            )

                        val databaseReference = FirebaseDatabase.getInstance().getReference("UserPosts")
                        val timestamp= ServerValue.TIMESTAMP
                        databaseReference.child(uid).child("BidsRecord").setValue(null)
                        databaseReference.child(uid).child("UserInfo").setValue(imageMap1)
                        databaseReference.child(uid).child("timestamp").setValue(timestamp)

                            .addOnSuccessListener {
                                product.text.clear()
                                catagory.text.clear()
                                sizes.text.clear()
                                weight.text.clear()
                                imageview.setImageDrawable(null)
                                isProgressBarVisible = false
                                progressBar.visibility = View.GONE

                                Toast.makeText(requireContext(), "Successfully inserted", Toast.LENGTH_SHORT)
                                    .show()
                            }
                            .addOnFailureListener {

                                Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show()
                            }
                    }
                }

        }

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().supportFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                val intent = Intent(requireActivity(), playing::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                requireActivity().finish()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)



    return v
    }


}