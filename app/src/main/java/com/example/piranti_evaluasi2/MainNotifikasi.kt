package com.example.piranti_evaluasi2

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainNotifikasi : AppCompatActivity() {
    private lateinit var notificationmanager: NotificationManagerCompat

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifikasi)

        // Inisialisasi manajer notifikasi
        notificationmanager = NotificationManagerCompat.from(this)

        val buttonsend1 = findViewById<Button>(R.id.buttonSend1)
        val buttonsend2 = findViewById<Button>(R.id.buttonSend2)
        val editTitle = findViewById<EditText>(R.id.editTitle)
        val editMessage = findViewById<EditText>(R.id.editMessage)

        buttonsend1.setOnClickListener {
            val title = editTitle.text.toString()
            val message = editMessage.text.toString()

            // Membuat objek NotificationCompat.Builder untuk membangun notifikasi
            val builder = NotificationCompat.Builder(this, BaseApplication.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_faporit)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)

            // Membuat objek notifikasi dari builder
            val notifikasi = builder.build()

            // Menampilkan notifikasi dengan menggunakan NotificationManagerCompat
            this.notificationmanager.notify(1, notifikasi)
        }
        buttonsend2.setOnClickListener {
            val title = editTitle.text.toString()
            val message = editMessage.text.toString()

            // Membuat objek NotificationCompat.Builder untuk membangun notifikasi
            val builder = NotificationCompat.Builder(this, BaseApplication.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_faporit)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)

            // Membuat objek notifikasi dari builder
            val notifikasi = builder.build()

            // Menampilkan notifikasi dengan menggunakan NotificationManagerCompat
            this.notificationmanager.notify(2, notifikasi)
        }
    }
}
