package pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.fragments

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

abstract class PermissionedFragment(private val requestCode: Int) : Fragment() {

    fun onRequestPermissions(context: Context, permissions: Array<String>) {
        var permissionsGiven = 0

        permissions.forEach {
            if (ContextCompat.checkSelfPermission(
                    context,
                    it
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(permissions, requestCode)
            } else {
                permissionsGiven++
            }
        }
        if (permissionsGiven == permissions.size) {
            onRequestPermissionsSucess()
        }
    }

    abstract fun onRequestPermissionsSucess()
    abstract fun onRequestPermissionsFailure()

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (this.requestCode == requestCode) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                onRequestPermissionsSucess()
            } else {
                onRequestPermissionsFailure()
            }
        }
    }

}