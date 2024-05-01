package com.example.reduxforandroid

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat.startActivity
import com.example.reduxforandroid.counter.CounterActivity
import com.example.reduxforandroid.todos.ui.ToDoActivity
import com.example.reduxforandroid.ui.theme.ReduxForAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReduxForAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    Greeting("Android")
                    Column{
                        CounterColumn()
                        ToDoColumn()
                    }

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun CounterColumn() {
    val context = LocalContext.current
    Column {
        Button(
            onClick = {
                val intent = Intent(context, CounterActivity::class.java)
                startActivity(context,intent,null)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Go To Counter")
        }
    }
}

@Composable
fun ToDoColumn() {
    val context = LocalContext.current
    Column {
        Button(
            onClick = {
                val intent = Intent(context, ToDoActivity::class.java)
                startActivity(context,intent,null)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Go To To Do List")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ReduxForAndroidTheme {
        Greeting("Android")
    }
}