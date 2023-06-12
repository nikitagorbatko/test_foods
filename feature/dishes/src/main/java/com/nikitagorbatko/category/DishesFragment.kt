package com.nikitagorbatko.category

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.nikitagorbatko.category.databinding.FragmentDishesBinding
import com.nikitagorbatko.network.DishDto
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
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
    private val chipsTitles = mutableMapOf<Int, String>()

    private val adapterDelegate = ListDelegationAdapter(
        dishAdapterDelegate {
            ProductFragment.newInstance(it as DishDto).show(childFragmentManager, ProductFragment.TAG)
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
        bind()
        observe()
        viewModel.getDishes()
    }

    private fun bind() {
        chipColorChecked = resources.getColor(R.color.chip_background_checked)
        chipColor = resources.getColor(R.color.card_background)
        chipText = resources.getColor(R.color.black)
        chipTextChecked = resources.getColor(R.color.white)
        binding.recyclerDishes.adapter = adapterDelegate

        binding.chipGroup.setOnCheckedStateChangeListener { _, checkedIds ->
            val checked = checkedIds.mapNotNull { chipsTitles.get(it) }
            viewModel.getDishes(checked)
        }
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.categories.collect { dishes ->
                adapterDelegate.items = dishes

                if (dishTags.isEmpty()) {
                    dishes.forEach { dishTags.addAll(it.tags) }
                    dishTags.forEach { binding.chipGroup.addChip(requireContext(), it) }
                }
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
        val chip = ChipUtil.getChip(
            context = context,
            chipGroup = this,
            label = label,
            chipColorChecked = chipColorChecked,
            chipColor = chipColor,
            chipTextChecked = chipTextChecked,
            chipText = chipText
        )
        chipsTitles.put(chip.id, chip.text.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}