package uk.jacobw.recipe.favourites.data

internal fun String.sha256Hex(): String =
    encodeToByteArray().sha256().toHexString()

private fun ByteArray.sha256(): ByteArray {
    val messageBitLength = size.toLong() * 8L
    val paddingLength = ((56 - ((size + 1) % 64)) + 64) % 64
    val padded = ByteArray(size + 1 + paddingLength + 8)

    copyInto(destination = padded)
    padded[size] = 0x80.toByte()

    for (index in 0 until 8) {
        val shift = index * 8
        padded[padded.lastIndex - index] = ((messageBitLength ushr shift) and 0xff).toByte()
    }

    var h0 = 0x6a09e667
    var h1 = 0xbb67ae85.toInt()
    var h2 = 0x3c6ef372
    var h3 = 0xa54ff53a.toInt()
    var h4 = 0x510e527f
    var h5 = 0x9b05688c.toInt()
    var h6 = 0x1f83d9ab
    var h7 = 0x5be0cd19

    val schedule = IntArray(64)

    for (chunkStart in padded.indices step 64) {
        for (index in 0 until 16) {
            val offset = chunkStart + (index * 4)
            schedule[index] =
                ((padded[offset].toInt() and 0xff) shl 24) or
                    ((padded[offset + 1].toInt() and 0xff) shl 16) or
                    ((padded[offset + 2].toInt() and 0xff) shl 8) or
                    (padded[offset + 3].toInt() and 0xff)
        }

        for (index in 16 until 64) {
            val s0 = schedule[index - 15].smallSigma0()
            val s1 = schedule[index - 2].smallSigma1()
            schedule[index] = schedule[index - 16] + s0 + schedule[index - 7] + s1
        }

        var a = h0
        var b = h1
        var c = h2
        var d = h3
        var e = h4
        var f = h5
        var g = h6
        var h = h7

        for (index in 0 until 64) {
            val temp1 = h + e.bigSigma1() + e.choose(f, g) + ROUND_CONSTANTS[index] + schedule[index]
            val temp2 = a.bigSigma0() + a.majority(b, c)

            h = g
            g = f
            f = e
            e = d + temp1
            d = c
            c = b
            b = a
            a = temp1 + temp2
        }

        h0 += a
        h1 += b
        h2 += c
        h3 += d
        h4 += e
        h5 += f
        h6 += g
        h7 += h
    }

    return byteArrayOf(
        *h0.toBigEndianBytes(),
        *h1.toBigEndianBytes(),
        *h2.toBigEndianBytes(),
        *h3.toBigEndianBytes(),
        *h4.toBigEndianBytes(),
        *h5.toBigEndianBytes(),
        *h6.toBigEndianBytes(),
        *h7.toBigEndianBytes(),
    )
}

private fun Int.rotate(bits: Int): Int =
    (this ushr bits) or (this shl (32 - bits))

private fun Int.choose(y: Int, z: Int): Int =
    (this and y) xor (this.inv() and z)

private fun Int.majority(y: Int, z: Int): Int =
    (this and y) xor (this and z) xor (y and z)

private fun Int.bigSigma0(): Int =
    rotate(2) xor rotate(13) xor rotate(22)

private fun Int.bigSigma1(): Int =
    rotate(6) xor rotate(11) xor rotate(25)

private fun Int.smallSigma0(): Int =
    rotate(7) xor rotate(18) xor (this ushr 3)

private fun Int.smallSigma1(): Int =
    rotate(17) xor rotate(19) xor (this ushr 10)

private fun Int.toBigEndianBytes(): ByteArray =
    byteArrayOf(
        ((this ushr 24) and 0xff).toByte(),
        ((this ushr 16) and 0xff).toByte(),
        ((this ushr 8) and 0xff).toByte(),
        (this and 0xff).toByte(),
    )

private fun ByteArray.toHexString(): String {
    val hexChars = CharArray(size * 2)

    forEachIndexed { index, byte ->
        val value = byte.toInt() and 0xff
        hexChars[index * 2] = HEX_DIGITS[value ushr 4]
        hexChars[(index * 2) + 1] = HEX_DIGITS[value and 0x0f]
    }

    return hexChars.concatToString()
}

private val ROUND_CONSTANTS =
    intArrayOf(
        0x428a2f98,
        0x71374491,
        0xb5c0fbcf.toInt(),
        0xe9b5dba5.toInt(),
        0x3956c25b,
        0x59f111f1,
        0x923f82a4.toInt(),
        0xab1c5ed5.toInt(),
        0xd807aa98.toInt(),
        0x12835b01,
        0x243185be,
        0x550c7dc3,
        0x72be5d74,
        0x80deb1fe.toInt(),
        0x9bdc06a7.toInt(),
        0xc19bf174.toInt(),
        0xe49b69c1.toInt(),
        0xefbe4786.toInt(),
        0x0fc19dc6,
        0x240ca1cc,
        0x2de92c6f,
        0x4a7484aa,
        0x5cb0a9dc,
        0x76f988da,
        0x983e5152.toInt(),
        0xa831c66d.toInt(),
        0xb00327c8.toInt(),
        0xbf597fc7.toInt(),
        0xc6e00bf3.toInt(),
        0xd5a79147.toInt(),
        0x06ca6351,
        0x14292967,
        0x27b70a85,
        0x2e1b2138,
        0x4d2c6dfc,
        0x53380d13,
        0x650a7354,
        0x766a0abb,
        0x81c2c92e.toInt(),
        0x92722c85.toInt(),
        0xa2bfe8a1.toInt(),
        0xa81a664b.toInt(),
        0xc24b8b70.toInt(),
        0xc76c51a3.toInt(),
        0xd192e819.toInt(),
        0xd6990624.toInt(),
        0xf40e3585.toInt(),
        0x106aa070,
        0x19a4c116,
        0x1e376c08,
        0x2748774c,
        0x34b0bcb5,
        0x391c0cb3,
        0x4ed8aa4a,
        0x5b9cca4f,
        0x682e6ff3,
        0x748f82ee,
        0x78a5636f,
        0x84c87814.toInt(),
        0x8cc70208.toInt(),
        0x90befffa.toInt(),
        0xa4506ceb.toInt(),
        0xbef9a3f7.toInt(),
        0xc67178f2.toInt(),
    )

private val HEX_DIGITS = "0123456789abcdef".toCharArray()
