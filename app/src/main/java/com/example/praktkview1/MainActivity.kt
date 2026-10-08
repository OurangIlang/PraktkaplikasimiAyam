package com.example.praktkview1

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ListView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val daftarRekapPesanan = ArrayList<String>()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi Widget
        val etNama = findViewById<EditText>(R.id.etNama)
        val spVarian = findViewById<Spinner>(R.id.spVarian)
        val rgPorsi = findViewById<RadioGroup>(R.id.rgPorsi)
        val cbPangsit = findViewById<CheckBox>(R.id.cbPangsit)
        val cbBakso = findViewById<CheckBox>(R.id.cbBakso)
        val cbCeker = findViewById<CheckBox>(R.id.cbCeker)
        val btnProses = findViewById<Button>(R.id.btnProses)
        val lvRekap = findViewById<ListView>(R.id.lvRekap)

        // Adapter untuk menghubungkan list data ke ListView
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            daftarRekapPesanan
        )
        lvRekap.adapter = adapter

        // Listener Tombol
        btnProses.setOnClickListener {
            val nama = etNama.text.toString().trim()

            if (nama.isEmpty()) {
                etNama.error = "Nama pemesan tidak boleh kosong!"
                return@setOnClickListener
            }

            val varian = spVarian.selectedItem.toString()

            val selectedPorsiId = rgPorsi.checkedRadioButtonId
            val rbSelectedPorsi = findViewById<RadioButton>(selectedPorsiId)
            val porsi = rbSelectedPorsi?.text?.toString() ?: "Reguler"

            val listTopping = ArrayList<String>()
            if (cbPangsit.isChecked) listTopping.add("Pangsit Goreng")
            if (cbBakso.isChecked) listTopping.add("Bakso Sapi")
            if (cbCeker.isChecked) listTopping.add("Ceker Ayam")

            val toppingText = if (listTopping.isNotEmpty()) {
                listTopping.joinToString(", ")
            } else {
                "Tanpa Topping"
            }

            val ringkasan = "Pemesan: $nama\n" +
                    "Varian: $varian\n" +
                    "Porsi: $porsi\n" +
                    "Topping: $toppingText"

            daftarRekapPesanan.add(ringkasan)
            adapter.notifyDataSetChanged()

            Toast.makeText(this, "Pesanan berhasil ditambahkan", Toast.LENGTH_SHORT).show()

            // Reset field
            etNama.text.clear()
            cbPangsit.isChecked = false
            cbBakso.isChecked = false
            cbCeker.isChecked = false
            rgPorsi.check(R.id.rbReguler)
            spVarian.setSelection(0)
        }
    }
}