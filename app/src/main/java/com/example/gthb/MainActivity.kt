package com.example.gthb

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.gthb.databinding.ActivityMainBinding
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.net.URI

class MainActivity : AppCompatActivity() {
    private val root: Any = TODO()
    var urime: Uri? = null
    private var binding: ActivityMainBinding
    private var mybase: Mybase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mybase = Mybase(this)
        binding.btnTakeImage.setOnClickListener {
            val list = ArrayList<ImageUser>()
            list.addAll(mybase.getAllImage())
            if (list.isNotEmpty()) {
                binding.imageview.setImageURI(Uri.parse(list[0].absolutePath))

                val bitmap = BitmapFactory.decodeByteArray(list[0].image, 0, list[0].image!!.size)
                binding.imageview.setImageBitmap(bitmap)
            }
        }
        binding.btnGetImage.setOnClickListener {
            getImageNew()

        }
    }

    private val getImageContent = registerForActivityResult(ActivityResultContracts.GetContent()){
        it ?: return@registerForActivityResult
        binding.imageview2.setImageURI(it)
        val inputStream = contentResolver?.openInputStream(it)
        val file = File(filesDir, "image.jpg")
        val fileOutputStream = FileOutputStream(file)
        inputStream?.copyTo(fileOutputStream)
        inputStream?.close()
        fileOutputStream?.close()
        val absoulut = file.absolutePath

        val fileInputStream = FileInputStream(file)
        val readBytes = fileInputStream.readBytes()
        val imageUser = ImageUser(absoulut, readBytes)

        mybase.insertImage(imageUser)

        mybase.getAllImage()
    }




    private fun getImageNew() {
        getImageContent.launch("image/*")
    }


}
