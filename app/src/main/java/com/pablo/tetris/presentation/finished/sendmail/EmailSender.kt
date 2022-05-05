package com.pablo.tetris.presentation.finished.sendmail

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.pablo.tetris.R

class EmailSender(private val activity: Activity, private val emailData: EmailData) {

    @SuppressLint("QueryPermissionsNeeded")
        fun send() {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(emailData.destinationEmail))
            putExtra(Intent.EXTRA_SUBJECT, emailData.subject)
            putExtra(Intent.EXTRA_TEXT, emailData.text)
        }
        if (intent.resolveActivity(activity.packageManager) != null) {
            activity.startActivity(intent)
            Toast.makeText(activity, activity.getText(R.string.opening_mail), Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(activity, activity.getText(R.string.error_opening_mail), Toast.LENGTH_LONG).show()
        }
    }
}