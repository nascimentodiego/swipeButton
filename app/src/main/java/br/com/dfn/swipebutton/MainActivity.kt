package br.com.dfn.swipebutton

import android.animation.Animator
import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var currentPosition = Direction.LEFT
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler().postDelayed({
            val yPixels = 5f
            val xPixels = 20f

            val dpY = (yPixels * Resources.getSystem().displayMetrics.density)
            val dpX = (xPixels * Resources.getSystem().displayMetrics.density)

            line.y = btn_one.y + btn_one.height + dpY
            line.x = btn_one.x - dpX

        }, 100)

        container.setOnTouchListener(getButtonTouchListener())
    }

    private var x1: Float = 0f
    private var x2: Float = 0f
    val MIN_DISTANCE = 100

    private fun getButtonTouchListener(): OnTouchListener {
        return OnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    x1 = event.x
                    return@OnTouchListener true
                }
                MotionEvent.ACTION_MOVE -> return@OnTouchListener true
                MotionEvent.ACTION_UP -> {
                    x2 = event.x
                    val deltaX = x2 - x1

                    if (Math.abs(deltaX) > MIN_DISTANCE) {
                        // Left to Right swipe action
                        if (x2 > x1) {
                            startAnimation(line.x + line.width, Direction.RIGHT)
                        } else {
                            startAnimation(line.x - line.width, Direction.LEFT)
                        } // Right to left swipe action
                    }
                    return@OnTouchListener true
                }
            }

            false
        }
    }

    private fun startAnimation(moveTo: Float, direction: Direction) {
        if (currentPosition == direction) return else currentPosition = direction


        val animatorListener = object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator) {
                if (direction == Direction.LEFT) {
                    Toast.makeText(baseContext, "Button one selected",
                            Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(baseContext, "Button two selected",
                            Toast.LENGTH_SHORT).show()
                }
            }
        }

        line.animate().setDuration(400).translationX(moveTo).setListener(animatorListener).start()
    }

    enum class Direction {
        RIGHT, LEFT
    }
}
