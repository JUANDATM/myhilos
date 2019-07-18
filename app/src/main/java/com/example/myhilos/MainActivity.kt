package com.example.myhilos

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import java.util.*
import android.os.AsyncTask
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import android.os.AsyncTask.execute
import android.util.Log
import com.example.myhilos.MainActivity.TareaSimple
import java.util.Random



class MainActivity : AppCompatActivity() {
    val generador = Random()
    var numerosAordenar = IntArray(40010)

    var SimpleTask: TareaSimple? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnCancelarHilo.setVisibility(View.INVISIBLE)
        tvEsperando.setVisibility(View.INVISIBLE)
        for (i in 0..40000) {
            numerosAordenar[i] = Math.abs(generador.nextInt() % 10000)
        }

    }

    fun ordena_clic(v: View) {
        tvEsperando.setText("Esperando...");
        tvEsperando.setVisibility(View.VISIBLE);
        Ordenar();
        tvEsperando.setText("Se ordenaron 40000 numeros " + numerosAordenar[1000] + ", " + numerosAordenar[1001]);
    }

    fun msg_clic(v: View) {
        Toast.makeText(this, "Prueba de segunda tarea", Toast.LENGTH_LONG).show();
    }

    fun cancelar_clic(v: View) {
        SimpleTask!!.cancel(true);
        ProgressBar1.setProgress(0);
    }

    fun ordenaHilo_clic(v: View) {
        SimpleTask = TareaSimple()
        SimpleTask!!.execute()
    }


    fun Ordenar() {
        var aux: Int
        for (i in 0..40000) {
            for (j in 0..40000 - 1) {
                if (numerosAordenar[j] > numerosAordenar[j + 1]) {
                    aux = numerosAordenar[j]
                    numerosAordenar[j] = numerosAordenar[j + 1]
                    numerosAordenar[j + 1] = aux
                }
            }
        }
    }


    inner class TareaSimple : AsyncTask<Void, Int, Void>() {

        override fun onPreExecute() {
            super.onPreExecute()
            btnCancelarHilo.setVisibility(View.VISIBLE)
            btnOrdenaHilo.setEnabled(false)

            tvEsperando.setVisibility(View.VISIBLE)
            ProgressBar1.setMax(100)
            ProgressBar1.setProgress(0)
        }

        override fun doInBackground(vararg params: Void?): Void? {
            var aux: Int
            var avance: Int = 0
            //Log.d("Zazueta","Ordenando")
            for (i in 0..10000) {
                for (j in 0..10000 - 1) {
                    if (numerosAordenar[j] > numerosAordenar[j + 1]) {
                        aux = numerosAordenar[j]
                        numerosAordenar[j] = numerosAordenar[j + 1]
                        numerosAordenar[j + 1] = aux
                    }
                }
                avance = ((i / 10000.0) * 100.0).toInt()
                publishProgress(avance)

                if (!isCancelled) {
                    break
                }

            }
            return null
        }

            override fun onPostExecute(aVoid: Void?) {
                super.onPostExecute(aVoid)
                tvEsperando.setText("Ordenamiento completado")
                btnOrdenaHilo.setEnabled(true)
                btnCancelarHilo.setVisibility(View.INVISIBLE)
            }

            override fun onProgressUpdate(vararg values: Int?) {
                super.onProgressUpdate(*values)
                if (values[0] != null) {
                    //Log.d("Zazueta","Ordenando" + values[0].toString())
                    ProgressBar1.setProgress(values[0]!!.toInt());
                    tvEsperando.setText(values[0].toString() + "%");
                }

            }

            override fun onCancelled() {
                super.onCancelled()
                tvEsperando.setText("Hilo Cancelado...")
                btnCancelarHilo.setVisibility(View.INVISIBLE)
                btnOrdenaHilo.setEnabled(true)
            }
        }
    }


