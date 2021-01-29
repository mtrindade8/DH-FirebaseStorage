package com.example.desafiofirebase_digitalhousemobile

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import dmax.dialog.SpotsDialog
import java.util.*


class CadastraJogoFragment : Fragment() {

    lateinit var alertDialog: AlertDialog
    lateinit var storageReference: StorageReference
    private  val CODE_IMG = 1000
    lateinit var btnAddImage: ImageButton
    lateinit var edtTitulo: EditText
    lateinit var edtAnoLancamento: EditText
    lateinit var edtDescricao: EditText
    lateinit var imgJogo: CircleImageView
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

        edtTitulo = view.findViewById<EditText>(R.id.edtName)
        edtAnoLancamento = view.findViewById<EditText>(R.id.edtCreated)
        edtDescricao = view.findViewById<EditText>(R.id.edtDescription)

        imgJogo = view.findViewById<CircleImageView>(R.id.imgCapa)

        btnAddImage = view.findViewById<ImageButton>(R.id.btnAddImage)
        btnAddImage.setOnClickListener {
            setIntent()
        }

        view.findViewById<Button>(R.id.btnSaveGame).setOnClickListener {
            saveOnFirebase()
            findNavController().navigate(R.id.action_cadastraJogoFragment_to_jogoFragment)
        }
    }

    fun config() {
        alertDialog = SpotsDialog.Builder().setContext(activity).build()
        val filename = UUID.randomUUID().toString()
        storageReference = FirebaseStorage.getInstance().getReference("/images/${filename}")
    }

    fun setIntent() {
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Captura imagem"), CODE_IMG)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CODE_IMG) {
            alertDialog.show()
            val uploadTask = storageReference.putFile(data!!.data!!)
            val task = uploadTask.continueWithTask { task ->
                if (task.isSuccessful) {
                    Log.i("CadastraJogoFragment", "Carregando imagem")
                }
                storageReference!!.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    jogoUrl = downloadUri!!.toString()
                            .substring(0, downloadUri.toString().indexOf("&token"))
                    Log.i("Url referencia ", jogoUrl)
                    alertDialog.dismiss()
                    imgJogo.visibility = View.VISIBLE
                    Picasso.get().load(jogoUrl).into(imgJogo)
                }
            }
        }
    }

    fun saveOnFirebase(){
        val jogo = Jogo(
                edtTitulo.text.toString(),
                edtAnoLancamento.text.toString().toInt(),
                edtDescricao.text.toString(),
                jogoUrl
        )

        FirebaseFirestore.getInstance().collection("jogos")
                .add(jogo)
                .addOnSuccessListener {
                }.addOnFailureListener {
                    Toast.makeText(
                            activity,
                            "Erro: " + it.message.toString(),
                            Toast.LENGTH_LONG
                    ).show()
                    Log.e("Teste", it.message.toString())
                }
    }

}