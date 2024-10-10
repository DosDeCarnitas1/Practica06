package com.example.practica06

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificacionActivity : AppCompatActivity() {

    private lateinit var peso: EditText
    private lateinit var estatura: EditText
    private lateinit var pendingIntentSI: PendingIntent
    private lateinit var pendingIntentNO: PendingIntent
    private val CHANNEL_ID = "Notificacion_IMC"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notificacion)

        peso = findViewById(R.id.edtPeso)
        estatura = findViewById(R.id.edtEstatura)
    }

    fun calcularIMC(view: View){

        var imc: Float
        var est: Float
        var pes: Float

        if(peso.text.isNotEmpty() && peso.text.isNotBlank() &&
            estatura.text.isNotEmpty() && estatura.text.isNotBlank()){
            pes = peso.text.toString().toFloat()
            est = estatura.text.toString().toFloat()
            imc = pes / (est * est)
            Toast.makeText(applicationContext, "IMC: $imc",Toast.LENGTH_SHORT).show()
            if(imc > 25.0f){
                Toast.makeText(applicationContext, "Estas pasado de peso!!!",Toast.LENGTH_SHORT).show()

                        createNotificationChannel()
                        configureActions()
                        buildNotificacion()
            } else{
                Toast.makeText(applicationContext, "Estas en buen estado!!!",Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(applicationContext, "Campos vacios!!!",Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val notificationId = 200
    }
    private fun createNotificationChannel() {
        val name = "Canal_IMC"
        val descriptionText = "Canal nutricional"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply()
        {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as
                    NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun configureActions() {

        val accionSi = Intent(this, FormularioActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        pendingIntentSI = PendingIntent.getActivity(this, 0,
            accionSi,PendingIntent.FLAG_IMMUTABLE)

        val accionNo = Intent(this, InfoActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        pendingIntentNO = PendingIntent.getActivity(this, 0,
            accionNo,PendingIntent.FLAG_IMMUTABLE)
    }

    @SuppressLint("MissingPermission")
    private fun buildNotificacion() {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_notifications_active_24)
            .setContentTitle("Nutriologos AC")
            .setContentText("Visitanos, nosotros te podemos apoyar. ¿Desea agendar una cita?")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                .addAction(R.drawable.baseline_notifications_active_24, getString(R.string.si),
                    pendingIntentSI)
                .addAction(R.drawable.baseline_notifications_active_24, getString(R.string.no),
                    pendingIntentNO)
                .setAutoCancel(true)

                    with(NotificationManagerCompat.from(this)) {

                        notify(notificationId, builder.build())
            }
    }
}