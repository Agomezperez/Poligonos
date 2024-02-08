package com.example.pruebatecnica.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pruebatecnica.data.local.database.entity.FiguraEntity
import com.example.pruebatecnica.data.local.database.repository.FiguraRepo
import com.example.pruebatecnica.databinding.ActivityMainBinding
import com.example.pruebatecnica.domain.model.PointModel
import com.example.pruebatecnica.ui.design_figuras.DesignActivity
import com.example.pruebatecnica.ui.main.adapter.FigurasAdapter
import com.example.pruebatecnica.ui.main.viewModel.MainPresenter
import com.example.pruebatecnica.ui.main.viewModel.MainPresenterImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import kotlin.math.cos
import kotlin.math.sin

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), FigurasAdapter.OnFiguraClickListener {
    lateinit var binding: ActivityMainBinding

    private val mainPresenter: MainPresenter by viewModels<MainPresenterImpl>()

    @Inject
    lateinit var figuraRepo: FiguraRepo

    private val figuraAdapter: FigurasAdapter by lazy { FigurasAdapter() }

    companion object {
        const val REQUEST_CODE_DESIGN_ACTIVITY = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            mainPresenter.uiEvent.collectLatest {
                when (it) {
                    is MainPresenterImpl.MainEvent.ShowElements -> {
                        figuraAdapter.actualizarFiguras(it.list)
                    }
                }
            }
        }

        binding.recyclerViewFiguras.apply {
            hasFixedSize()
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = figuraAdapter
        }

        figuraAdapter.setOnFiguraClickListener(this)

        CoroutineScope(Dispatchers.Main).launch {
            val figuras = figuraRepo.getAll()
            if (figuras.isEmpty()) {
                figuraRepo.insertarFigurasDesdeJson()
            }

            mainPresenter.mostrarFiguras(figuras)

        }

        instanceListeners()
    }

    private fun instanceListeners() {
        binding.fabAddFigura.setOnClickListener {
            mostrarDialogoGenerarPoligono()
        }
    }

    fun mostrarDialogoGenerarPoligono() {
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
        runBlocking { figuraRepo.insetarFigura(nuevoPoligono) }
        CoroutineScope(Dispatchers.Main).launch {
            val figuras = figuraRepo.getAll()
            figuraAdapter.actualizarFiguras(figuras)
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
                val figuras = figuraRepo.getAll()
                figuraAdapter.actualizarFiguras(figuras)
                mainPresenter.mostrarFiguras(figuras)
            }
        }
    }

}

