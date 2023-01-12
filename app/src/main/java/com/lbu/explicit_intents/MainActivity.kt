package com.lbu.explicit_intents

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.TextView
import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.view.View

class MainActivity : AppCompatActivity() {
    private lateinit var mMessageEditText: EditText
    private lateinit var mReplyHeadTextView: TextView
    private lateinit var mReplyTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(LOG_TAG, "-------")
        Log.d(LOG_TAG, "onCreate")
        mMessageEditText = findViewById(R.id.editText_main)
        mReplyHeadTextView = findViewById(R.id.text_header_reply)
        mReplyTextView = findViewById(R.id.text_message_reply)
        if (savedInstanceState != null) {
            val isVisible = savedInstanceState
                .getBoolean("reply_visible")
            if (isVisible) {
                mReplyHeadTextView.setVisibility(View.VISIBLE)
                mReplyTextView.setText(
                    savedInstanceState
                        .getString("reply_text")
                )
                mReplyTextView.setVisibility(View.VISIBLE)
            }
        }
    }

    fun launchSecondActivity(view: View?) {
        Log.d(LOG_TAG, "Button clicked!")
        val message = mMessageEditText.text.toString()
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra(EXTRA_MESSAGE, message)
        startActivityForResult(intent, TEXT_REQUEST)
        //startActivity(intent);
    }

    public override fun onActivityResult(
        requestCode: Int,
        resultCode: Int, data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == TEXT_REQUEST) {
            if (resultCode == RESULT_OK) {
                val reply = data!!.getStringExtra(SecondActivity.EXTRA_REPLY)
                mReplyHeadTextView.visibility = View.VISIBLE
                mReplyTextView.text = reply
                mReplyTextView.visibility = View.VISIBLE
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(LOG_TAG, "onStart")
    }

    override fun onPause() {
        super.onPause()
        Log.d(LOG_TAG, "onPause")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(LOG_TAG, "onRestart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(LOG_TAG, "onResume")
    }

    override fun onStop() {
        super.onStop()
        Log.d(LOG_TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(LOG_TAG, "onDestroy")
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (mReplyHeadTextView.visibility == View.VISIBLE) {
            outState.putBoolean("reply_visible", true)
            outState.putString("reply_text", mReplyTextView.text.toString())
        }
    }

    companion object {
        private val LOG_TAG = MainActivity::class.java.simpleName
        const val EXTRA_MESSAGE = "com.lbu.explicit_intents.MainActivity.extra.MESSAGE"
        const val TEXT_REQUEST = 6876
    }
}