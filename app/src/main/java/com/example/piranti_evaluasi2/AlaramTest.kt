package com.example.piranti_evaluasi2;

import MyBroadcastReceiver
import android.Manifest
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.Calendar

class AlarmTest : AppCompatActivity() {

    private var timePicker: TimePicker? = null
    private var btnSetAlarm: Button? = null
    private var btnStartAlarm: Button? = null
    private var jam = 0
    private var menit = 0
    private var mediaPlayer: MediaPlayer? = null

    private val CHANNEL_ID = "alarm_channel"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alaram_test)

        createNotificationChannel()

        timePicker = findViewById(R.id.timePicker)
        btnSetAlarm = findViewById(R.id.btnSetAlarm)
        btnStartAlarm = findViewById(R.id.btnStartAlarm)

        timePicker?.setOnTimeChangedListener { _, hourOfDay, minute ->
            jam = hourOfDay
            menit = minute
        }

        btnSetAlarm?.setOnClickListener(View.OnClickListener {
            Toast.makeText(this@AlarmTest, "Set Alarm $jam:$menit", Toast.LENGTH_SHORT).show()
            setAlarm()
            showNotification("Alarm Set", "Alarm has been set for $jam:$menit")
        })

        btnStartAlarm?.setOnClickListener(View.OnClickListener {
            Toast.makeText(this@AlarmTest, "Start Alarm", Toast.LENGTH_SHORT).show()
            playRingtone(jam, menit)
            showNotification("Alarm Started", "Alarm is now active")
        })
    }

    private fun setAlarm() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val date = Calendar.getInstance().timeInMillis
        val cal_alarm = Calendar.getInstance()
        cal_alarm.timeInMillis = date
        cal_alarm[Calendar.HOUR_OF_DAY] = jam
        cal_alarm[Calendar.MINUTE] = menit
        cal_alarm[Calendar.SECOND] = 0

        if (cal_alarm.before(Calendar.getInstance())) {
            cal_alarm.add(Calendar.DATE, 1)
        }

        val intent = Intent(this@AlarmTest, MyBroadcastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this@AlarmTest, 0, intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                cal_alarm.timeInMillis,
                pendingIntent
            )
        } else {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                cal_alarm.timeInMillis,
                pendingIntent
            )
        }
    }

    private fun playRingtone(jam: Int, menit: Int) {
        try {
            stopRingtone()

            val now = Calendar.getInstance()
            val currentHour = now.get(Calendar.HOUR_OF_DAY)
            val currentMinute = now.get(Calendar.MINUTE)

            val differenceHours = jam - currentHour
            val differenceMinutes = menit - currentMinute
            val durationInMillis = (differenceHours * 60 + differenceMinutes) * 60 * 1000
            val duration = if (durationInMillis < 0) durationInMillis + 24 * 60 * 60 * 1000 else durationInMillis

            mediaPlayer = MediaPlayer.create(this, R.raw.alarm)

            mediaPlayer?.setOnCompletionListener {
                stopRingtone()
            }

            mediaPlayer?.start()

            mediaPlayer?.let {
                Handler(Looper.getMainLooper()).postDelayed({
                    stopRingtone()
                }, duration.toLong())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun stopRingtone() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
            }
            it.release()
        }
        mediaPlayer = null
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showNotification(title: String, content: String) {
        val i = Intent(this, AlarmTest::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent =
            PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)

        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(
                    this@AlarmTest,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(1, builder.build())
        }
    }
}
