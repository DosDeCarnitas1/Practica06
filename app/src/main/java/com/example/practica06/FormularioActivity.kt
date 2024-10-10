package com.example.practica06

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import com.example.practica06.NotificacionActivity.Companion.notificationId

class FormularioActivity : AppCompatActivity() {

    private lateinit var nom: EditText
    private lateinit var ape: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario)

        NotificationManagerCompat.from(this).apply {
            cancel(notificationId)
        }

        setSupportActionBar(findViewById(R.id.barraForm))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        nom = findViewById(R.id.edtNombre)
        ape = findViewById(R.id.edtApellido)
    }
    fun agendarCita(view: View){

        if(nom.text.isNotEmpty() && nom.text.isNotBlank() &&
            ape.text.isNotEmpty() && ape.text.isNotBlank()){
            Toast.makeText(applicationContext, "Cita registrada a nombre de ${nom.text} ${ape.text}.", Toast.LENGTH_SHORT).show()
            nom.text = null
            ape.text = null
            nom.requestFocus()
        } else {
            Toast.makeText(applicationContext, "Campos vacios.",
                Toast.LENGTH_SHORT).show()
            nom.requestFocus()
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent = Intent(this, NotificacionActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        return super.onOptionsItemSelected(item)
    }
}