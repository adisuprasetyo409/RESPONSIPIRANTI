
//import android.media.Ringtone
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.piranti_evaluasi2.AlarmTest
import com.example.piranti_evaluasi2.R

class MyBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        try {
            checkAndPerformAction(context)
        } catch (e: SecurityException) {
            e.printStackTrace()
            // Handle SecurityException here
        }
    }

    private fun checkAndPerformAction(context: Context) {
        val permission = android.Manifest.permission.VIBRATE

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
                vibrate(context)
            } else {
                requestPermission(context, permission)
            }
        } else {
            vibrate(context)
        }
    }

    private fun vibrate(context: Context) {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as android.os.Vibrator
        vibrator.vibrate(2000)
        showNotification(context)
        playAlarmSound(context)
    }

    @SuppressLint("MissingPermission")
    private fun showNotification(context: Context) {
        val i = Intent(context, AlarmTest::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = android.app.PendingIntent.getActivity(context, 0, i, android.app.PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(context, "Notify")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentText("Alarm Reminders")
            .setContentTitle("Hey, Wake Up!")
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)

        // Di dalam fungsi atau metode yang sesuai, misalnya, showNotification()
        val notificationManagerCompat = NotificationManagerCompat.from(context)
        notificationManagerCompat.notify(200, builder.build())

    }

    private fun playAlarmSound(context: Context) {
        try {
            val alarmSound: Uri = Uri.parse("android.resource://${context.packageName}/${R.raw.alarm}")
            val ringtone = RingtoneManager.getRingtone(context, alarmSound)
            ringtone.play()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun requestPermission(context: Context, permission: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Minta izin kepada pengguna
            val permissions = arrayOf(permission)
            val requestCode = 123 // Ganti dengan kode permintaan yang sesuai
            (context as AppCompatActivity).requestPermissions(permissions, requestCode)
        }
    }
}
