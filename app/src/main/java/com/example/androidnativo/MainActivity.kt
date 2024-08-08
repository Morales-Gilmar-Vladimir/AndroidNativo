package com.example.androidnativo

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.androidnativo.ui.theme.AndroidNativoTheme
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : ComponentActivity() {

    private lateinit var editTextData: EditText
    private lateinit var buttonSave: Button
    private lateinit var textViewData: TextView
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializa Firebase
        FirebaseApp.initializeApp(this)

        // Encuentra vistas por ID
        editTextData = findViewById(R.id.editTextData)
        buttonSave = findViewById(R.id.buttonSave)
        textViewData = findViewById(R.id.textViewData)

        // Referencia a la base de datos
        databaseReference = FirebaseDatabase.getInstance().getReference("data")

        // Configura el botón para guardar datos
        buttonSave.setOnClickListener {
            val data = editTextData.text.toString()
            if (data.isNotEmpty()) {
                // Guarda los datos en Firebase
                databaseReference.setValue(data)
            }
        }

        // Configura el Listener para leer datos
        databaseReference.get().addOnSuccessListener { snapshot ->
            val data = snapshot.getValue(String::class.java)
            textViewData.text = data ?: "No data"
        }
    }
}
