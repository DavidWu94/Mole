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
import androidx.compose.runtime.collectAsState // 1. 匯入 collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel // 2. 匯入 viewModel 函式
import tw.edu.pu.csim.s1130045.mole.ui.theme.MoleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // 修正：這裡應該是 enableEdgeToEdge()
        setContent {
            MoleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MoleScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MoleScreen(
    modifier: Modifier = Modifier,
    viewModel: MoleViewModel = viewModel() // 3. 取得 ViewModel 實例
) {
    // 4. 從 ViewModel 讀取 counter 狀態
    // collectAsState 會將 StateFlow 轉換成 Compose 的 State
    // 當 ViewModel 的 counter 改變時，UI 會自動重組
    val counter by viewModel.counter.collectAsState()

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = counter.toString(),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 32.dp),
            fontSize = 48.sp
        )

        Image(
            painter = painterResource(id = R.drawable.mole),
            contentDescription = "地鼠",
            modifier = Modifier
                .offset { IntOffset(50, 200) }
                .size(150.dp)
                .clickable {
                    // 5. 點擊時，呼叫 ViewModel 的函式，而不是直接修改 counter++
                    viewModel.incrementCounter()
                }
        )
    }
}