package com.lkw1120.memo.ui

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.lkw1120.memo.R
import com.lkw1120.memo.databinding.ActivityWriteBinding
import com.lkw1120.memo.datasource.entity.Image
import com.lkw1120.memo.datasource.entity.MemoWithImages
import com.lkw1120.memo.ui.adapter.ImageListAdapter
import com.lkw1120.memo.utils.ImageUtils
import com.lkw1120.memo.utils.InjectorUtils
import com.lkw1120.memo.utils.PermissionUtils
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
                PermissionUtils.permissionCheck(applicationContext,this)
                actionCaptureCamera()
                true
            }
            R.id.action_pick_album -> {
                PermissionUtils.permissionCheck(applicationContext,this)
                actionPickAlbum()
                true
            }
            R.id.action_link_web -> {
                actionLinkWeb()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        PermissionUtils.permissionCheckResult(requestCode, permissions, grantResults)
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(data != null) {
            val imageSrc: String
            val cursor: Cursor =
                contentResolver.query(data.data!!, null, null, null, null ,null)!!
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


    private fun actionCaptureCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, 0)
    }
    
    private fun actionPickAlbum() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intent, 0)
    }
    
    private fun actionLinkWeb() {
        val editText = EditText(this@WriteActivity)
        val dialog = AlertDialog.Builder(this@WriteActivity)
            .setTitle(resources.getString(R.string.dialog_title_add_web_image))
            .setMessage(resources.getString(R.string.dialog_message_add_web_image))
            .setView(editText)
            .setPositiveButton(android.R.string.ok, null)
            .setNegativeButton(android.R.string.cancel, null)
            .create()
        dialog.setOnShowListener {
            val button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            button.setOnClickListener {
                val imageSrc = editText.text.toString()
                val image = Image()
                image.src = imageSrc
                viewModel.addImage(image)
                dialog.dismiss()
            }
        }
        dialog.show()
    }
}
