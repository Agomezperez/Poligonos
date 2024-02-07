package com.example.pruebatecnica

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.pruebatecnica.data.local.database.repository.FiguraRepo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var figuraRepo: FiguraRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CoroutineScope(Dispatchers.Main).launch {
            figuraRepo.insertarFigurasDesdeJson()
        }
    }
}

