package com.example.desafiofirebase_digitalhousemobile

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import dmax.dialog.SpotsDialog


class CadastraJogoFragment : Fragment() {

    lateinit var alertDialog: AlertDialog
    lateinit var storageReference: StorageReference
    private val CODE_IMG = 1000
    lateinit var btnAddImage: ImageButton
    lateinit var jogoUrl: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_cadastra_jogo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        config()

        btnAddImage = view.findViewById<ImageButton>(R.id.btnAddImage)
        btnAddImage.setOnClickListener {
            setIntent()
        }

        view.findViewById<Button>(R.id.btnSaveGame).setOnClickListener {
            findNavController().navigate(R.id.action_cadastraJogoFragment_to_jogoFragment)
        }
    }

    fun config() {
        alertDialog = SpotsDialog.Builder().setContext(activity).build()
        storageReference = FirebaseStorage.getInstance().getReference("prod_img")
    }

    fun setIntent() {
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Captura imagem"), CODE_IMG)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == CODE_IMG) {
            alertDialog.show()
            val uploadTask = storageReference.putFile(data!!.data!!)
            val task = uploadTask.continueWithTask { task ->
                if (task.isSuccessful) {
                    Toast.makeText(activity, "Chegando", Toast.LENGTH_SHORT).show()
                }
                storageReference!!.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    val jogoUrl = downloadUri!!.toString()
                        .substring(0, downloadUri.toString().indexOf("&token"))
                    Log.i("Url referencia ", jogoUrl)
                    alertDialog.dismiss()
                    Picasso.get().load(jogoUrl).into(btnAddImage)
                }
            }
        }
    }
}