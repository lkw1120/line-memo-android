package com.lkw1120.memo.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.lkw1120.memo.R
import com.lkw1120.memo.databinding.ActivityDetailBinding
import com.lkw1120.memo.datasource.entity.MemoWithImages
import com.lkw1120.memo.ui.adapter.ImageListAdapter
import com.lkw1120.memo.utils.InjectorUtils
import com.lkw1120.memo.viewmodel.DetailViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = intent.getLongExtra("id",1)
        viewModel = InjectorUtils.provideDetailViewModelFactory(this,id)
            .create(DetailViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.vm = viewModel
        binding.lifecycleOwner = this

        viewSetup()
    }

    private fun viewSetup() {
        binding.run {
            setSupportActionBar(headerDetail.toolbar)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)

            contentDetail.imageList.imageViewPager.run {
                val imageListAdapter = ImageListAdapter(this@DetailActivity)
                adapter = imageListAdapter
                offscreenPageLimit = 1
                subscribeUi(imageListAdapter)
            }
        }
    }

    private fun subscribeUi(adapter: ImageListAdapter) {
        viewModel.imageList.observe(this, Observer {
            if(it.isNotEmpty()) {
                binding.contentDetail.imageList.listIsEmpty.visibility = View.GONE
            }
            else {
                binding.contentDetail.imageList.listIsEmpty.visibility = View.VISIBLE
            }
            adapter.submitList(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home-> {
                finish()
                true
            }
            R.id.action_modify -> {
                actionModify()
                true
            }
            R.id.action_delete -> {
                actionDelete()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun actionModify() {
        val intent = Intent(this@DetailActivity,WriteActivity::class.java)
        val memo = viewModel.memo.value!!
        val imageList = viewModel.imageList.value!!
        intent.putExtra("modify", MemoWithImages(memo, imageList))
        startActivity(intent)
    }

    private fun actionDelete() {
        finish()
        viewModel.deleteMemo()
    }
}
