package tw.edu.pu.csim.s1130045.mole

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MoleViewModel : ViewModel() {
    // 儲存分數
    var counter by mutableLongStateOf(0)
        private set

    // 儲存遊戲時間（秒）
    var stay by mutableLongStateOf(0)
        private set

    // 地鼠的 X, Y 座標 (像素)
    var moleX by mutableStateOf(0f)
        private set
    var moleY by mutableStateOf(0f)
        private set

    // *** 修正 1: 新增遊戲狀態 ***
    // 遊戲是否正在進行中
    var isGameActive by mutableStateOf(true)
        private set // 只有 ViewModel 內部可以修改

    // 儲存螢幕和地鼠的尺寸
    private var screenWidth: Float = 0f
    private var screenHeight: Float = 0f
    private var moleSize: Float = 0f

    /**
     * 讓 UI (MoleScreen) 可以傳入螢幕尺寸
     */
    fun updateScreenSize(width: Float, height: Float, size: Float) {
        screenWidth = width
        screenHeight = height
        moleSize = size
        setRandomMolePosition()
    }

    /**
     * 隨機設定地鼠位置 (使用儲存好的螢幕尺寸)
     */
    private fun setRandomMolePosition() {
        if (screenWidth == 0f || screenHeight == 0f) return
        val maxX = screenWidth - moleSize
        val maxY = screenHeight - moleSize
        if (maxX > 0 && maxY > 0) {
            moleX = Random.nextFloat() * maxX
            moleY = Random.nextFloat() * maxY
        } else {
            moleX = 0f
            moleY = 0f
        }
    }

    /**
     * *** 修正 2: 點擊地鼠時，檢查遊戲是否還在進行 ***
     */
    fun incrementCounter() {
        if (isGameActive) {
            counter++
        }
    }

    init {
        // 啟動計時器和移動邏輯
        startCounting()
    }

    /**
     * *** 修正 3: 修改計時迴圈，在 60 秒時停止 ***
     */
    private fun startCounting() {
        viewModelScope.launch {
            // 迴圈只跑到 60 秒
            while (stay < 60L) {
                delay(1000L) // 延遲 1 秒
                stay++

                // 每 2 秒呼叫一次隨機位置
                if (stay % 2L == 0L) {
                    setRandomMolePosition()
                }
            }
            // 迴圈結束 (stay == 60)，將遊戲狀態設為 false
            isGameActive = false
        }
    }
}