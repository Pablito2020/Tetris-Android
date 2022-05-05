package com.pablo.tetris.presentation.finished

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.pablo.tetris.databinding.ActivityFinishedBinding
import com.pablo.tetris.infra.logs.LoggerGetter
import com.pablo.tetris.presentation.common.HideStatusBarActivity
import com.pablo.tetris.presentation.finished.sendmail.EmailData
import com.pablo.tetris.presentation.finished.sendmail.EmailSender
import kotlinx.coroutines.flow.collect

class FinishedActivity : HideStatusBarActivity() {

    private lateinit var binding: ActivityFinishedBinding
    private lateinit var model: FinishedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViewModel()
        setUpComponents()
    }

    fun setUpViewModel() {
        model = ViewModelProvider(this).get(FinishedViewModel::class.java)
        lifecycleScope.launchWhenCreated {
            model.results.collect {
                binding.emailEditText.error = it.emailError
                if (it.emailError == null) {
                    val data = EmailData(
                        destinationEmail = it.email,
                        text = getLogMessage(),
                        subject = "TODO: HERE SHOULD BE DATE"
                    )
                    EmailSender(this@FinishedActivity, data).send()
                }
            }
        }
    }

    fun setUpComponents() {
        binding.logText.movementMethod = ScrollingMovementMethod()
        binding.logText.text = LoggerGetter.get().getLog().joinToString(separator = "\n")
        binding.SendEmailButton.setOnClickListener { model.collect() }
        binding.emailEditText.addTextChangedListener { model.update(it.toString()) }
    }

    override fun onDestroy() {
        super.onDestroy()
        LoggerGetter.get().clear()
    }

    private fun getLogMessage() = LoggerGetter.get().getLog().joinToString(separator = "\n")

}