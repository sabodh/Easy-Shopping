package com.online.shoppinglist.presentation.ui

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.online.shoppinglist.R
import com.online.shoppinglist.data.model.Product
import com.online.shoppinglist.databinding.FragmentProductDetailsBinding
import com.online.shoppinglist.presentation.viewmodel.CartViewModel
import com.online.shoppinglist.presentation.viewmodel.ProductViewModel
import com.online.shoppinglist.utils.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

/**
 * Used to display the selected product details
 */
@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {

    private var _binding: FragmentProductDetailsBinding? = null

    val binding get() = _binding!!

    private val args: ProductDetailsFragmentArgs by navArgs()

    private val productViewModel by viewModels<ProductViewModel>()

    private val cartViewModel by activityViewModels<CartViewModel>()
    @Inject
    lateinit var imageUtils: ImageUtils

    @Inject
    lateinit var snackbarUtil: SnackbarUtil

    @Inject
    lateinit var networkUtils: NetworkUtils

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkNetworkConnectivity()
        observeProduct()
        // handle date received through arguments
        binding.apply {
            args.selectedProduct.apply {
                setProductDetails(binding, this)
                productViewModel.getProductDetails(this.id.toString())
            }
        }
    }

    fun checkNetworkConnectivity() {
        if (!networkUtils.isNetworkConnected()) {
            showSnackbar(getString(R.string.network_error))
        }
    }

    /**
     * Setup selected product details initially
     */
    fun setProductDetails(
        fragmentProductDetailsBinding: FragmentProductDetailsBinding,
        product: Product
    ) {
        fragmentProductDetailsBinding.apply {
            txtAmount.text = "£" + product.price.toString()
            txtCategory.text = product.category.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.ROOT
                ) else it.toString()
            }
            txtSummary.text = product.description
            txtInfoHeader.text = product.title
            txtRating.text =  getString(R.string.rating_is, product.rating.rate, product.rating.count)
//            product.rating.rate.toString()+ "/ " +product.rating.count.toString()
            imageUtils.loadImage(product.image, imageView)
            (activity as? MainActivity)?.setHeader(
                product.title!!,
                Gravity.START,
                View.VISIBLE
            )
            btnAddCart?.setOnClickListener {
                cartViewModel.addItemsToCart(product)
            }
        }

    }

    /**
     * Setup observer for product details received from server
     */
    private fun observeProduct() {
        val observer = Observer<ServiceResponse<Product>> {
            when (it.status) {
                Status.SUCCESS -> {
                    setProductDetails(binding, it.data!!)
                    showProgress(View.GONE)
                }
                Status.ERROR -> {
                    showProgress(View.GONE)
                    showSnackbar(it.message.toString())
                }
                Status.LOADING -> {
                    showProgress(View.VISIBLE)
                }
            }
        }
        productViewModel.product.observe(viewLifecycleOwner, observer)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showProgress(status: Int) {
        binding.progressBar.visibility = status
    }

    fun showSnackbar(message: String) {
        snackbarUtil.showSnackbar(binding.imageView, message)
    }
}