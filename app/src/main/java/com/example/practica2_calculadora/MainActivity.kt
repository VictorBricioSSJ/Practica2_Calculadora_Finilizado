package com.example.practica2_calculadora

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
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
    private lateinit var botonCambiar: ImageButton




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        entradaLado = findViewById(R.id.textViewLado)
        entradaBase = findViewById(R.id.textViewBase)
        entradaAltura = findViewById(R.id.textViewAltura)
        entradaRadio = findViewById(R.id.textViewRadio)
        entradaApotema = findViewById(R.id.textViewApotema)
        textViewResultado = findViewById(R.id.textView4)
        botonCalcular = findViewById(R.id.botonCalcular)
        botonBorrar = findViewById(R.id.botonBorrar)
        botonCambiar = findViewById(R.id.imageButtonCambiar)

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
        //llama a las funciones para establecer los listeners de los botones
        establecerListenersForma()
        establecerListenersNumeros()
        establecerListenerBotonCalcular()
        establecerListenerBotonBorrar()
        establecerListenerBotonCambiar()
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
        //listener para cada botnn que selecciona la forma geometrica
        for (boton in botonesForma) {
            boton.setOnClickListener {
                //determina la forma seleccionada segun el boton seleccioando
                formaSeleccionada = when (boton.id) {
                    R.id.imageButtonCuadrado -> FormaGeometrica.CUADRADO
                    R.id.imageButtonRectangulo -> FormaGeometrica.RECTANGULO
                    R.id.imageButtonTriangulo -> FormaGeometrica.TRIANGULO
                    R.id.imageButtonCirculo -> FormaGeometrica.CIRCULO
                    R.id.imageButtonPentagono -> FormaGeometrica.PENTAGONO
                    R.id.imageButtonHexagono -> FormaGeometrica.HEXAGONO
                    else -> FormaGeometrica.CUADRADO
                }
                //actualiza la visibilidad de las entradas dependiendo de la forma seleccionada
                actualizarVisibilidadEntrada()
            }
        }
    }
    //funcion para establecer los listeners de los botones numericos
    private fun establecerListenersNumeros() {
        //listener para cada botón numérico
        for (i in botonesNumero.indices) {
            val numero = (i + 1).toString()
            botonesNumero[i].setOnClickListener { view ->
                agregarNumero(numero)
            }
        }
    }




    /*private fun establecerListenersNumeros() {
        // Listener para cada botón numérico
        for (i in botonesNumero.indices) {
            val numero = i.toString()
            botonesNumero[i].setOnClickListener { agregarNumero(numero) }
        }
    }*/
    //funcion para agregar un numero a la entrada segun la forma seleccionada
    /*private fun agregarNumero(numero: String) {
        val textView = obtenerTextView(formaSeleccionada)
        val textoActual = textView?.text.toString()
        textView?.text = textoActual + numero
    }*/


    //añado este botonCambiar para que al darle, pase a escribirme al siguiente textView, por ejemplo en el rectangulo primero pongo los numeros en la base, y para bajar a la altura y colocar los datos le tengo que dar al boton
    var opcion = 0

    private fun listenerCambiar(){
        if(opcion == 0){
            if(botonCambiar.isClickable){
                opcion = 1
            }
        } else
            opcion = 0

    }

    private fun establecerListenerBotonCambiar() {
        botonCambiar.setOnClickListener {
            listenerCambiar()
        }
    }



    //funcion para agregar los numeros dependiendo de la forma que se seleccione
    private fun agregarNumero(numero: String) {
        when (formaSeleccionada) {
            FormaGeometrica.CUADRADO -> {

                entradaLado.append(numero)
            }

            FormaGeometrica.RECTANGULO -> {

                when(opcion){

                    0 -> entradaBase.append(numero)
                    1 ->  entradaAltura.append(numero)
                }

            }

            FormaGeometrica.CIRCULO -> {

                entradaRadio.append(numero)
            }
            FormaGeometrica.TRIANGULO -> {

                when(opcion){

                    0 -> entradaBase.append(numero)
                    1 ->  entradaAltura.append(numero)
                }
            }
            FormaGeometrica.PENTAGONO -> {

                when(opcion){

                    0 -> entradaBase.append(numero)
                    1 ->  entradaApotema.append(numero)
                }
            }
            FormaGeometrica.HEXAGONO -> {

                when(opcion){

                    0 -> entradaBase.append(numero)
                    1 ->  entradaApotema.append(numero)
                }
            }
        }
    }

    fun onNumero(numero: String) {
        agregarNumero(numero)
    }




    private fun obtenerTextView(formaSeleccionada: FormaGeometrica): TextView? {
        return when (formaSeleccionada) {
            FormaGeometrica.CUADRADO -> entradaLado
            FormaGeometrica.RECTANGULO, FormaGeometrica.TRIANGULO -> entradaAltura
            FormaGeometrica.RECTANGULO, FormaGeometrica.TRIANGULO, FormaGeometrica.PENTAGONO, FormaGeometrica.HEXAGONO -> entradaBase
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
    //funcion para establecer el listener del boton de calcular


    //funcion para obtener el valor numero de los TextView
    private fun obtenerValorNumerico(textView: TextView): Double {
        val text = textView.text.toString()
        return try {
            text.toDouble()
        } catch (e: NumberFormatException) {
            Log.e("Error", "Error al convertir $text a Double", e)
            Double.NaN
        }
    }

    private fun actualizarVisibilidadEntrada() {
        //oculta todos los campos de entrada

        entradaLado.visibility = View.GONE
        entradaBase.visibility = View.GONE
        entradaAltura.visibility = View.GONE
        entradaRadio.visibility = View.GONE
        entradaApotema.visibility = View.GONE

        //muestra solo el campo que se quiere para la forma seleccionada
        when (formaSeleccionada) {
            FormaGeometrica.CUADRADO -> {
                entradaLado.text = "Lado:"
                entradaLado.visibility = View.VISIBLE

            }
            FormaGeometrica.RECTANGULO -> {
                entradaBase.text = "Base:"
                entradaAltura.text = "Altura:"
                entradaBase.visibility = View.VISIBLE
                entradaAltura.visibility = View.VISIBLE

            }
            FormaGeometrica.CIRCULO -> {
                entradaRadio.text = "Radio:"
                entradaRadio.visibility = View.VISIBLE

            }
            FormaGeometrica.TRIANGULO -> {
                entradaBase.text = "Base:"
                entradaAltura.text = "Altura:"
                entradaBase.visibility = View.VISIBLE
                entradaAltura.visibility = View.VISIBLE

            }
            FormaGeometrica.PENTAGONO -> {
                entradaBase.text = "Base:"
                entradaApotema.text = "Apotema:"
                entradaBase.visibility = View.VISIBLE
                entradaApotema.visibility = View.VISIBLE

            }
            FormaGeometrica.HEXAGONO -> {
                entradaBase.text = "Base:"
                entradaApotema.text = "Apotema:"
                entradaBase.visibility = View.VISIBLE
                entradaApotema.visibility = View.VISIBLE

            }
        }
        textViewResultado.visibility = View.VISIBLE
    }






    //funcion para actualizar la visibilidad de las entradas dependiendo de la forma seleccionada
    /*private fun actualizarVisibilidadEntrada() {
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
    }*/




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
    //funcion para calcular el area dependiendo de la forma seleccionada
    /*private fun calcularArea() {
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
        //actualiza el TextView con el resultado del area
        textViewResultado.text = "El área de la forma geométrica es: $area"
    }*/

    //funcion para calcular el area segun la forma seleccionada

    private fun establecerListenerBotonCalcular() {
        botonCalcular.setOnClickListener {
            calcularArea()
        }
    }

    //aqui uso la funcion trim, para que cuando coja los numeros de los textView, no me coja la parte del texto, y solo me coja los numeros para que me calcule el area sin nigun problema, porque sino tambien me cogeria el texto y da error
    private fun calcularArea() {

        try {
            val resultado: Double = when (formaSeleccionada) {
                FormaGeometrica.CUADRADO -> {
                    fun String.fullTrim() = trim().replace("Lado:", "")
                    val paraStringCuadrado = entradaLado.text.toString().fullTrim()
                    val lado: Double =  paraStringCuadrado.toDouble()

                    if (lado != null) {
                        val area = calcularAreaCuadrado(lado)
                        actualizarTextViews("", "","", "Lado: $lado", "", "El área de la forma geométrica es: $area")
                        area
                    } else {
                        throw NumberFormatException()
                    }

                }

                FormaGeometrica.RECTANGULO -> {
                    fun String.fullTrim() = trim().replace("Base:", "")
                    val paraStringRectanguloBase = entradaBase.text.toString().fullTrim()
                    val base:Double = paraStringRectanguloBase.toDouble()
                    fun String.fullTrim2() = trim().replace("Altura:", "")
                    val paraStringRectanguloAltura = entradaAltura.text.toString().fullTrim2()
                    val altura = paraStringRectanguloAltura.toDoubleOrNull()
                    if (base != null && altura != null) {
                        val area = calcularAreaRectangulo(base, altura)
                        actualizarTextViews("Base: $base", "", "", "", "Altura: $altura", "El área de la forma geométrica es: $area")
                        area
                    } else {
                        throw NumberFormatException()
                    }
                }

                FormaGeometrica.CIRCULO -> {
                    fun String.fullTrim() = trim().replace("Radio:", "")
                    val paraStringCirculo = entradaRadio.text.toString().fullTrim()
                    val radio = paraStringCirculo.toDoubleOrNull()
                    if (radio != null) {
                        val area = calcularAreaCirculo(radio)
                        actualizarTextViews("", "","Radio: $radio", "", "", "El área de la forma geométrica es: $area")
                        area
                    } else {
                        throw NumberFormatException()
                    }
                }

                FormaGeometrica.TRIANGULO -> {
                    fun String.fullTrim() = trim().replace("Base:", "")
                    val paraStringTrianguloBase = entradaBase.text.toString().fullTrim()
                    val base = paraStringTrianguloBase.toDoubleOrNull()
                    fun String.fullTrim2() = trim().replace("Altura:", "")
                    val paraStringTrianguloAltura = entradaAltura.text.toString().fullTrim2()
                    val altura = paraStringTrianguloAltura.toDoubleOrNull()
                    if (base != null && altura != null) {
                        val area = calcularAreaTriangulo(base, altura)
                        actualizarTextViews("Base: $base", "","", "", "Altura: $altura", "El área de la forma geométrica es: $area")
                        area
                    } else {
                        throw NumberFormatException()
                    }
                }

                FormaGeometrica.PENTAGONO -> {
                    fun String.fullTrim() = trim().replace("Base:", "")
                    val paraStringPentagonoBase = entradaBase.text.toString().fullTrim()
                    val base = paraStringPentagonoBase.toDoubleOrNull()
                    fun String.fullTrim2() = trim().replace("Apotema:", "")
                    val paraStringPentagonoApotema = entradaApotema.text.toString().fullTrim2()
                    val apotema = paraStringPentagonoApotema.toDoubleOrNull()
                    if (base != null && apotema != null) {
                        val area = calcularAreaPentagono(base, apotema)
                        actualizarTextViews("Base: $base", "Apotema: $apotema","", "", "", "El área de la forma geométrica es: $area")
                        area
                    } else {
                        throw NumberFormatException()
                    }
                }

                FormaGeometrica.HEXAGONO -> {
                    fun String.fullTrim() = trim().replace("Base:", "")
                    val paraStringHexagonoBase = entradaBase.text.toString().fullTrim()
                    val base = paraStringHexagonoBase.toDoubleOrNull()
                    fun String.fullTrim2() = trim().replace("Apotema:", "")
                    val paraStringHexagonoApotema = entradaApotema.text.toString().fullTrim2()
                    val apotema = paraStringHexagonoApotema.toDoubleOrNull()
                    if (base != null && apotema != null) {
                        val area = calcularAreaHexagono(base, apotema)
                        actualizarTextViews("Base: $base", "Apotema: $apotema","", "", "", "El área de la forma geométrica es: $area")
                        area
                    } else {
                        throw NumberFormatException()
                    }
                }

            }

            textViewResultado.text = "El área de la forma geométrica es: $resultado"
        } catch (e: NumberFormatException) {
            textViewResultado.text = "Error: Ingresa números válidos"
        } catch (e: IllegalArgumentException) {
            textViewResultado.text = "Forma geométrica no válida"
        }
    }



   /* if (resultado != null) {
        textViewResultado.text = "El área de la forma geométrica es: $resultado"
    } else {
        textViewResultado.text = "Hubo un error al calcular el área"
    }*/


    //funcuion para actualizar los TextViews con la informacion que se necesita
    private fun actualizarTextViews(valorBase: String,valorApotema: String,valorRadio: String,valorLado: String, valorAltura: String, valorResultado: String) {
        entradaBase.text = valorBase
        entradaLado.text = valorLado
        entradaRadio.text = valorRadio
        entradaApotema.text = valorApotema
        entradaAltura.text = valorAltura
        textViewResultado.text = valorResultado
    }





    //funciones para calcular el area de las fomras geometricas
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

    //funcion para borrar las entradas y reiniciar el TextView del resultado
    private fun borrarEntradas() {
        entradaLado.text = ""
        entradaBase.text = ""
        entradaAltura.text = ""
        entradaRadio.text = ""
        entradaApotema.text = ""
        textViewResultado.text = "El área de la forma geométrica es: "
    }

    private fun establecerListenerBotonBorrar() {
        botonBorrar.setOnClickListener {
            borrarEntradas()
        }
    }
}
