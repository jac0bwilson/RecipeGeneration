package uk.jacobw.recipe.favourites.data

import okio.ByteString.Companion.encodeUtf8

internal fun String.sha256Hex(): String = encodeUtf8().sha256().hex()
