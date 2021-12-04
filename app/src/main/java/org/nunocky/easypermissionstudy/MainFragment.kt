package org.nunocky.easypermissionstudy

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.vmadalin.easypermissions.EasyPermissions
import org.nunocky.easypermissionstudy.databinding.FragmentMainBinding


class MainFragment : Fragment(), EasyPermissions.PermissionCallbacks,
    EasyPermissions.RationaleCallbacks {
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onResume() {
        super.onResume()
        checkPermissions()
    }

    //  -----------------------------------------------
    //  permissions issue
    //  -----------------------------------------------
    private val requiredPermissions = listOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA,
    )

    private fun checkPermissions() {
        if (!EasyPermissions.hasPermissions(
                requireActivity(),
                *(requiredPermissions.toTypedArray())
            )
        ) {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(
                host = this,
                rationale = "permissions required",
                requestCode = REQUEST_PERMISSIONS,
                perms = requiredPermissions.toTypedArray()
            )
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, requiredPermissions)) {
            toast("somePermissionPermanentlyDenied")

            val dialog = SimpleDialog()
            dialog.show(childFragmentManager, "dialog")

            // ... or want to call System Setting
//            val intent = Intent(
//                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
//                Uri.parse("package:" + activity!!.packageName)
//            )
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            activity!!.startActivity(intent)
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        if (perms.size == requiredPermissions.size) {
            toast("all permissions granted :-D")
        }
    }

    // TODO : not called?
    override fun onRationaleAccepted(requestCode: Int) {
        toast("onRationaleAccepted")
    }

    // TODO : not called?
    override fun onRationaleDenied(requestCode: Int) {
        toast("onRationaleDenied")
    }

    private fun toast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val REQUEST_PERMISSIONS = 999
    }
}