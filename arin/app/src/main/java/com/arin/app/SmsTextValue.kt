package com.arin.app

import java.io.BufferedReader
import java.io.FileReader

class SmsTextValue {
    var SmsText :String = ""
    fun getText() {
        val filePath = "example.txt" // Replace with the path to your text file
        var reader: BufferedReader? = null

        try {
            reader = BufferedReader(FileReader(filePath))
            var line: String?

            while (reader.readLine().also { line = it } != null) {
                // Process each line
                println(line)
            }
        } catch (e: Exception) {
            println("An error occurred: ${e.message}")
        } finally {
            try {
                reader?.close()
            } catch (e: Exception) {
                println("An error occurred while closing the file: ${e.message}")
            }
        }
    }
}