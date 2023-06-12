package com.nikitagorbatko.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.nikitagorbatko.cart.databinding.FragmentCartBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartFragment : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CartViewModel by viewModel()

    private val adapterDelegate = ListDelegationAdapter(
        cartDishAdapterDelegate(
            onPlusClick = {
                viewModel.addDish(it)
            },
            onMinusClick = {
                viewModel.minusDish(it)
            }
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerCartDishes.adapter = adapterDelegate
        observe()
        viewModel.getDishes()
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.dishes.collectLatest { dishes ->
                    adapterDelegate.items = dishes
                    adapterDelegate.notifyDataSetChanged()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.totalPrice.collectLatest {
                    binding.button.text = "Оплатить $it ₽"
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collectLatest {
                    when (it) {
                        CartViewModel.State.LOADING -> {
                            binding.progressDishes.visibility = View.VISIBLE
                            binding.recyclerCartDishes.visibility = View.GONE
                            binding.textDishesError.visibility = View.GONE
                            binding.button.visibility = View.GONE
                        }
                        CartViewModel.State.PRESENT -> {
                            binding.progressDishes.visibility = View.GONE
                            binding.recyclerCartDishes.visibility = View.VISIBLE
                            binding.textDishesError.visibility = View.GONE
                            binding.button.visibility = View.VISIBLE
                        }
                        CartViewModel.State.NO_DISHES -> {
                            binding.progressDishes.visibility = View.GONE
                            binding.recyclerCartDishes.visibility = View.GONE
                            binding.textDishesError.visibility = View.VISIBLE
                            binding.button.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}