package com.example.pruebatecnica.ui.design_figuras

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pruebatecnica.data.local.database.entity.FiguraEntity
import com.example.pruebatecnica.data.local.database.repository.FiguraRepo
import com.example.pruebatecnica.databinding.ActivityDesignBinding
import com.example.pruebatecnica.domain.model.PointModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import kotlin.math.pow

@AndroidEntryPoint
class DesignActivity: AppCompatActivity() {
    lateinit var binding: ActivityDesignBinding

    @Inject
    lateinit var figuraRepo: FiguraRepo

    private lateinit var figuraEntity: FiguraEntity
    private var selectedPointIndex: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDesignBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.getParcelable<FiguraEntity>("figura")?.also { it2 -> figuraEntity = it2 } ?: kotlin.run {
            Toast.makeText(this, "Huho un problema al cargar la información", Toast.LENGTH_SHORT).show()
            finish()
        }

        val parcelableArray = intent.extras?.getParcelableArray("point")
        if (parcelableArray != null) {
            val points = ArrayList<PointModel>()
            for (parcelable in parcelableArray) {
                if (parcelable is PointModel) {
                    points.add(parcelable)
                }
            }
            figuraEntity.puntos = points
        } else {
            Toast.makeText(this, "Huho un problema al cargar los puntos", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.imageViewFigura.post { drawFigure() }
        binding.imageViewFigura.setOnTouchListener { view, motionEvent -> handleTouch(view, motionEvent) }
    }

    private fun drawFigure() {
        val width = binding.imageViewFigura.width
        val height = binding.imageViewFigura.height

        if (width <= 0 || height <= 0) {
            Toast.makeText(this, "Error: La vista no tiene dimensiones válidas", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint().apply {
            color = Color.BLACK
            style = Paint.Style.STROKE
            strokeWidth = 5f
        }

        val path = Path()
        val firstPoint = figuraEntity.puntos.firstOrNull()
        if (firstPoint != null) {
            val scaleX = width.toFloat()
            val scaleY = height.toFloat()
            path.moveTo(firstPoint.x.toFloat() * scaleX, firstPoint.y.toFloat() * scaleY)
            for (i in 1 until figuraEntity.puntos.size) {
                val point = figuraEntity.puntos[i]
                path.lineTo(point.x.toFloat() * scaleX, point.y.toFloat() * scaleY)
            }
            path.close()
            canvas.drawPath(path, paint)
        }

        binding.imageViewFigura.setImageBitmap(bitmap)
        instanceListeners()
    }

    private fun instanceListeners() {
        binding.save.setOnClickListener {
            guardarFigura()
        }
    }

    private fun guardarFigura() {
        runBlocking {
            figuraRepo.updateFigura(figuraEntity)
        }

        this.setResult(RESULT_OK)
        finish()

        Toast.makeText(this, "Figura guardada", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun handleTouch(view: View, motionEvent: MotionEvent): Boolean {
        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {
                val x = motionEvent.x / view.width.toFloat()
                val y = motionEvent.y / view.height.toFloat()
                selectedPointIndex = findNearestPointIndex(x, y)
            }

            MotionEvent.ACTION_MOVE -> {
                if (selectedPointIndex != -1) {
                    val x = motionEvent.x / view.width.toFloat()
                    val y = motionEvent.y / view.height.toFloat()
                    if (x in 0f..1f && y in 0f..1f) {
                        val newPoints = figuraEntity.puntos.toMutableList()
                        newPoints[selectedPointIndex] = PointModel(x.toDouble(), y.toDouble())
                        figuraEntity.puntos = newPoints
                        drawFigure()
                    }
                }
            }

            MotionEvent.ACTION_UP -> {
                selectedPointIndex = -1
            }
        }
        return true
    }

    private fun findNearestPointIndex(x: Float, y: Float): Int {
        var nearestIndex = -1
        var minDistance = Double.MAX_VALUE
        for (i in figuraEntity.puntos.indices) {
            val point = figuraEntity.puntos[i]
            val distance = Math.sqrt((point.x - x).pow(2) + (point.y - y).pow(2))
            if (distance < minDistance) {
                minDistance = distance
                nearestIndex = i
            }
        }
        return nearestIndex
    }

}