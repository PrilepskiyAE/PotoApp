package com.ambrella.fotoApp.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ambrella.fotoApp.ui.state.MainAction
import com.ambrella.fotoApp.ui.theme.PotoAppTheme
import com.yandex.authsdk.YandexAuthLoginOptions
import com.yandex.authsdk.YandexAuthOptions
import com.yandex.authsdk.YandexAuthResult
import com.yandex.authsdk.YandexAuthSdk
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PotoAppTheme {
                val state by mainViewModel.state.collectAsState()
                val sdk = YandexAuthSdk.create(YandexAuthOptions(applicationContext))
                val launcher = rememberLauncherForActivityResult(sdk.contract) { result ->
                    when (result) {
                        is YandexAuthResult.Success -> run {
                            mainViewModel.doAction(MainAction.OnClickLogin(result.token.value))
                        }

                        is YandexAuthResult.Failure -> {
                            Log.d("TAG999", "Failure: $result")
                        }

                        YandexAuthResult.Cancelled -> {
                            Log.d("TAG999", "Cancelled: $result ")
                        }
                    }
                }
                val loginOptions = YandexAuthLoginOptions()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Button(
                            onClick = {
                                launcher.launch(loginOptions)
                            },
                            content = {
                                Text(text = "AuthLogin")
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(55.dp)
                                .padding(horizontal = 36.dp)
                        )
                    }
                }
            }
        }
    }
}
