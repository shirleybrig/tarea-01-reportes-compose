package com.example.tarjetapresentacion

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tarjetapresentacion.ui.theme.TarjetaPresentacionTheme

// Modelo de datos para reportes
data class Reporte(
    val id: Int,
    val titulo: String,
    val descripcion: String,
    val prioridad: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("CICLO_VIDA", "onCreate iniciado correctamente en MainActivity")
        enableEdgeToEdge()
        setContent {
            TarjetaPresentacionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PantallaReportes(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// 1. parámetros y Logcat
@Composable
fun TarjetaPresentacion(
    nombre: String,
    cargo: String,
    contacto: String,
    modifier: Modifier = Modifier
) {
    Log.d("COMPOSE_UI", "Renderizando TarjetaPresentacion para: $nombre")
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = nombre, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(text = cargo, fontSize = 14.sp, color = MaterialTheme.colorScheme.onPrimaryContainer)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Contacto: $contacto", fontSize = 12.sp)
        }
    }
}

// 2. lógica de prioridad y Logcat
@Composable
fun TarjetaReporte(reporte: Reporte, modifier: Modifier = Modifier) {
    val colorPrioridad = when (reporte.prioridad.lowercase()) {
        "alta" -> {
            Log.w("ALERTA_REPORTE", "Reporte de ALTA prioridad detectado: ${reporte.titulo}")
            Color(0xFFFFCDD2) // Rojo suave
        }
        "media" -> {
            Log.i("INFO_REPORTE", "Reporte de prioridad MEDIA: ${reporte.titulo}")
            Color(0xFFFFF9C4) // Amarillo suave
        }
        else -> {
            Log.d("DEBUG_REPORTE", "Reporte de prioridad BAJA: ${reporte.titulo}")
            Color(0xFFC8E6C9) // Verde suave
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = colorPrioridad),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = reporte.titulo, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
                Text(
                    text = reporte.prioridad.uppercase(),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = reporte.descripcion, fontSize = 14.sp, color = Color.Black)
        }
    }
}

// 3. lista dinámica y depuración
@Composable
fun PantallaReportes(modifier: Modifier = Modifier) {
    val listaReportes = listOf(
        Reporte(1, "Falla en servidor principal", "Tiempo de respuesta elevado en el módulo de pagos.", "Alta"),
        Reporte(2, "Actualización de catálogo", "Revisar imágenes nuevas de productos de temporada.", "Media"),
        Reporte(3, "Respaldo semanal", "Copia de seguridad completada sin novedades.", "Baja"),
        Reporte(4, "Error de autenticación", "Usuarios reportan cierre inesperado al iniciar sesión.", "Alta")
    )

    Log.i("PANTALLA_REPORTES", "Cargando PantallaReportes con ${listaReportes.size} elementos")

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TarjetaPresentacion(
            nombre = "Shirley Brigitte Cardenas Cobo",
            cargo = "Estudiante Sistemas y gestion de data UR",
            contacto = "shirleybrig767@gmail.com"
        )

        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "Listado de Reportes", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(listaReportes) { item ->
                TarjetaReporte(reporte = item)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaReportesPreview() {
    TarjetaPresentacionTheme {
        PantallaReportes()
    }
}