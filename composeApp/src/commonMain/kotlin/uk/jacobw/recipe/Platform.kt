package uk.jacobw.recipe

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
