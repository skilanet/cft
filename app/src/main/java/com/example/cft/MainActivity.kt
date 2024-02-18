package com.example.cft

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cft.databinding.ActivityMainBinding
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val INTENT_KEY = "INTENT_KEY"
private const val SHARED_PREFS_NAME = "MyPrefsFile"
private const val SHARED_PREFS_KEY = "MyPrefsKey"

class MainActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private val baseUrl = "https://randomuser.me/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service = retrofit.create(PersonApi::class.java)
    private val adapter = Adapter(this)
    private val persons = ArrayList<Person>()
    private val sharedPrefs by lazy {
        getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvPeople.adapter = adapter
        persons.addAll(fromJsonToPersons(sharedPrefs.getString(SHARED_PREFS_KEY, "")!!))
        if (persons.isEmpty()) {
            binding.rvPeople.visibility = View.GONE
            binding.llEmptyList.visibility = View.VISIBLE
            binding.llSomethingWentWrong.visibility = View.GONE
        }
        adapter.persons = persons
        binding.rvPeople.layoutManager = LinearLayoutManager(this)

        fun sendRequest() = service.getPerson(100)
            .enqueue(object : Callback<PersonResponse> {
                override fun onResponse(
                    call: Call<PersonResponse>,
                    response: Response<PersonResponse>
                ) {
                    persons.clear()
                    if (response.code() == 200) {
                        if (response.body()!!.results.isNotEmpty()) {
                            binding.llEmptyList.visibility = View.GONE
                            binding.llSomethingWentWrong.visibility = View.GONE
                            binding.rvPeople.visibility = View.VISIBLE
                            persons.addAll(response.body()?.results!!)
                            adapter.notifyItemRangeChanged(0, persons.size)
                            sharedPrefs.edit()
                                .putString(SHARED_PREFS_KEY, fromPersonsToJson(Persons(persons)))
                                .apply()
                        } else {
                            binding.rvPeople.visibility = View.GONE
                            binding.llEmptyList.visibility = View.VISIBLE
                            binding.llSomethingWentWrong.visibility = View.GONE
                        }
                    } else {
                        binding.rvPeople.visibility = View.GONE
                        binding.llSomethingWentWrong.visibility = View.VISIBLE
                        binding.llEmptyList.visibility = View.GONE
                    }

                }

                override fun onFailure(call: Call<PersonResponse>, t: Throwable) {
                    binding.rvPeople.visibility = View.GONE
                    binding.llSomethingWentWrong.visibility = View.VISIBLE
                    binding.llEmptyList.visibility = View.GONE
                }
            })

        binding.ivUpdate.setOnClickListener {
            sendRequest()

        }

        binding.btnUpdateEmpty.setOnClickListener {
            binding.llEmptyList.visibility = View.GONE
            binding.rvPeople.visibility = View.GONE
            binding.llSomethingWentWrong.visibility = View.GONE
            sendRequest()
        }

        binding.btnUpdateSomething.setOnClickListener {
            binding.llEmptyList.visibility = View.GONE
            binding.rvPeople.visibility = View.GONE
            binding.llSomethingWentWrong.visibility = View.GONE
            sendRequest()
        }

    }

    override fun onItemClick(position: Int) {
        Intent(this, PersonFullInfo::class.java).apply {
            putExtra(INTENT_KEY, fromPersonToJson(persons[position]))
            startActivity(this)
        }
    }

    private fun fromPersonToJson(person: Person): String = Gson().toJson(person)
    private fun fromPersonsToJson(persons: Persons): String = Gson().toJson(persons)
    private fun fromJsonToPersons(json: String): ArrayList<Person> {
        return if (json.isEmpty()) {
            ArrayList()
        } else {
            Gson().fromJson(json, Persons::class.java).persons
        }
    }
}

data class Persons(val persons: ArrayList<Person>)