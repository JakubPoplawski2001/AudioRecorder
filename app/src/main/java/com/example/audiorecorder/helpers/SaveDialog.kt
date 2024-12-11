package com.example.audiorecorder.helpers

import android.content.Context
import android.widget.EditText
import androidx.appcompat.app.AlertDialog

class SaveDialog (
    private val context: Context,
    private val onValidateInput: (String) -> (Int),
    private val onSave: (String) -> Unit,
    private val onCancel: () -> Unit
) {

    private var dialog: AlertDialog
    private var nameField: EditText = EditText(context)

    companion object {
        // Error codes
        const val SUCCESS = 0
        const val NULL_OR_BLANK = 1
        const val FILE_ALREADY_EXIST = 2
    }


    init {
        nameField.hint = "File name"

        dialog = AlertDialog.Builder(context)
            .setTitle("Save audio file")
            .setMessage("Enter file name")
            .setView(nameField)
            .setCancelable(false)
            .setPositiveButton("Save", null)
            .setNegativeButton("Cancel and delete file", null)
            .create()
    }

    fun show(){
        dialog.show()
        setButtonsListeners()
    }

    private fun setButtonsListeners() {
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            val name = nameField.text.toString().trim()

            val errorCode = onValidateInput(name)

            nameField.error = when (errorCode) {
                SUCCESS -> null
                NULL_OR_BLANK -> "Name can not be empty"
                FILE_ALREADY_EXIST -> "File with that name already exists"
                else -> "Unknown Error"
            }

            if (errorCode == SUCCESS) {
                dialog.dismiss()
                onSave(name)
            }
        }

        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener {
            dialog.dismiss()
            onCancel()
        }
    }
}