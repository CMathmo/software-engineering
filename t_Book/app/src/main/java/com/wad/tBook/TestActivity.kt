package com.wad.tBook

import android.app.Activity
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.getbase.floatingactionbutton.FloatingActionButton
import com.getbase.floatingactionbutton.FloatingActionsMenu

class TestActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        findViewById<View>(R.id.pink_icon).setOnClickListener {
            Toast.makeText(
                this,
                "Clicked pink Floating Action Button",
                Toast.LENGTH_SHORT
            ).show()
        }
        val button =
            findViewById<View>(R.id.setter) as FloatingActionButton
        button.size = FloatingActionButton.SIZE_MINI
        button.setColorNormalResId(R.color.pink)
        button.setColorPressedResId(R.color.pink_pressed)
        button.setIcon(R.drawable.ic_action_content_new)
        button.isStrokeVisible = false
        val actionB = findViewById<View>(R.id.action_b)
        val actionC =
            FloatingActionButton(baseContext)
        actionC.title = "Hide/Show Action above"
        actionC.setOnClickListener {
            actionB.visibility = if (actionB.visibility == View.GONE) View.VISIBLE else View.GONE
        }
        val menuMultipleActions =
            findViewById<View>(R.id.multiple_actions) as FloatingActionsMenu
        menuMultipleActions.addButton(actionC)
        val removeAction =
            findViewById<View>(R.id.button_remove) as FloatingActionButton
        removeAction.setOnClickListener {
            (findViewById<View>(R.id.multiple_actions_down) as FloatingActionsMenu).removeButton(
                removeAction
            )
        }
        val drawable = ShapeDrawable(OvalShape())
        drawable.paint.color = resources.getColor(R.color.white)
        (findViewById<View>(R.id.setter_drawable) as FloatingActionButton).setIconDrawable(
            drawable
        )
        val actionA =
            findViewById<View>(R.id.action_a) as FloatingActionButton
        actionA.setOnClickListener { actionA.title = "Action A clicked" }

        // Test that FAMs containing FABs with visibility GONE do not cause crashes
        findViewById<View>(R.id.button_gone).visibility = View.GONE
        val actionEnable =
            findViewById<View>(R.id.action_enable) as FloatingActionButton
        actionEnable.setOnClickListener {
            menuMultipleActions.isEnabled = !menuMultipleActions.isEnabled
        }
        val rightLabels =
            findViewById<View>(R.id.right_labels) as FloatingActionsMenu
        val addedOnce =
            FloatingActionButton(this)
        addedOnce.title = "Added once"
        rightLabels.addButton(addedOnce)
        val addedTwice =
            FloatingActionButton(this)
        addedTwice.title = "Added twice"
        rightLabels.addButton(addedTwice)
        rightLabels.removeButton(addedTwice)
        rightLabels.addButton(addedTwice)
    }
}