package hs.project.qrcode

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import hs.project.qrcode.databinding.ActivityScanqrBinding
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.timerTask

class ScanQRActivity : AppCompatActivity() {

    private lateinit var binding : ActivityScanqrBinding

    private val barcodeLauncher = registerForActivityResult(ScanContract()) { result : ScanIntentResult ->
        if (result.contents == null){
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Scanned : " + result.contents, Toast.LENGTH_SHORT).show()

            val jsonData = result.contents

            try {

                val jsonObject = JSONObject(jsonData)

                val meetId = jsonObject.getInt("meetId")
                val step = jsonObject.getInt("step")

                if (meetId == 1 && step == -1){
                    val currentDateTime = getCurrentDateTime()
                    binding.txtResult.text = currentDateTime
                }



            } catch (e: JSONException) {
                e.printStackTrace()
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScanqrBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnScan.setOnClickListener {
            onScanButtonClicked()
        }

        binding.btnCustomScan.setOnClickListener {
            onCustomScanButtonClicked()
        }

    }

    private fun onScanButtonClicked(){
        // 세로모드
        var options = ScanOptions()
        options.setOrientationLocked(false)
        barcodeLauncher.launch(ScanOptions())
    }

    private fun onCustomScanButtonClicked(){
        val options = ScanOptions()
        options.setOrientationLocked(false)
        options.setBeepEnabled(true)
        options.setPrompt("커스텀 QR 스캐너")
        options.captureActivity = CustomQrScannerActivity::class.java

        barcodeLauncher.launch(options)
    }

    private fun convertIntToTime(time: Int): String {
        var min = time / 60
        val hour = min / 60
        val sec = time % 60
        min %= 60
        return String.format("%02d:%02d:%02d", hour, min, sec)
    }

    fun getCurrentDateTime(): String {
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return dateFormat.format(currentDate)
    }

}