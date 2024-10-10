package com.example.practica06

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private val CHANNEL_ID = "Canal_notificacion"
    private val textTitle = "Titulo de notificación"
    private val textContent = "Este es el texto informativo de la notificación"

    private val notificationId = 100


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotificationChannel()

        val btnBasica: View = findViewById(R.id.btnBasica)
        val btnToque: View = findViewById(R.id.btnToque)
        val btnAccion: View = findViewById(R.id.btnAccion)
        val btnProgreso: View = findViewById(R.id.btnProgreso)

        btnBasica.setOnClickListener { notificacionBasica() }
        btnToque.setOnClickListener { notificacionToque() }
        btnAccion.setOnClickListener { notificacionBoton() }
        btnProgreso.setOnClickListener { notificacionProgress() }
    }

    private fun createNotificationChannel() {
        val name = "My Channel"
        val descriptionText = "Descripción del canal"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply()
        {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as
                    NotificationManager
        notificationManager.createNotificationChannel(channel)
    }//createNotificationChannel

    @SuppressLint("MissingPermission")
    private fun notificacionBasica() {

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_notifications_active_24)
            .setContentTitle(textTitle)
            .setContentText(textContent)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("Un texto más extenso que sobrepasa de una sola linea y se debe mostrar."))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                        with(NotificationManagerCompat.from(this)) {

                            notify(notificationId, builder.build())
                }
                        Toast.makeText(applicationContext,"Notificacion básica",Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("MissingPermission")
    private fun notificacionToque() {

        val intent = Intent(this, PendingActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0,
            intent,
            PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_notifications_active_24)
            .setContentTitle(textTitle)
            .setContentText("Toque la notificación para abrir Activity")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
                    notify(notificationId, builder.build())
        }
    }

    @SuppressLint("MissingPermission")
    private fun notificacionBoton() {
        val accionSi = Intent(this, PendingActivity::class.java).apply {
            putExtra("accion", 1)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val accionNo = Intent(this, PendingActivity::class.java).apply {
            putExtra("accion", 2)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntentSi: PendingIntent = PendingIntent.getActivity(this,
            0, accionSi,
            PendingIntent.FLAG_IMMUTABLE)
        val pendingIntentNo: PendingIntent = PendingIntent.getActivity(this,
            0, accionNo,
            PendingIntent.FLAG_IMMUTABLE)
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_notifications_active_24)
            .setContentTitle(textTitle)
            .setContentText("Notificación con botón")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            .addAction(R.drawable.baseline_notifications_active_24, getString(R.string.si),
                pendingIntentSi)
            .addAction(R.drawable.baseline_notifications_active_24, getString(R.string.no),
                pendingIntentNo)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
                    notify(notificationId, builder.build())
        }
    }

    @SuppressLint("MissingPermission")
    private fun notificacionProgress(){
        val builder = NotificationCompat.Builder(this, CHANNEL_ID).apply {
            setContentTitle(textTitle)
            setContentText("Descarga en progreso")
            setSmallIcon(R.drawable.baseline_notifications_active_24)
            setPriority(NotificationCompat.PRIORITY_LOW)
        }
        val PROGRESS_MAX = 100
        val PROGRESS_CURRENT = 0
        NotificationManagerCompat.from(this).apply {

            builder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false)
            notify(notificationId, builder.build())
            builder.setContentText("Descarga completa")
                .setProgress(PROGRESS_MAX, PROGRESS_CURRENT, true)

                .setTimeoutAfter(5000)
            notify(notificationId, builder.build())
        }
    }
}