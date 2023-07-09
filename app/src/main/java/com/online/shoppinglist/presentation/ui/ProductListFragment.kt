package com.online.shoppinglist.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.online.shoppinglist.R
import com.online.shoppinglist.data.network.model.Product
import com.online.shoppinglist.databinding.FragmentProductListBinding
import com.online.shoppinglist.presentation.adapter.ProductListAdapter
import com.online.shoppinglist.presentation.viewmodel.ProductViewModel
import com.online.shoppinglist.utils.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Used to show the list of the products
 */
@AndroidEntryPoint
class ProductListFragment : Fragment() {

    private var _binding: FragmentProductListBinding? = null

    val binding get() = _binding!!

    private val productViewModel by viewModels<ProductViewModel>()

    private lateinit var productAdapter: ProductListAdapter
    @Inject
    lateinit var imageUtils: ImageUtils

    @Inject
    lateinit var snackbarUtil: SnackbarUtil

    @Inject
    lateinit var networkUtils: NetworkUtils

    @Inject
    lateinit var screenUtils: ScreenUtils

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkNetworkConnectivity()
        initRecyclerview(view)
        swipeRefreshing()
        observeProducts()
    }

    private fun checkNetworkConnectivity() {
        if (!networkUtils.isNetworkConnected()) {
            showSnackbar(getString(R.string.network_error))
        }
    }

    private fun swipeRefreshing(){
        binding.swipeRefresh.setOnRefreshListener {
            if(!networkUtils.isNetworkConnected()){
                showSnackbar(getString(R.string.network_error))
                return@setOnRefreshListener
            }
            showPullToRefresh(true)
            productViewModel.getProductList()
        }
    }
    /**
     * Recyclerview rendering
     */
    private fun initRecyclerview(view: View) {
        val spanCount = screenUtils.getGridCount()
        binding.recyclerView.layoutManager =
            GridLayoutManager(view.context, spanCount)
        productAdapter = ProductListAdapter(::onProductItemClick, imageUtils)
        binding.recyclerView.adapter = productAdapter
    }

    /**
     * Observe the values changes and update the view based on it
     */
    private fun observeProducts() {
        val observer = Observer<ServiceResponse<List<Product>>> {
            when (it.status) {
                Status.SUCCESS -> {
                    productAdapter.submitList(it.data)
                    showProgress(View.GONE)
                    showError(View.GONE)
                    showRecyclerview(View.VISIBLE)
                    showPullToRefresh(false)
                }
                Status.ERROR -> {
                    showProgress(View.GONE)
                    showRecyclerview(View.GONE)
                    showError(View.VISIBLE)
                    showPullToRefresh(false)
                    showSnackbar(it.message.toString())
                }
                Status.LOADING -> {
                    showProgress(View.VISIBLE)
                    showRecyclerview(View.VISIBLE)
                    showError(View.GONE)
                    showPullToRefresh(false)
                }
            }
        }
        productViewModel.products.observe(viewLifecycleOwner, observer)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showProgress(status: Int){
        binding.progressBar.visibility = status
    }
    private fun showRecyclerview(status: Int){
        binding.recyclerView.visibility = status
    }
    private fun showError(status: Int){
        binding.textviewEmpty.visibility = status
    }

    /**
     * Navigate to details screen based on the recyclerview adapter click
     */
    private fun onProductItemClick(product: Product){
        val action = ProductListFragmentDirections.actionProductListToDetails(product)
        Navigation.findNavController(_binding!!.root).navigate(action)
    }
    fun showSnackbar(message: String) {
        snackbarUtil.showSnackbar(binding.recyclerView, message)
    }
    private fun showPullToRefresh(visibility: Boolean) {
        binding.swipeRefresh.isRefreshing = visibility
    }
}