package com.cpo.base_project.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.ViewTreeObserver
import android.view.animation.AnimationUtils
import androidx.activity.ComponentActivity
import com.cpo.base_project.R
import com.cpo.base_project.databinding.ActivityCalculateBinding

class CalculateActivity: ComponentActivity() {

    private val binding by lazy {ActivityCalculateBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding){
            animateCategories()

            editTextListener()

            toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

            button.setOnClickListener {
                startActivity(Intent(this@CalculateActivity,
                    CompletedActivity::class.java))
            }
        }

    }

    private fun editTextListener(){
        binding.etWeight.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    val length = it.length
                    for (i in 0 until length) {
                        if (it[i] == ' ') {
                            it.replace(i, i + 1, "-")
                        }
                    }
                }
            }
        })
    }

    private fun animateCategories(){
        binding.categories.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val totalWidth = binding.categories.width
                if (binding.categories.right >= totalWidth * 0.7) {
                    val elasticSlideIn = AnimationUtils.loadAnimation(
                        this@CalculateActivity, R.anim.slide_in_from_right)
                    binding.categories.startAnimation(elasticSlideIn)
                    binding.categories.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            }
        })
    }

}