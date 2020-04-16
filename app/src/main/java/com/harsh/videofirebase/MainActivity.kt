package com.harsh.videofirebase

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
import com.harsh.videofirebase.utils.STORAGE_PERMISSION
import com.harsh.videofirebase.utils.VIDEO_PERMISSIONS
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val titles = arrayOf("Home", "Videos")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        supportActionBar?.elevation = 0f
        viewPager.adapter = ViewPagerFragmentAdapter(this)
        TabLayoutMediator(tab_layout, viewPager,
            TabConfigurationStrategy { tab: TabLayout.Tab, position: Int ->
                tab.text = titles[position]
            }
        ).attach()
    }

    private inner class ViewPagerFragmentAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {

        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> return RecordVideoFragment.newInstance()
                1 -> return MoviesFragment()
            }
            return RecordVideoFragment.newInstance()
        }

        override fun getItemCount(): Int {
            return titles.size
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            STORAGE_PERMISSION -> if (grantResults.isNotEmpty() && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                val alert =
                    AlertDialog.Builder(this)
                alert.setMessage("You need to allow permission")
                alert.setPositiveButton(
                    android.R.string.ok
                ) { dialogInterface, i ->
                    dialogInterface.dismiss()
                    val intent =
                        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    intent.data = Uri.parse("package:$packageName")
                    startActivityForResult(intent, requestCode)
                }
                alert.setCancelable(false)
                alert.show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        checkPermissionWithCode()
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            STORAGE_PERMISSION -> {
                checkPermissionWithCode()
            }
        }
    }

    private fun checkPermissionWithCode() {
        if ((ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED)
            &&
            (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED)
            && (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED)
        ) {
            //Do nothing
        } else {
            ActivityCompat.requestPermissions(
                this,
                VIDEO_PERMISSIONS,
                STORAGE_PERMISSION
            )
        }
    }
}