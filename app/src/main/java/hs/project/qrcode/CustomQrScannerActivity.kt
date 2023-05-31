package hs.project.qrcode

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import hs.project.qrcode.databinding.ActivityCustomQrScannerBinding

class CustomQrScannerActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCustomQrScannerBinding
    private lateinit var captureManager : CaptureManager
    private lateinit var decoratedBarcodeView : DecoratedBarcodeView

    private var isFlash = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCustomQrScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        decoratedBarcodeView = binding.decoratedBarcodeView

        captureManager = CaptureManager(this, decoratedBarcodeView)
        captureManager.initializeFromIntent(intent, savedInstanceState)
        captureManager.setShowMissingCameraPermissionDialog(true, "카메라 권한 요청")
        captureManager.decode()

        binding.btnFlash.setOnClickListener {
            if (!isFlash) {
                isFlash = true
                decoratedBarcodeView.setTorchOn()
            } else {
                isFlash = false
                decoratedBarcodeView.setTorchOff()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        captureManager.onResume()
    }

    override fun onPause() {
        super.onPause()
        captureManager.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        captureManager.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        captureManager.onSaveInstanceState(outState)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        captureManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}