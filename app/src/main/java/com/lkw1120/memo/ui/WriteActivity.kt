package com.lkw1120.memo.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.lkw1120.memo.R
import com.lkw1120.memo.databinding.ActivityWriteBinding
import com.lkw1120.memo.datasource.entity.Image
import com.lkw1120.memo.datasource.entity.MemoWithImages
import com.lkw1120.memo.ui.adapter.ImageListAdapter
import com.lkw1120.memo.utils.InjectorUtils
import com.lkw1120.memo.viewmodel.WriteViewModel

class WriteActivity : AppCompatActivity() {

    private lateinit var viewModel: WriteViewModel
    private lateinit var binding:ActivityWriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val memoWithImages =
            if(intent.hasExtra("modify"))
                intent.getSerializableExtra("modify") as MemoWithImages
            else
                intent.getSerializableExtra("create") as MemoWithImages
        viewModel = InjectorUtils.provideWriteViewModelFactory(this, memoWithImages)
            .create(WriteViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_write)
        binding.vm = viewModel
        binding.lifecycleOwner = this

        viewSetup()
    }

    private fun viewSetup() {
        binding.run {
            setSupportActionBar(headerWrite.toolbar)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title =
                if(intent.hasExtra("modify")) {
                    resources.getString(R.string.modify_title)
                }
                else {
                    resources.getString(R.string.create_title)
                }

            contentWrite.imageList.imageViewPager.run {
                val imageListAdapter = ImageListAdapter(this@WriteActivity)
                adapter = imageListAdapter
                offscreenPageLimit = 1
                subscribeUi(imageListAdapter)
                imageListAdapter.setOnIconClickListener(object: ImageListAdapter.OnIconClickListener{
                    override fun onIconClick(view: View, pos: Int) {
                        val image = imageListAdapter.currentList[pos]
                        viewModel.removeImage(image)
                    }
                })
            }

            submitFab.setOnClickListener {
                viewModel.updateMemoWithImages()
                finish()
            }
        }
    }

    private fun subscribeUi(adapter: ImageListAdapter) {
        viewModel.imageList.observe(this, Observer {
            if(it.isNotEmpty()) {
                binding.contentWrite.imageList.listIsEmpty.visibility = View.GONE
            }
            else {
                binding.contentWrite.imageList.listIsEmpty.visibility = View.VISIBLE
            }
            // 주소값이 다른 "새로운" 리스트를 넣어야 변경이 된다
            adapter.submitList(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_write, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home-> {
                finish()
                true
            }
            R.id.action_capture_camera -> {
                permissionCheck()
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, 0)
                true
            }
            R.id.action_pick_album -> {
                permissionCheck()
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = MediaStore.Images.Media.CONTENT_TYPE
                startActivityForResult(intent, 0)
                true
            }
            R.id.action_link_web -> {
                webLinkDialog()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(data != null) {
            val imageSrc: String
            val cursor: Cursor = contentResolver.query(data.data!!, null, null, null, null ,null)!!
            cursor.moveToNext()
            imageSrc = cursor.getString(cursor.getColumnIndex( "_data" ))
            cursor.close()
            val image = Image()
            image.src = imageSrc
            viewModel.addImage(image)
        }
        else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun webLinkDialog() {
        val alert = AlertDialog.Builder(this)
        val editText = EditText(this)
        alert.apply {
            setTitle("웹이미지 넣기")
            setMessage("링크를 삽입해주세요")
            setView(editText)
            setPositiveButton("확인") { _, _ ->
                val imageSrc = editText.text.toString()
                val image = Image()
                image.src = imageSrc
                viewModel.addImage(image)
            }
        }.show()
    }

    private fun permissionCheck() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            //listOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            val permissionResult = ContextCompat.checkSelfPermission(this, permission[0])
            if (permissionResult == PackageManager.PERMISSION_DENIED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission[0])) {

                } else {
                    ActivityCompat.requestPermissions(this, permission,100)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            100 -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    /*
                    권한을 허용한 경우의 액션 부분
                     */

                    Log.d("PERMISSION_CHECK","허용함")
                }
                else {
                    Toast.makeText(this, resources.getString(R.string.storage_permission_alert), Toast.LENGTH_LONG).show()
                }
            }
            else -> {
                // Ignore all other requests.
            }
        }
    }
}
