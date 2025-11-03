package tw.edu.pu.csim.s1130045.mole

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * 這個 ViewModel 負責儲存和管理 MoleScreen 的狀態。
 */
class MoleViewModel : ViewModel() {

    // _counter 是私有的、可變的狀態
    // 我們使用 StateFlow 來讓 Compose UI 可以觀察(observe)它
    private val _counter = MutableStateFlow(0L) // L 代表是 Long 類型

    // counter 是公開的、不可變的 StateFlow
    // UI 只能讀取這個值，不能修改它
    val counter: StateFlow<Long> = _counter.asStateFlow()

    /**
     * 當 UI 呼叫這個函式時，我們會安全地更新計數器
     */
    fun incrementCounter() {
        // .update 是一個安全的方式來更新 StateFlow 的值
        _counter.update { currentValue ->
            currentValue + 1
        }
    }
}