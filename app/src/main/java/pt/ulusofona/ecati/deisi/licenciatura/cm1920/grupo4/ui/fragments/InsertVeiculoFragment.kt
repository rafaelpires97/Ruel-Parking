package pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_insert_veiculo.*
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.R
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.entities.Veiculo
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.service.repository.local.parque.AppDataBase
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.NavigationManager
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.activities.REQUEST_CODE
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.activities.listVeiculo
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.adapter.VeiculoAdapter
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.fragments.UpdateFragment.Companion.openFile
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

lateinit var newVeiculo : Veiculo
class InsertVeiculoFragment : Fragment() {

    lateinit var mAdapter: VeiculoAdapter
    private  val PERMISSION_CODE = 123
    private lateinit var bmp: Bitmap
    var photoFile: File? = null

    var mCurrentPhotoPath: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_insert_veiculo, container, false)

        return view
    }

    private fun openCamera() {
        val camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        createImageFile()
        val uriSavedImage: Uri = Uri.fromFile(File(mCurrentPhotoPath))
        camera.putExtra(MediaStore.EXTRA_OUTPUT ,uriSavedImage)
        startActivityForResult(camera, REQUEST_CODE)
    }

    @Throws(IOException::class)
    fun createImageFile(): File? {
        // Create an image file name
        val format = SimpleDateFormat("yyyyMMddhhmmss")
        val hora = format.format(Date())
        val timeStamp: String = hora.format(format)
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir: File = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            imageFileName,  /* prefix */
            ".jpg",  /* suffix */
            storageDir /* directory */
        )
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.absolutePath
        return image
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode== REQUEST_CODE){
            inserir_foto_veiculo.setImageBitmap(openFile(mCurrentPhotoPath.toString()))
        }
    }




    var i: Int = 0
    @SuppressLint("WrongConstant")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        inserir_foto_veiculo.setOnClickListener {
            if(PermissionChecker.checkSelfPermission(
                    activity as Context,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_DENIED || PermissionChecker.checkSelfPermission(
                    activity as Context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_DENIED){
                val permission = arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permission,PERMISSION_CODE)
            }else{
                openCamera()
            }
        }


        btn_vehicle_insert.setOnClickListener {
                newVeiculo = Veiculo(
                    marca_info_car.text.toString(),
                    modelo_info_car.text.toString(),
                    matricula_info_car.text.toString(),
                    mCurrentPhotoPath.toString(),
                    data_info_car.text.toString()
                )

                mAdapter =VeiculoAdapter(listVeiculo)
                mAdapter.notifyItemChanged(i)

                insert(listVeiculo.size, newVeiculo!!)



                activity?.supportFragmentManager?.let { NavigationManager.goToVeiculoFragment(it) }

        }
    }

    fun insert(position: Int, veiculo: Veiculo) {
        AppDataBase.getDatabase(activity as Context).veiculoDAO().save(veiculo)
        mAdapter.notifyItemInserted(position)
    }
}