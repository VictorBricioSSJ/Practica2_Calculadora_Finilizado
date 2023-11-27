package com.example.practica2_calculadora

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.PI
import kotlin.math.pow

enum class FormaGeometrica {
    CUADRADO, RECTANGULO, CIRCULO, PENTAGONO, HEXAGONO, TRIANGULO
}

class MainActivity : AppCompatActivity() {

    private lateinit var formaSeleccionada: FormaGeometrica
    private lateinit var entradaLado: TextView
    private lateinit var entradaBase: TextView
    private lateinit var entradaAltura: TextView
    private lateinit var entradaRadio: TextView
    private lateinit var entradaApotema: TextView
    private lateinit var textViewResultado: TextView
    private lateinit var botonCalcular: Button
    private lateinit var botonesNumero: Array<ImageButton>
    private lateinit var botonBorrar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        entradaLado = findViewById(R.id.textView5)
        entradaBase = findViewById(R.id.textView3)
        entradaAltura = findViewById(R.id.textView4)
        entradaRadio = findViewById(R.id.textView5)
        entradaApotema = findViewById(R.id.textView3)
        textViewResultado = findViewById(R.id.textView4)
        botonCalcular = findViewById(R.id.botonCalcular)
        botonBorrar = findViewById(R.id.botonBorrar)

        botonesNumero = arrayOf(
            findViewById(R.id.imageButtonUNO),
            findViewById(R.id.imageButtonDOS),
            findViewById(R.id.imageButtonTRES),
            findViewById(R.id.imageButtonCUATRO),
            findViewById(R.id.imageButtonCINCO),
            findViewById(R.id.imageButtonSEIS),
            findViewById(R.id.imageButtonSIETE),
            findViewById(R.id.imageButtonOCHO),
            findViewById(R.id.imageButtonNUEVE),
            findViewById(R.id.imageButtonCERO)
        )
        // Llamada a funciones para establecer los listeners de los botones
        establecerListenersForma()
        establecerListenersNumeros()
        establecerListenerBotonCalcular()
        establecerListenerBotonBorrar()
    }

    private fun establecerListenersForma() {
        val botonesForma = arrayOf(
            findViewById<ImageButton>(R.id.imageButtonCuadrado),
            findViewById<ImageButton>(R.id.imageButtonRectangulo),
            findViewById<ImageButton>(R.id.imageButtonTriangulo),
            findViewById<ImageButton>(R.id.imageButtonCirculo),
            findViewById<ImageButton>(R.id.imageButtonPentagono),
            findViewById<ImageButton>(R.id.imageButtonHexagono)
        )
        // Listener para cada botón que selecciona la forma geométrica
        for (boton in botonesForma) {
            boton.setOnClickListener {
                // Determina la forma seleccionada según el botón presionado
                formaSeleccionada = when (boton.id) {
                    R.id.imageButtonCuadrado -> FormaGeometrica.CUADRADO
                    R.id.imageButtonRectangulo -> FormaGeometrica.RECTANGULO
                    R.id.imageButtonTriangulo -> FormaGeometrica.TRIANGULO
                    R.id.imageButtonCirculo -> FormaGeometrica.CIRCULO
                    R.id.imageButtonPentagono -> FormaGeometrica.PENTAGONO
                    R.id.imageButtonHexagono -> FormaGeometrica.HEXAGONO
                    else -> FormaGeometrica.CUADRADO
                }
                // Actualiza la visibilidad de las entradas dependiendo de la forma seleccionada
                actualizarVisibilidadEntrada()
            }
        }
    }
    // Función para establecer los listeners de los botones numéricos
    /*private fun establecerListenersNumeros() {
        // Listener para cada botón numérico
        for (i in botonesNumero.indices) {
            val numero = i.toString()
            botonesNumero[i].setOnClickListener {
                agregarNumero(numero)
            }
        }
    }*/



    private fun establecerListenersNumeros() {
        // Listener para cada botón numérico
        for (i in botonesNumero.indices) {
            val numero = i.toString()
            botonesNumero[i].setOnClickListener { agregarNumero(numero) }
        }
    }
    // Función para agregar un número a la entrada correspondiente según la forma seleccionada
    private fun agregarNumero(view: String) {
        val numero = view
        val textView = obtenerTextView(formaSeleccionada)
        val textoActual = textView?.text.toString()
        textView?.text = textoActual + numero
    }

    private fun obtenerTextView(formaSeleccionada: FormaGeometrica): TextView? {
        return when (formaSeleccionada) {
            FormaGeometrica.CUADRADO -> entradaLado
            FormaGeometrica.RECTANGULO, FormaGeometrica.TRIANGULO -> entradaBase
            FormaGeometrica.CIRCULO -> entradaRadio
            FormaGeometrica.PENTAGONO, FormaGeometrica.HEXAGONO -> entradaApotema
        }
    }



    /*private fun agregarNumero(numero: String) {
        val editText = when (formaSeleccionada) {
            FormaGeometrica.CUADRADO -> entradaLado
            FormaGeometrica.RECTANGULO, FormaGeometrica.TRIANGULO -> entradaBase
            FormaGeometrica.CIRCULO -> entradaRadio
            FormaGeometrica.PENTAGONO, FormaGeometrica.HEXAGONO -> entradaApotema
        }
        val textoActual = editText.text.toString()
        editText.setText(textoActual + numero)
    }*/
    // Función para establecer el listener del botón de cálculo
    private fun establecerListenerBotonCalcular() {
        botonCalcular.setOnClickListener {
            calcularArea()
        }
    }
    // Función para establecer el listener del botón de borrar
    private fun establecerListenerBotonBorrar() {
        botonBorrar.setOnClickListener {
            borrarEntradas()
        }
    }
    // Función para actualizar la visibilidad de las entradas dependiendo de la forma seleccionada
    private fun actualizarVisibilidadEntrada() {
        // Oculta todos los campos de entrada
        entradaLado.visibility = View.GONE
        entradaBase.visibility = View.GONE
        entradaAltura.visibility = View.GONE
        entradaRadio.visibility = View.GONE
        entradaApotema.visibility = View.GONE

        // muestra solo el campo que se quiere para la forma geometrica seleccionada
        when (formaSeleccionada) {
            FormaGeometrica.CUADRADO -> entradaLado.visibility = View.VISIBLE
            FormaGeometrica.RECTANGULO -> {
                entradaBase.visibility = View.VISIBLE
                entradaAltura.visibility = View.VISIBLE
            }
            FormaGeometrica.CIRCULO -> entradaRadio.visibility = View.VISIBLE
            FormaGeometrica.TRIANGULO -> {
                entradaBase.visibility = View.VISIBLE
                entradaAltura.visibility = View.VISIBLE
            }
            FormaGeometrica.PENTAGONO -> entradaApotema.visibility = View.VISIBLE
            FormaGeometrica.HEXAGONO -> entradaApotema.visibility = View.VISIBLE
        }
        textViewResultado.visibility = View.VISIBLE
    }




    /* private fun actualizarVisibilidadEntrada() {
         when (formaSeleccionada) {
             FormaGeometrica.CUADRADO -> {
                 entradaLado.visibility = View.VISIBLE
                 entradaBase.visibility = View.GONE
                 entradaAltura.visibility = View.GONE
                 entradaRadio.visibility = View.GONE
                 entradaApotema.visibility = View.GONE
             }
             FormaGeometrica.RECTANGULO -> {
                 entradaLado.visibility = View.GONE
                 entradaBase.visibility = View.VISIBLE
                 entradaAltura.visibility = View.VISIBLE
                 entradaRadio.visibility = View.GONE
                 entradaApotema.visibility = View.GONE
             }
             FormaGeometrica.CIRCULO -> {
                 entradaLado.visibility = View.GONE
                 entradaBase.visibility = View.GONE
                 entradaAltura.visibility = View.GONE
                 entradaRadio.visibility = View.VISIBLE
                 entradaApotema.visibility = View.GONE
             }
             FormaGeometrica.TRIANGULO -> {
                 entradaLado.visibility = View.GONE
                 entradaBase.visibility = View.VISIBLE
                 entradaAltura.visibility = View.VISIBLE
                 entradaRadio.visibility = View.GONE
                 entradaApotema.visibility = View.GONE
             }
             FormaGeometrica.PENTAGONO -> {
                 entradaLado.visibility = View.VISIBLE
                 entradaBase.visibility = View.GONE
                 entradaAltura.visibility = View.GONE
                 entradaRadio.visibility = View.GONE
                 entradaApotema.visibility = View.VISIBLE
             }
             FormaGeometrica.HEXAGONO -> {
                 entradaLado.visibility = View.VISIBLE
                 entradaBase.visibility = View.GONE
                 entradaAltura.visibility = View.GONE
                 entradaRadio.visibility = View.GONE
                 entradaApotema.visibility = View.VISIBLE
             }
         }
     }*/




    /*private fun actualizarVisibilidadEntrada() {
        entradaLado.visibility = if (formaSeleccionada == FormaGeometrica.CUADRADO) View.VISIBLE else View.GONE
        entradaBase.visibility =
            if (formaSeleccionada == FormaGeometrica.RECTANGULO || formaSeleccionada == FormaGeometrica.TRIANGULO) View.VISIBLE else View.GONE
        entradaAltura.visibility =
            if (formaSeleccionada == FormaGeometrica.RECTANGULO || formaSeleccionada == FormaGeometrica.TRIANGULO) View.VISIBLE else View.GONE
        entradaRadio.visibility = if (formaSeleccionada == FormaGeometrica.CIRCULO) View.VISIBLE else View.GONE
        entradaApotema.visibility =
            if (formaSeleccionada == FormaGeometrica.PENTAGONO || formaSeleccionada == FormaGeometrica.HEXAGONO) View.VISIBLE else View.GONE
    }*/
    // Función para calcular el área dependiendo de la forma seleccionada
    private fun calcularArea() {
        val area = when (formaSeleccionada) {
            FormaGeometrica.CUADRADO -> calcularAreaCuadrado(entradaLado.text.toString().toDouble())
            FormaGeometrica.RECTANGULO -> calcularAreaRectangulo(
                entradaBase.text.toString().toDouble(),
                entradaAltura.text.toString().toDouble()
            )
            FormaGeometrica.CIRCULO -> calcularAreaCirculo(entradaRadio.text.toString().toDouble())
            FormaGeometrica.TRIANGULO -> calcularAreaTriangulo(
                entradaBase.text.toString().toDouble(),
                entradaAltura.text.toString().toDouble()
            )
            FormaGeometrica.PENTAGONO -> calcularAreaPentagono(
                entradaBase.text.toString().toDouble(),
                entradaApotema.text.toString().toDouble()
            )
            FormaGeometrica.HEXAGONO -> calcularAreaHexagono(
                entradaBase.text.toString().toDouble(),
                entradaApotema.text.toString().toDouble()
            )
        }
        // Actualiza el TextView con el resultado del área
        textViewResultado.text = "El área de la forma geométrica es: $area"
    }

    // Funciones para calcular el área de cada forma geométrica
    private fun calcularAreaCuadrado(lado: Double): Double {
        return lado * lado
    }

    private fun calcularAreaRectangulo(base: Double, altura: Double): Double {
        return base * altura
    }

    private fun calcularAreaCirculo(radio: Double): Double {
        return PI * radio.pow(2)
    }

    private fun calcularAreaTriangulo(base: Double, altura: Double): Double {
        return (base * altura) / 2
    }

    private fun calcularAreaPentagono(base: Double, apotema: Double): Double {
        return (5 * base * apotema) / 2
    }

    private fun calcularAreaHexagono(base: Double, apotema: Double): Double {
        return (6 * base * apotema) / 2
    }

    // Función para borrar las entradas y reiniciar el TextView del resultado
    private fun borrarEntradas() {
        entradaLado.text = ""
        entradaBase.text = ""
        entradaAltura.text = ""
        entradaRadio.text = ""
        entradaApotema.text = ""
        textViewResultado.text = "El área de la forma geométrica es: "
    }
}
