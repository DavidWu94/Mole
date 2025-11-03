package tw.edu.pu.csim.s1130045.mole

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign // 1. 匯入 TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import tw.edu.pu.csim.s1130045.mole.ui.theme.MoleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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
    viewModel: MoleViewModel = viewModel()
) {
    // 從 ViewModel 讀取所有狀態
    val counter = viewModel.counter
    val stay = viewModel.stay
    val moleX = viewModel.moleX
    val moleY = viewModel.moleY
    val isGameActive = viewModel.isGameActive

    val moleSize = 150.dp
    val density = LocalDensity.current

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // --- 1. 頂部資訊區 (Info Bar) ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "打地鼠遊戲(資管二A 411300459 吳岱威)", // <--- 請修改 "您的姓名"

                // --- 修正開始 ---
                fontSize = 28.sp, // 2. 稍微縮小字體
                fontWeight = FontWeight.Bold,
                lineHeight = 34.sp, // 3. 明確設定行高，大於字體
                textAlign = TextAlign.Center // 4. 讓換行後的文字也置中
                // --- 修正結束 ---
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "分數: $counter",
                    fontSize = 40.sp,
                    color = Color.Blue
                )
                Text(
                    text = "時間: $stay",
                    fontSize = 32.sp,
                    color = Color.Red
                )
            }
            if (!isGameActive) {
                Text(
                    text = "時間到！",
                    fontSize = 48.sp,
                    color = Color.Red,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        } // --- 資訊區結束 ---

        // --- 2. 遊戲區 (Game Area) ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .onSizeChanged { size ->
                    val screenWidthPx = size.width.toFloat()
                    val screenHeightPx = size.height.toFloat()
                    val moleSizePx = with(density) { moleSize.toPx() }
                    viewModel.updateScreenSize(screenWidthPx, screenHeightPx, moleSizePx)
                }
        ) {
            Image(
                painter = painterResource(id = R.drawable.mole),
                contentDescription = "地鼠",
                modifier = Modifier
                    .offset { IntOffset(moleX.toInt(), moleY.toInt()) }
                    .size(moleSize)
                    .clickable {
                        viewModel.incrementCounter()
                    }
            )
        } // --- 遊戲區結束 ---
    } // --- 根 Column 結束 ---
}