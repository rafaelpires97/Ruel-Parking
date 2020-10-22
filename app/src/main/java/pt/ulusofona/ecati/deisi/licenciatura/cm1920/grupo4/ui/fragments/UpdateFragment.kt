package pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_update_vehicle.*
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.R
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.entities.Veiculo
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.service.repository.local.parque.AppDataBase
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.NavigationManager
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.activities.REQUEST_CODE
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.adapter.VeiculoAdapter
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class UpdateFragment : Fragment() {

    var imageView: ImageView? = null
    var photoFile: File? = null

    private  val PERMISSION_CODE = 123
    var mCurrentPhotoPath: String? = null
    var veiculo: Veiculo? = null

    lateinit var mAdapter: VeiculoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_update_vehicle, container, false)

        return view

    }


    companion object {
        fun openFile(veiculoPath: String): Bitmap? {
            val imgFile = File(veiculoPath)
            if (imgFile.exists()) {
                val myBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
                return myBitmap
            }
            return null
        }
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
    override fun onStart() {
        super.onStart()

        inserir_foto_veiculo.setImageBitmap(openFile(newVeiculo.image_carro))


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


        btn_vehicle_update.setOnClickListener {
            newVeiculo.image_carro = mCurrentPhotoPath.toString()
            newVeiculo.marca = marca_update_car.text.toString()
            newVeiculo.modelo = modelo_update_car.text.toString()
            newVeiculo.matricula = matricula_update_car.text.toString()
            newVeiculo.ano_matricula = data_update_car.text.toString()
            update(newVeiculo)


            activity?.supportFragmentManager?.let { NavigationManager.goToVeiculoFragment(it) }
        }

        mostrarInfo(newVeiculo)
    }

    fun update(veiculo: Veiculo) {
        AppDataBase.getDatabase(activity as Context).veiculoDAO().update(veiculo)
        //listVeiculo=  AppDataBase.getDatabase(activity as Context).veiculoDAO().getList()
    }

    fun mostrarInfo(veiculo: Veiculo?) {
        veiculo?.let {
            marca_update_car.setText(veiculo.marca)
            modelo_update_car.setText(veiculo.modelo)
            matricula_update_car.setText(veiculo.matricula)
            data_update_car.setText(veiculo.ano_matricula)
            inserir_foto_veiculo.setImageBitmap(openFile(veiculo.image_carro))

        }
    }
}