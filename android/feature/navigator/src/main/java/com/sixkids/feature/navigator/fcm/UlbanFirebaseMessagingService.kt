package com.sixkids.feature.navigator.fcm

import android.Manifest
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.sixkids.feature.navigator.MainActivity
import com.sixkids.designsystem.R as DesignSystemR

private const val TAG = "D107"

class UlbanFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "onNewToken: $token")

    }

    override fun onMessageReceived(message: RemoteMessage) {
        var messageTitle = ""
        var messageContent = ""

        message.notification?.let {
            messageTitle = it.title.toString()
            messageContent = it.body.toString()
        } ?: run {
            message.data.isNotEmpty().let {
                messageTitle = message.data["title"].toString()
                messageContent = message.data["body"].toString()
            }
        }

        Log.d(TAG, "onMessageReceived: $messageTitle, $messageContent")

        val mainIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntentFlags =
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT

        val mainPendingIntent: PendingIntent = PendingIntent.getActivity(
            this,
            0,
            mainIntent,
            pendingIntentFlags,
        )

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(DesignSystemR.drawable.announce)
            .setContentTitle(messageTitle)
            .setContentText(messageContent)
            .setAutoCancel(true)
            .setContentIntent(mainPendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS,
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Log.d(TAG, "onMessageReceived: no permission")
                return
            }
        }
        Log.d(TAG, "onMessageReceived: ${builder.build()}")

        notificationManager.notify(107, builder.build())

    }

    companion object {
        const val CHANNEL_ID = "ULBAN_NOTIFICATION_CHANNEL"
    }

}
