package com.example.poligonos.ui.main.adapter

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.poligonos.data.local.database.entity.FiguraEntity
import com.example.poligonos.databinding.ItemFiguraBinding

class FigurasAdapter : RecyclerView.Adapter<FigurasAdapter.FiguraViewHolder>() {
    private var figuras: List<FiguraEntity> = emptyList()

    private var onFiguraClickListener: OnFiguraClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FiguraViewHolder {
        val binding = ItemFiguraBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FiguraViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FiguraViewHolder, position: Int) {
        holder.bind(figuras[position], position)
        holder.itemView.setOnClickListener {
            onFiguraClickListener?.onFiguraClick(figuras[position], position)
        }
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    override fun getItemCount(): Int = figuras.size

    fun setFiguras(figuras: List<FiguraEntity>) {
        this.figuras = figuras
        notifyDataSetChanged()
    }

    inner class FiguraViewHolder(private val binding: ItemFiguraBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(figura: FiguraEntity, position: Int) {
            binding.textViewName.text = figura.nombre

            binding.root.setOnClickListener {
            }

            val bitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)

            val paint = Paint().apply {
                color = Color.BLACK
                style = Paint.Style.FILL
            }

            val puntos = figura.puntos
            if (puntos.isNotEmpty()) {
                val path = Path()
                val width = bitmap.width.toFloat()
                val height = bitmap.height.toFloat()
                path.moveTo(puntos[0].x.toFloat() * width, puntos[0].y.toFloat() * height)
                for (i in 1 until puntos.size) {
                    path.lineTo(puntos[i].x.toFloat() * width, puntos[i].y.toFloat() * height)
                }
                path.close()
                canvas.drawPath(path, paint)
            }

            binding.imageViewFigura.setImageBitmap(bitmap)

            binding.linearLayoutItemFigura.setOnClickListener {
                onFiguraClickListener?.onFiguraClick(figura, position)
            }
        }
    }

    interface OnFiguraClickListener {
        fun onFiguraClick(figura: FiguraEntity, position: Int)
    }

    fun setOnFiguraClickListener(listener: OnFiguraClickListener) {
        this.onFiguraClickListener = listener
    }

}
