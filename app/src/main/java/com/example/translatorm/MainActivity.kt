package com.example.translatorm

import android.os.Bundle
//import android.os.Parcel
//import android.os.Parcelable
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
//import com.example.translatorm.R
import com.example.translatorm.databinding.ActivityMainBinding
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
//import kotlinx.coroutines.selects.select

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var items = arrayOf(
        "English",
        "Ukrainian",
        "Polish",
        "German",
        "Spanish",
        "Czech",
        "Slovak",
        "Romanian",
        "Hindi",
        "Hungarian"
    )

    private var conditions = DownloadConditions.Builder()
        .requireWifi()
        .build()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        val itemAdapter:ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, items)

        binding.languageFrom.setAdapter(itemAdapter)
        binding.languageTo.setAdapter(itemAdapter)


        binding.translate.setOnClickListener {
            val options = TranslatorOptions.Builder().setSourceLanguage(selectFrom())
                .setTargetLanguage(selectTo()).build()

            val translator = Translation.getClient(options)

            translator.downloadModelIfNeeded(conditions).addOnSuccessListener {
                translator.translate(binding.input.text.toString()).addOnSuccessListener {
                    binding.output.text = it
                }.addOnFailureListener{
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()

                }
            }.addOnFailureListener{
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }


        }
    }


    private fun selectFrom(): String {

        return when(binding.languageFrom.text.toString()){

            "" -> TranslateLanguage.ENGLISH

            "English" -> TranslateLanguage.ENGLISH
            "Ukrainian" -> TranslateLanguage.UKRAINIAN
            "Polish" -> TranslateLanguage.POLISH
            "German" -> TranslateLanguage.GERMAN
            "Spanish" -> TranslateLanguage.SPANISH
            "Czech" -> TranslateLanguage.CZECH
            "Slovak" -> TranslateLanguage.SLOVAK
            "Romanian" -> TranslateLanguage.ROMANIAN
            "Hindi" -> TranslateLanguage.HINDI
            "Hungarian" -> TranslateLanguage.HUNGARIAN
            else -> TranslateLanguage.ENGLISH
        }
    }

    private fun selectTo(): String {
        return when(binding.languageTo.text.toString()){

            "" -> TranslateLanguage.UKRAINIAN

            "English" -> TranslateLanguage.ENGLISH
            "Ukrainian" -> TranslateLanguage.UKRAINIAN
            "Polish" -> TranslateLanguage.POLISH
            "German" -> TranslateLanguage.GERMAN
            "Spanish" -> TranslateLanguage.SPANISH
            "Czech" -> TranslateLanguage.CZECH
            "Slovak" -> TranslateLanguage.SLOVAK
            "Romanian" -> TranslateLanguage.ROMANIAN
            "Hindi" -> TranslateLanguage.HINDI
            "Hungarian" -> TranslateLanguage.HUNGARIAN

            else -> TranslateLanguage.UKRAINIAN
        }
    }
}
