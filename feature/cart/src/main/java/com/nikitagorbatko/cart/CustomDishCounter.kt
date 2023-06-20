package com.nikitagorbatko.cart

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.nikitagorbatko.cart.databinding.ItemCounterBinding

class CustomDishCounter
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private val binding = ItemCounterBinding.inflate(LayoutInflater.from(context))

    var counter: Int = 1
        set(value) {
            if (value >= 0) {
                field = value
                binding.textCounter.text = field.toString()
            }
        }

    init {
        addView(binding.root)
        binding.textCounter.text = counter.toString()
    }

    fun setOnMinusClickListener(onClickListener: () -> Unit) {
        binding.imageMinus.setOnClickListener {
            onClickListener.invoke()
            //binding.textCounter.text = (--counter).toString()
        }
    }

    fun setOnPlusClickListener(onClickListener: () -> Unit) {
        binding.imagePlus.setOnClickListener {
            onClickListener.invoke()
            //binding.textCounter.text = (++counter).toString()
        }
    }
}