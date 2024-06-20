package com.example.poligonos.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.poligonos.data.local.database.entity.FiguraEntity
import com.example.poligonos.databinding.ActivityMainBinding
import com.example.poligonos.domain.model.PointModel
import com.example.poligonos.ui.design_figuras.DesignActivity
import com.example.poligonos.ui.main.adapter.FigurasAdapter
import com.example.poligonos.ui.main.viewModel.MainViewModel
import com.example.poligonos.ui.main.viewModel.MainViewModelImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.math.cos
import kotlin.math.sin

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), FigurasAdapter.OnFiguraClickListener {
    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModels<MainViewModelImpl>()

    private val figuraAdapter: FigurasAdapter by lazy { FigurasAdapter() }

    companion object {
        const val REQUEST_CODE_DESIGN_ACTIVITY = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerViewFiguras.apply {
            hasFixedSize()
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = figuraAdapter
        }

        mainViewModel.showFiguras.observe(this@MainActivity) {
            Timber.d("poligono, $it" )
            figuraAdapter.setFiguras(it)
        }

        mainViewModel.init()
        figuraAdapter.setOnFiguraClickListener(this)

        instanceListeners()
    }

    private fun instanceListeners() {
        binding.fabAddFigura.setOnClickListener {
            mostrarDialogoGenerarPoligono()
        }
    }

    private fun mostrarDialogoGenerarPoligono() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Generar Polígono Regular")
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_NUMBER
        builder.setView(input)
        builder.setPositiveButton("Generar") { _, _ ->
            val numLadosStr = input.text.toString()
            if (numLadosStr.isNotEmpty()) {
                val numLados = numLadosStr.toInt()
                generarPoligonoRegular(numLados)
            } else {
                Toast.makeText(this, "Por favor ingrese el número de lados", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.cancel()
        }
        builder.show()
    }

    private fun generarPoligonoRegular(numLados: Int) {
        val centerX = 0.5
        val centerY = 0.5
        val radio = 0.4

        val puntosPoligono = mutableListOf<PointModel>()
        val anguloInicial = -Math.PI / 2

        for (i in 0 until numLados) {
            val angulo = anguloInicial + 2 * Math.PI * i / numLados
            val x = centerX + radio * cos(angulo).toFloat()
            val y = centerY + radio * sin(angulo).toFloat()
            puntosPoligono.add(PointModel(x, y))
        }

        val nuevoPoligono = FiguraEntity(null, "Polígono Regular $numLados Lados", puntosPoligono)
        mainViewModel.insertarPoligono(nuevoPoligono) {
            figuraAdapter.notifyDataSetChanged()
        }
    }

    override fun onFiguraClick(figura: FiguraEntity, position: Int) {
        showDialogEscalarFigura(figura)
    }

    private fun showDialogEscalarFigura(figura: FiguraEntity) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Escalar Figura")
        val input = EditText(this)
        input.inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL
        builder.setView(input)
        builder.setPositiveButton("Aceptar") { _, _ ->
            val escalaStr = input.text.toString()
            if (escalaStr.isNotEmpty()) {
                val escala = escalaStr.toDouble()
                escalarFigura(figura, escala)
            } else {
                Toast.makeText(this, "Por favor ingrese la escala", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.cancel()
        }
        builder.show()
    }

    private fun escalarFigura(figura: FiguraEntity, escala: Double) {
        val puntosEscala = figura.puntos.map { PointModel(it.x * escala, it.y * escala) }
        val intent = Intent(this, DesignActivity::class.java)
        intent.putExtra("figura", figura)
        intent.putExtra("point", puntosEscala.toTypedArray())
        startActivityForResult(intent, REQUEST_CODE_DESIGN_ACTIVITY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_DESIGN_ACTIVITY && resultCode == Activity.RESULT_OK) {
            CoroutineScope(Dispatchers.Main).launch {

                mainViewModel.init()
            }
        }
    }

}

