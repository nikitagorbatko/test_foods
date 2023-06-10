package com.nikitagorbatko.category

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.ChipGroup
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.nikitagorbatko.category.databinding.FragmentDishesBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class DishesFragment : Fragment() {
    private var chipColorChecked = -1
    private var chipColor = -1
    private var chipTextChecked = -1
    private var chipText = -1

    private var _binding: FragmentDishesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DishesViewModel by viewModel()

    private val dishTags = mutableSetOf<String>()

    private val adapterDelegate = ListDelegationAdapter(
        dishAdapterDelegate {
//            val request = NavDeepLinkRequest.Builder
//                .fromUri("android-app://com.nikitagorbatko.testfoods/navigation_product".toUri())
//                .build()
//            findNavController().navigate(request)
            SimpleFragment.newInstance("Haribol", "sadf").show(childFragmentManager, "ase32j28")
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDishesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chipColorChecked = resources.getColor(R.color.chip_background_checked)
        chipColor = resources.getColor(R.color.card_background)
        chipText = resources.getColor(R.color.black)
        chipTextChecked = resources.getColor(R.color.white)

        binding.recyclerDishes.adapter = adapterDelegate
        observe()
        viewModel.getDishes()
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.categories.collect { dishes ->
                adapterDelegate.items = dishes
                dishes.forEach {
                    dishTags.addAll(it.tegs)
                }
                dishTags.forEach {
                    binding.chipGroup.addChip(requireContext(), it)
                }
                adapterDelegate.notifyDataSetChanged()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectLatest {
                when (it) {
                    DishesViewModel.State.LOADING -> {
                        binding.progressDishes.visibility = View.VISIBLE
                        binding.recyclerDishes.visibility = View.GONE
                        binding.textDishesError.visibility = View.GONE
                    }
                    DishesViewModel.State.PRESENT -> {
                        binding.progressDishes.visibility = View.GONE
                        binding.recyclerDishes.visibility = View.VISIBLE
                        binding.textDishesError.visibility = View.GONE
                    }
                    DishesViewModel.State.ERROR -> {
                        binding.progressDishes.visibility = View.VISIBLE
                        binding.recyclerDishes.visibility = View.GONE
                        binding.textDishesError.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun ChipGroup.addChip(context: Context, label: String) {
        ChipUtil.getChip(
            context = context,
            chipGroup = this,
            label = label,
            chipColorChecked = chipColorChecked,
            chipColor = chipColor,
            chipTextChecked = chipTextChecked,
            chipText = chipText
        )
//        Chip(context).apply {
//            id = View.generateViewId()
//            text = label
//            isClickable = true
//            isCheckable = true
//            setChipSpacingHorizontalResource(R.dimen.dimen_16dp)
//            chipBackgroundColor = ColorStateList(
//                arrayOf(intArrayOf(android.R.attr.state_checked), intArrayOf()), intArrayOf(
//                    checkedChipColor,
//                    chipColor
//                )
//            )
//            setOnClickListener {
//                setTextColor(resources.)
//            }
//            setTextColor(resources.getColor(R.color.chip_text_color))
//            //setp
//            isCheckedIconVisible = false
//            isFocusable = true
//            addView(this)
//        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}