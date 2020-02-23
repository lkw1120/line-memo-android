package com.lkw1120.memo.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.lkw1120.memo.R
import com.lkw1120.memo.databinding.ActivityMainBinding
import com.lkw1120.memo.datasource.entity.MemoWithImages
import com.lkw1120.memo.ui.adapter.MemoListAdapter
import com.lkw1120.memo.utils.InjectorUtils
import com.lkw1120.memo.ui.adapter.ListDecoration
import com.lkw1120.memo.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel:MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = InjectorUtils.provideMainViewModelFactory(this)
            .create(MainViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.vm = viewModel
        binding.lifecycleOwner = this

        viewSetup()
    }

    private fun viewSetup() {
        binding.run {
            setSupportActionBar(headerMain.toolbar)

            contentMain.memoList.memoRecyclerView.run {
                val memoListAdapter = MemoListAdapter()
                addItemDecoration(ListDecoration(
                    resources.getDimensionPixelSize(R.dimen.item_memo_margin_width),
                    resources.getDimensionPixelSize(R.dimen.item_memo_margin_height)
                ))
                adapter = memoListAdapter
                subscribeUi(memoListAdapter)
            }

            writeFab.setOnClickListener {
                val intent = Intent(this@MainActivity, WriteActivity::class.java)
                intent.putExtra("create", MemoWithImages())
                startActivity(intent)
            }
        }
    }

    private fun subscribeUi(adapter: MemoListAdapter) {
        viewModel.memoList.observe(this, Observer {
            if(it.isNotEmpty()) {
                binding.contentMain.memoList.listIsEmpty.visibility = View.GONE
            }
            else {
                binding.contentMain.memoList.listIsEmpty.visibility = View.VISIBLE
            }
            adapter.submitList(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
