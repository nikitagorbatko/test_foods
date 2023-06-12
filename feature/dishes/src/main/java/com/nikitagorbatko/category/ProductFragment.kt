package com.nikitagorbatko.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.nikitagorbatko.category.databinding.FragmentProductBinding
import com.nikitagorbatko.network.DishDto
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProductFragment private constructor() : DialogFragment() {
    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProductViewModel by viewModel()

    companion object {
        const val TAG = "ProductDialog"
        private const val KEY_DISH = "DISH"
        fun newInstance(dishDto: DishDto): ProductFragment {
            val args = Bundle()
            args.putParcelable(KEY_DISH, dishDto)
            val fragment = ProductFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.MyDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dish = arguments?.getParcelable<DishDto>(KEY_DISH)
        val weigh = " · ${dish?.weight}г"
        val price = "${dish?.price}₽"
        with(binding) {
            textPrice.text = price
            textGrams.text = weigh
            textDescription.text = dish?.description
            textTitle.text = dish?.name
            imageClose.setOnClickListener {
                dialog?.cancel()
            }
            buttonAdd.setOnClickListener {
                if (dish != null) {
                    viewModel.addDish(dish)
                }
                dismiss()
            }
            Glide
                .with(root)
                .load(dish?.imageUrl ?: dish?.description)
                .into(imageView)
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }
}