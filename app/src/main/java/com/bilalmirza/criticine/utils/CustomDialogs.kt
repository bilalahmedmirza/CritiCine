package com.bilalmirza.criticine.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import android.widget.Toast
import com.bilalmirza.criticine.databinding.CustomAlertDialogBinding
import com.bilalmirza.criticine.interfaces.ItemClickListener

object CustomDialogs {
    fun showEditNameDialog(context: Context, name: String, listener: ItemClickListener<String>) {
        val dialog = Dialog(context)
        val customBinding = CustomAlertDialogBinding.inflate(dialog.layoutInflater)
        dialog.setContentView(customBinding.root)
        dialog.setCanceledOnTouchOutside(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )

        customBinding.changeNameET.setText(name)

        customBinding.updateNameBtn.setOnClickListener {
            if (customBinding.changeNameET.text.toString().isEmpty()) {
                Toast.makeText(context, "Please enter a name.", Toast.LENGTH_SHORT).show()
            } else {
                listener.onItemClick(customBinding.changeNameET.text.toString(), 0, 0)
                dialog.dismiss()
            }
        }
        customBinding.cancelBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}