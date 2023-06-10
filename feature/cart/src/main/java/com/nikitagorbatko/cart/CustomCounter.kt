package com.nikitagorbatko.cart

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.nikitagorbatko.cart.databinding.ItemCounterBinding

class CustomCounter
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
            }
        }

    init {
        addView(binding.root)
        binding.textCounter.text = counter.toString()
    }

    fun setOnMinusClickListener(onClickListener: (result: Int) -> Unit) {
        binding.imageMinus.setOnClickListener {
            onClickListener.invoke(--counter)
        }
    }

    fun setOnPlusClickListener(onClickListener: (result: Int) -> Unit) {
        binding.imageMinus.setOnClickListener {
            onClickListener.invoke(++counter)
        }
    }
}