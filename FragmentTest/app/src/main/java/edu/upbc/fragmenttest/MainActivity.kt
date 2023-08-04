package edu.upbc.fragmenttest

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import edu.upbc.fragmenttest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        const val PERMISSION_REQUEST_STORAGE = 0
        const val urlApp = "https://github.com/gmlimo/testBedu/raw/apk/fragment_test-release.apk"
        //const val urlCode = "https://github.com/andres2093/apk-s/raw/sesion6/versionCode.txt"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var downloadController: DownloadController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment1 = Fragment1()
        downloadController = DownloadController(this, urlApp)

        with (binding) {
            fragment1Btn.setOnClickListener {
                changeFragment(R.id.activity_main, fragment1)
                fragment1Btn.isVisible = false
                fragment2Btn.isVisible = false
                showMessage(this@MainActivity, "Fragment 1 is ON")
            }

            fragment2Btn.setOnClickListener {
                checkStoragePermission()
            }
        }
    }

    override fun onBackPressed() {
        val fragmentBlank = BlankFragment()
        changeFragment(R.id.activity_main, fragmentBlank)
        binding.fragment1Btn.isVisible = true
        binding.fragment2Btn.isVisible = true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_STORAGE) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                downloadController.enqueueDownload()
            } else {
                Toast.makeText(this, R.string.storage_permission_denied, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun checkStoragePermission() {
        if (checkSelfPermissionCompat(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        ) {
            downloadController.enqueueDownload()
        } else {
            requestStoragePermission()
        }
    }

    private fun requestStoragePermission() {
        if (shouldShowRequestPermissionRationaleCompat(WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(this, R.string.storage_permission_denied, Toast.LENGTH_LONG).show()
            requestPermissionsCompat(
                arrayOf(WRITE_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_STORAGE
            )
        } else {
            requestPermissionsCompat(
                arrayOf(WRITE_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_STORAGE
            )
        }
    }
}