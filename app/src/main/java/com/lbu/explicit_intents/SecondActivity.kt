package com.lbu.explicit_intents

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.os.Bundle
import android.content.Intent
import android.widget.TextView
import android.view.View

class SecondActivity : AppCompatActivity() {
    private var mReply: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        mReply = findViewById(R.id.editText_second)
        val intent = intent
        val message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE)
        val textView = findViewById<TextView>(R.id.text_message)
        textView.text = message
    }

    fun returnReply(view: View?) {
        val reply = mReply!!.text.toString()
        val replyIntent = Intent()
        replyIntent.putExtra(EXTRA_REPLY, reply)
        setResult(RESULT_OK, replyIntent)
        finish()
    }

    companion object {
        const val EXTRA_REPLY = "com.lbu.explicit_intents.SecondActivity.extra.REPLY"
    }
}