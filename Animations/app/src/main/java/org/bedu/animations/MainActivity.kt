package org.bedu.animations

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.CycleInterpolator
import android.view.animation.LinearInterpolator
import org.bedu.animations.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {
            btnBarrel.setOnClickListener { barrelRoll() }
            btnSin.setOnClickListener { arwingDodging()}
            btnAlpha.setOnClickListener { blinkArwing() }
            btnTiny.setOnClickListener {shrinkArwing() }
            btnStart.setOnClickListener { moveArwingToStart() }
            btnPivot.setOnClickListener { changePivot() }
        }
    }

    fun barrelRoll() {
        //-720 gira en sentido contrario
        val valueAnimator = ValueAnimator.ofFloat(0f, 720f).run {
            addUpdateListener {
                val value = it.animatedValue as Float
                binding.arwing.rotationY = value
            }
            interpolator = AccelerateDecelerateInterpolator()
            //interpolator = LinearInterpolator()
            duration = 1000
            start()
        }
    }

    fun arwingDodging() {
      /*  ObjectAnimator.ofFloat(binding.arwing, "translationX", 200f).apply {
            duration = 3000
            interpolator = CycleInterpolator(1f)
            start()
        }*/

        AnimatorInflater.loadAnimator(this, R.animator.dodging).apply {
            setTarget(binding.arwing)
            start()
        }

    }

    fun blinkArwing() {
        AnimatorInflater.loadAnimator(this, R.animator.blink).apply {
            setTarget(binding.arwing)
            addListener(object: AnimatorListener{
                override fun onAnimationStart(animation: Animator) {
                    Log.d("Blink", "Iniciando")
                }

                override fun onAnimationEnd(animation: Animator) {
                    Log.d("Blink", "Finalizando")
                }

                override fun onAnimationCancel(animation: Animator) {
                    Log.d("Blink", "Cancelando")
                }

                override fun onAnimationRepeat(animation: Animator) {
                    Log.d("Blink", "Repitiendo")
                }
            })
            start()
        }

    }

    fun shrinkArwing() {
        AnimatorInflater.loadAnimator(this, R.animator.shrink).apply {
            setTarget(binding.arwing)
            start()
        }
    }

    fun moveArwingToStart() {
        binding.arwing.animate().apply {
            translationX(0f)
            translationY(0f)
            duration = 1000
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
    }

    fun changePivot() {
        val initPivotX = PropertyValuesHolder.ofFloat("pivotX", 0f)
        val initPivotY = PropertyValuesHolder.ofFloat("pivotY", 0f)
        val transparent = PropertyValuesHolder.ofFloat("alpha", 0.6f)
        val animation1 =
            ObjectAnimator.ofPropertyValuesHolder(binding.arwing, initPivotX, initPivotY, transparent)
        animation1.duration = 500

        val pivotCenterX = binding.arwing.width.toFloat() / 2f
        val pivotCenterY = binding.arwing.height.toFloat() / 2f
        val centerPivotX = PropertyValuesHolder.ofFloat("pivotX", pivotCenterX)
        val centerPivotY = PropertyValuesHolder.ofFloat("pivotY", pivotCenterY)
        val opacy = PropertyValuesHolder.ofFloat("alpha", 1f)

        val animation2 =
            ObjectAnimator.ofPropertyValuesHolder(binding.arwing, centerPivotX, centerPivotY, opacy)
                .apply {
                    duration = 500
                    startDelay = 4000
                }

        AnimatorSet().apply {
            playSequentially(animation1, animation2)
            start()
        }
    }
}
