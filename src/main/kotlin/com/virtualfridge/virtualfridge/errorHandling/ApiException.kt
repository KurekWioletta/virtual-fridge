package com.virtualfridge.virtualfridge.errorHandling

class ApiException(
        val errorMessage: String
) : Exception() {
}