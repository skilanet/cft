package com.example.cft

data class Person(
    val gender: String,
    val name: Name,
    val location: Location,
    val email: String,
    val login: Login,
    val dob: Dob,
    val registered: Registered,
    val phone: String,
    val cell: String,
    val picture: Picture,
    val nat: String
) {
    val fio: String
        get() = "${name.title} ${name.first} ${name.last}"
    val address: String
        get() = "${location.street.name} ${location.street.number}"
    val timeZone: String
        get() = "${location.timezone.description} (${location.timezone.offset})"
    val dateOfBirth: String
        get() = dob.date.dropLast(14)
    val dateOfRegistration: String
        get() = registered.date.dropLast(14)
}

data class Name(
    val first: String,
    val last: String,
    val title: String
)

data class Location(
    val city: String,
    val coordinates: Coordinates,
    val country: String,
    val postcode: String,
    val state: String,
    val street: Street,
    val timezone: Timezone
) {
    data class Coordinates(
        val latitude: String,
        val longitude: String
    )

    data class Street(
        val name: String,
        val number: Int
    )

    data class Timezone(
        val description: String,
        val offset: String
    )
}

data class Login(
    val md5: String,
    val password: String,
    val salt: String,
    val sha1: String,
    val sha256: String,
    val username: String,
    val uuid: String
)

data class Dob(
    val age: Int,
    val date: String
)

data class Registered(
    val age: Int,
    val date: String
)

data class Picture(
    val large: String,
    val medium: String,
    val thumbnail: String
)