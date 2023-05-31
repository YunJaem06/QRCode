package hs.project.qrcode

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import hs.project.qrcode.databinding.ActivityScanqrBinding

class ScanQRActivity : AppCompatActivity() {

    private lateinit var binding : ActivityScanqrBinding

    private val barcodeLauncher = registerForActivityResult(ScanContract()) { result : ScanIntentResult ->
        if (result.contents == null){
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Scanned : " + result.contents, Toast.LENGTH_SHORT).show()

            binding.txtResult.text = result.contents.toString()
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

    fun onScanButtonClicked(){
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

}