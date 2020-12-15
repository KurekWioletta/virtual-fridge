package com.virtualfridge.virtualfridge.utils

import org.abstractj.kalium.NaCl
import org.abstractj.kalium.crypto.Password
import org.abstractj.kalium.encoders.Encoder.HEX

fun String.hash(): String = Password().hash(
        /*password =*/ this.toByteArray(),
        /*encoder =*/ HEX,
        /*salt =*/ "[<~A 32-bytes salt for scrypt~>]".toByteArray(),
        /*opslimit =*/ NaCl.Sodium.CRYPTO_PWHASH_SCRYPTSALSA208SHA256_OPSLIMIT_INTERACTIVE,
        /*memlimit =*/ NaCl.Sodium.CRYPTO_PWHASH_SCRYPTSALSA208SHA256_MEMLIMIT_INTERACTIVE.toLong()
)