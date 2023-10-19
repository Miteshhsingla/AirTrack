package com.example.test

data class AirQualityResponse(
    val coord: Coord,
    val list: List<AQIData>
)

data class Coord(
    val lon: Double,
    val lat: Double
)

data class AQIData(
    val main: AQIMain,
    val components: Components,
    val dt: Long
)

data class AQIMain(
    val aqi: Int
)

data class Components(
    val co: Double,
    val no: Double,
    val no2: Double,
    val o3: Double,
    val so2: Double,
    val pm2_5: Double,
    val pm10: Double,
    val nh3: Double
)
