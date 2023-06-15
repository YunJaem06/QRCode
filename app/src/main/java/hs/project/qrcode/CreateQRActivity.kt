package hs.project.qrcode

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import hs.project.qrcode.databinding.ActivityCreateqrBinding

class CreateQRActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCreateqrBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateqrBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            val barcodeEncode = BarcodeEncoder()
            val bitmap = barcodeEncode.encodeBitmap("\"meetId\":1,\"step\":-1", BarcodeFormat.QR_CODE, 400, 400)
            val imageViewQrCode : ImageView = binding.imageViewQrCode
            imageViewQrCode.setImageBitmap(bitmap)
        } catch (e : Exception) {

        }
    }
}