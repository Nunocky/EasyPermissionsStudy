package org.nunocky.easypermissionstudy

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController

class SimpleDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
            .setTitle("somePermissionPermanentlyDenied")
            .setPositiveButton("close") { _, _ ->
                findNavController().popBackStack()
            }

        return builder.create()
    }
}