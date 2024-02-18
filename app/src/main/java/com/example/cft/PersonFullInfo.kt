package com.example.cft

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.cft.databinding.ActivityPersonFullInfoBinding
import com.google.gson.Gson

class PersonFullInfo : AppCompatActivity() {

    private lateinit var binding: ActivityPersonFullInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonFullInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        val person = fromJsonToPerson(intent.getStringExtra(INTENT_KEY)!!)
        Glide.with(this)
            .load(person.picture.large)
            .centerCrop()
            .placeholder(R.drawable.placeholder)
            .into(binding.ivPersonPhoto)
        binding.tvFioInfo.text = person.fio
        binding.tvGenderInfo.text = person.gender
        binding.tvAgeInfo.text = person.dob.age.toString()
        binding.tvPhoneInfo.text = person.phone
        binding.tvPhoneInfo.setOnClickListener {
            Intent(Intent.ACTION_DIAL, Uri.parse("tel:${person.phone}")).apply {
                startActivity(this)
            }
        }
        binding.tvEmailInfo.text = person.email
        binding.tvEmailInfo.setOnClickListener {
            Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:${person.email}")).apply {
                startActivity(this)
            }
        }
        binding.tvDobInfo.text = person.dateOfBirth
        binding.tvNatInfo.text = person.nat
        binding.tvAddressInfo.text = person.address
        binding.tvAddressInfo.setOnClickListener {
            Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=${person.address}")).apply {
                startActivity(this)
            }
        }
        binding.tvCityInfo.text = person.location.city
        binding.tvCityInfo.setOnClickListener {
            Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=${person.location.city}")).apply {
                startActivity(this)
            }
        }
        binding.tvStateInfo.text = person.location.state
        binding.tvStateInfo.setOnClickListener {
            Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=${person.location.state}")).apply {
                startActivity(this)
            }
        }
        binding.tvCountryInfo.text = person.location.country
        binding.tvCountryInfo.setOnClickListener {
            Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=${person.location.country}")).apply {
                startActivity(this)
            }
        }
        binding.tvLatitudeInfo.text = person.location.coordinates.latitude
        binding.tvLatitudeInfo.setOnClickListener {
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("geo:${person.location.coordinates.latitude},${person.location.coordinates.longitude}?q=${person.address}")
            ).apply {
                startActivity(this)
            }
        }
        binding.tvLongitudeInfo.text = person.location.coordinates.longitude
        binding.tvLongitudeInfo.setOnClickListener {
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("geo:${person.location.coordinates.latitude}, ${person.location.coordinates.longitude}?q=${person.address}")
            ).apply {
                startActivity(this)
            }
        }
        binding.tvPostcodeInfo.text = person.location.postcode
        binding.tvMobilePhoneInfo.text = person.cell
        binding.tvMobilePhoneInfo.setOnClickListener {
            Intent(Intent.ACTION_DIAL, Uri.parse("tel:${person.cell}")).apply {
                startActivity(this)
            }
        }
        binding.tvTimezoneInfo.text = person.timeZone
        binding.tvUsernameInfo.text = person.login.username
        binding.tvPasswordInfo.text = person.login.password
        binding.tvDorInfo.text = person.dateOfRegistration
        binding.tvUuidInfo.text = person.login.uuid
        binding.tvSaltInfo.text = person.login.salt
        binding.tvMd5Info.text = person.login.md5
        binding.tvSha1Info.text = person.login.sha1
        binding.tvSha256Info.text = person.login.sha256
    }


    private fun fromJsonToPerson(json: String): Person = Gson().fromJson(json, Person::class.java)
}