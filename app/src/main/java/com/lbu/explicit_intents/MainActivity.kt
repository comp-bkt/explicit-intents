package com.lbu.explicit_intents

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ExplicitIntentTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainPage()
                }
            }
        }

        Log.d(LOG_TAG, "-------")
        Log.d(LOG_TAG, "onCreate")
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

    companion object {
        private val LOG_TAG = MainActivity::class.java.simpleName
        const val EXTRA_MESSAGE = "com.lbu.explicit_intents.MainActivity.extra.MESSAGE"
    }
}

fun createToast(context: Context) {
    Toast.makeText(context, R.string.back_button_pressed, Toast.LENGTH_SHORT).show()
}

@Composable
fun MainPage(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val input: MutableState<String> = rememberSaveable { mutableStateOf("")}
    val replyMessage: MutableState<String> = rememberSaveable { mutableStateOf("")}
    var isVisible : MutableState<Boolean> = rememberSaveable {mutableStateOf(false)}

    val startForResult =
        rememberLauncherForActivityResult(ActivityResultContracts
                .StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                replyMessage.value = intent!!.getStringExtra(SecondActivity.EXTRA_REPLY).toString()
                isVisible.value = true;
            }
            else {
                isVisible.value = false;
                createToast(context = context)
            }
        }
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Gray),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        TopBox(visibility = isVisible.value, replyMessage = replyMessage, modifier = modifier)
        BottomBox(input = input, activityForResult = startForResult, context = context, modifier = modifier)

    }
}

@Composable
fun TopBox(modifier: Modifier = Modifier, replyMessage: MutableState<String>, visibility: Boolean) {
    val height:Int = if(visibility) 120 else 0
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height.dp)
            .background(color = Color.Yellow),
        contentAlignment = Alignment.CenterStart,
    ) {
        if(visibility) {
            Column() {
                Text(
                    text = stringResource(id = R.string.text_header_reply),
                    fontSize = 32.sp,
                )
                Text(
                    text = replyMessage.value,
                    fontSize = 16.sp,
                )
            }
        }
    }
}

@Composable
fun BottomBox(
    modifier: Modifier = Modifier,
    activityForResult: ManagedActivityResultLauncher<Intent, ActivityResult>,
    context: Context,
    input: MutableState<String>
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(color = Color.Yellow),
        contentAlignment = Alignment.CenterStart,
    ) {
        Row() {
            TextField(value = input.value,
                onValueChange = { it ->
                    input.value = it
                }
            )
            Button(onClick = {
                val message = input.value.toString()
                val intent = Intent(context, SecondActivity::class.java)
                intent.putExtra(MainActivity.EXTRA_MESSAGE, message)
                activityForResult.launch(intent)
            }) {
                Text(
                    text = "SEND",
                    fontSize = 16.sp,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IntentsPreview() {
    ExplicitIntentTheme {
        MainPage()
    }
}


