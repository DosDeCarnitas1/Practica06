package com.example.practica06

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat

class PendingActivity :AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pending)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val texto: TextView = findViewById(R.id.txtPending1)

        val respuesta = intent.getIntExtra("accion", 1)

        texto.text = if (respuesta==1) "Seleccionaste Aceptar" else
            "Seleccionaste Cancelar"

        with(NotificationManagerCompat.from(this)) {
            cancelAll()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Toast.makeText(this,"REGRESO",Toast.LENGTH_SHORT).show()

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        return super.onOptionsItemSelected(item)
    }
}