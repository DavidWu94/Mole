package tw.edu.pu.csim.s1130045.mole

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable // 1. 匯入 rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tw.edu.pu.csim.s1130045.mole.ui.theme.MoleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoleTheme {
                // 使用 Scaffold 來處理邊界和系統 UI
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MoleScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MoleScreen(modifier: Modifier = Modifier) {
    // 2. 將 remember 改為 rememberSaveable
    var counter by rememberSaveable { mutableLongStateOf(0) }

    // 使用一個 Box 作為根佈局
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // 1. 將 Text (分數) 放在 Box 中，並對齊到頂部中央
        Text(
            text = counter.toString(),
            modifier = Modifier
                .align(Alignment.TopCenter) // 對齊到頂部中央
                .padding(top = 32.dp),      // 留出一些邊距
            fontSize = 48.sp                // 讓分數更顯眼
        )

        // 2. 將 Image (地鼠) 也放在 Box 中
        Image(
            painter = painterResource(id = R.drawable.mole),
            contentDescription = "地鼠",
            modifier = Modifier
                .offset { IntOffset(50, 200) } // 從左上角偏移
                .size(150.dp)
                .clickable { counter++ }
        )
    }
}