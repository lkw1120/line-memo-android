package com.lkw1120.memo.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.lkw1120.memo.R

object PermissionUtils {
    private const val READ_EXTERNAL_STORAGE = 100

    fun permissionCheck(context: Context, activity: Activity) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            //listOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            val permissionResult = ContextCompat.checkSelfPermission(context, permission[0])
            if (permissionResult == PackageManager.PERMISSION_DENIED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission[0])) {
                    Toast.makeText(activity, activity.resources.getString(R.string.storage_permission_alert), Toast.LENGTH_LONG).show()
                }
                else {
                    ActivityCompat.requestPermissions(activity, permission,READ_EXTERNAL_STORAGE)
                }
            }
        }
    }

    fun permissionCheckResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            READ_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    //권한을 허용한 경우
                    Log.d("PERMISSION_CHECK","허용함")
                }
                else {

                }
            }
            else -> {
                // Ignore all other requests.
            }
        }
    }

}