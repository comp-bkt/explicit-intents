package com.lbu.explicit_intents

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lbu.explicit_intents.ui.theme.ExplicitIntentTheme

class SecondActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent
        val message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE).toString()
        setContent {
            ExplicitIntentTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ReplyPage(message = message)
                }
            }
        }
    }
    companion object {
        const val EXTRA_REPLY = "com.lbu.explicit_intents.SecondActivity.extra.REPLY"
    }
}

@Composable
fun ReplyPage(modifier: Modifier = Modifier, message: String) {
    val context = LocalContext.current
    val inputReply: MutableState<String> = rememberSaveable { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Gray),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        TopReplyBox(sentMessage = message, modifier = modifier)
        BottomReplyBox(inputReply = inputReply)

    }
}
@Composable
fun TopReplyBox(modifier: Modifier = Modifier, sentMessage:String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(color = Color.Yellow),
        contentAlignment = Alignment.CenterStart,
    ) {
            Column() {
                Text(
                    text = stringResource(id = R.string.text_header),
                    fontSize = 32.sp,
                )
                Text(
                    text = sentMessage,
                    fontSize = 16.sp,
                )
            }
    }
}

@Composable
fun BottomReplyBox(
    inputReply: MutableState<String>
) {
    val activity = (LocalContext.current as? Activity)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(color = Color.Yellow),
        contentAlignment = Alignment.CenterStart,
    ) {
        Row() {
            TextField(value = inputReply.value,
                onValueChange = { it ->
                    inputReply.value = it
                }
            )
            Button(onClick = {
                val message = inputReply.value
                val intent = Intent()
                intent.putExtra(SecondActivity.EXTRA_REPLY, message)
                activity?.setResult(RESULT_OK, intent)
                activity?.finish()
            }) {
                Text(
                    text = "REPLY",
                    fontSize = 16.sp,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReplyPreview() {
    ExplicitIntentTheme {
        ReplyPage(message = "TEST STRING")
    }
}