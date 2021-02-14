package wsr.base.domain

data class TestResponse(
    val disclaimer: String,
    val date: String,
    val timestamp: Long,
    val base: String,
    val rates: Map<String, Double>
)