package com.online.shoppinglist.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.online.shoppinglist.R
import com.online.shoppinglist.data.model.Product
import com.online.shoppinglist.databinding.RowProductBinding
import com.online.shoppinglist.utils.ImageUtils

class ProductListAdapter(
    private val onProductItemClick: (Product) -> Unit,
    private val imageUtils: ImageUtils
) :
    ListAdapter<Product, ProductListAdapter.ProductViewHolder>(ComparatorDiffUtil()) {

    inner class ProductViewHolder(private val binding: RowProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(product: Product, context: Context) {
            product.apply {
                binding.apply {
                    txtProductName.text = title
                    txtPrice.text = "£"+price.toString()
                    txtCategory.text = category
                    txtRating.text = context. getString(R.string.rating_only, product.rating.rate, product.rating.count)
                    imageUtils.loadImage(imageUrl = image, imageView)
                    root.setOnClickListener {
                        onProductItemClick(product)
                    }
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = RowProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.bindItem(product, holder.itemView.context)
    }

    class ComparatorDiffUtil : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }


    }
}