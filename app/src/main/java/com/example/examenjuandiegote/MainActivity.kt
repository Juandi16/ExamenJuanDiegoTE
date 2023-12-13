package com.example.examenjuandiegote

import android.os.Bundle
import android.provider.ContactsContract.Data
import android.widget.NumberPicker.OnValueChangeListener
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.examen1.data.DataSource
import com.example.examen1.data.Producto
import com.example.examenjuandiegote.ui.theme.ExamenJuanDiegoTETheme
import com.google.android.gms.analytics.ecommerce.Product

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExamenJuanDiegoTETheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppScreen()
                }
            }
        }
    }
}


@Composable
fun AppScreen() {
    var texto by remember { mutableStateOf("Todavía no han añadido ningún valor") }
    var nombre by remember { mutableStateOf(" ") }
    var precio by remember { mutableStateOf("0") }
    var precioAntiguo by remember { mutableStateOf(0) }
    var productos = DataSource.productos
    Column {
        NombreAlumno(nombre = "Juandi")
        Text(
            text = texto,
            modifier = Modifier
                .background(color = Color.LightGray)
                .fillMaxWidth()
        )
        Row() {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextoInput(
                    label = "nombre",
                    value = nombre,
                    onValueChange = { nombre = it },
                    keyboardOptions = KeyboardOptions.Default.copy(),
                    modifier = Modifier.width(200.dp)
                )
                TextoInput(
                    label = "precio",
                    value = precio,
                    onValueChange = { precio = it },
                    keyboardOptions = KeyboardOptions.Default.copy(),
                    modifier = Modifier.width(200.dp)
                )
                Button(onClick = {
                    var accion: Int = 3
                    for (producto in productos) {
                        if (nombre == producto.nombre) {
                            accion = 2;
                            if (precio.toInt() == producto.precio) {
                                accion = 1;
                            } else {
                                precioAntiguo = producto.precio;
                                producto.precio = precio.toInt();
                            }
                        }
                    }
                    if (accion == 1) {
                        texto = "NO se ha modificado el produco $nombre, el precio es el mismo"
                    }
                    if (accion == 2) {
                        texto =
                            "Del producto $nombre se ha modificado el precio de $precioAntiguo a $precio"
                    }
                    if (accion == 3) {
                        texto = "Se ha añadido el producto $nombre con precio $precio"
                        productos.add(Producto(nombre, precio.toInt()))
                    }
                }) {
                    Text(text = "Add/Update producto")
                }
            }
            LazyColumn() {
                items(productos) { producto ->
                    ProductosListItem(producto = producto)
                }
            }
        }
    }
}

@Composable
fun NombreAlumno(
    nombre: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = "Hola soy alumno $nombre",
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.LightGray)
            .padding(horizontal = 20.dp, vertical = 50.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextoInput(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        keyboardOptions = keyboardOptions,
        modifier = modifier.padding(8.dp)
    )
}

//cada producto
@Composable
fun ProductosListItem(producto: Producto) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(200.dp)
    ) {
        Text(
            text = "Nombre: " + producto.nombre,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Yellow)
                .padding(20.dp)
        )
        Text(
            text = "Precio: " + producto.precio,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Cyan)
                .padding(20.dp)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun Preview() {
    ExamenJuanDiegoTETheme {
        AppScreen()
    }
}