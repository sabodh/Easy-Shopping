package com.online.shoppinglist.presentation.ui

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.online.shoppinglist.R
import com.online.shoppinglist.data.network.model.Product
import com.online.shoppinglist.databinding.ActivityMainBinding
import com.online.shoppinglist.databinding.ToolbarLayoutBinding
import com.online.shoppinglist.presentation.viewmodel.CartViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var actionbarBinding: ToolbarLayoutBinding? = null

    private val cartViewModel by viewModels<CartViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCustomActionbar()

        val observer = Observer<MutableList<Product>>{
            Toast.makeText(this, "" + it.size, Toast.LENGTH_SHORT).show()
        }
        cartViewModel.carts.observe(this, observer)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp()
                || super.onSupportNavigateUp()
    }

    /**
     * Handling actionbar configurations
     */
    private fun setCustomActionbar() {
        actionbarBinding = ToolbarLayoutBinding.inflate(layoutInflater)
        actionbarBinding?.backMenu?.visibility = View.GONE
        actionbarBinding?.backMenu?.setOnClickListener {
            onSupportNavigateUp()
            setHeader(getString(R.string.app_name), Gravity.CENTER, View.GONE)
        }
        supportActionBar?.apply {
            displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            setBackgroundDrawable(AppCompatResources.getDrawable(baseContext, R.color.white))
            customView = actionbarBinding?.root
        }
        setHeader(getString(R.string.app_name), Gravity.CENTER, View.GONE)
    }
    fun setHeader(header: String, gravity: Int, visibility: Int) {
        actionbarBinding?.actionBarTitle?.text = header
        handleActionbar(gravity, visibility)
    }

    private fun handleActionbar(gravity: Int, visibility: Int) {
        actionbarBinding?.let {
            it.actionBarTitle.gravity = gravity
            it.backMenu.visibility = visibility
        }
    }
}