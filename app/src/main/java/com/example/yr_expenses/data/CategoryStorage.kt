/*
package com.example.yr_expenses.data

import android.content.Context
import org.json.JSONArray
import java.io.File

object CategoryStorage {

    private const val FILE_NAME = "categories.json"

    private val defaultCategories = listOf(
        "Food",
        "Groceries",
        "Transport",
        "Sports",
        "Beauty",
        "Uncategorized"
    )

    fun load(context: Context): List<String> {
        val file = File(context.filesDir, FILE_NAME)

        if (!file.exists()) {
            save(context, defaultCategories)
            return defaultCategories
        }

        val arr = JSONArray(file.readText())
        return (0 until arr.length()).map { arr.getString(it) }
    }

    fun save(context: Context, categories: List<String>) {
        val arr = JSONArray()
        categories.forEach { arr.put(it) }

        File(context.filesDir, FILE_NAME).writeText(arr.toString())
    }

    fun add(context: Context, newCategory: String) {
        val existing = load(context).toMutableList()

        if (!existing.contains(newCategory)) {
            existing.add(newCategory)
            save(context, existing)
        }
    }
}
*/
/*
package com.example.yr_expenses.data

import android.content.Context
import org.json.JSONArray
import java.io.File

object CategoryStorage {

    private const val FILE_NAME = "categories.json"

    private val defaultCategories = listOf(
        "Food",
        "Ordering In",
        "Eating Out",
        "Instamart",
        "Produce",
        "Groceries",
        "Transport",
        "Sports",
        "Beauty",
        "Sin",
        "Misc",
        "Uncategorized"
    )

    fun load(context: Context): List<String> {

        val file = File(context.filesDir, FILE_NAME)

        if (!file.exists()) {
            save(context, defaultCategories)
            return defaultCategories
        }

        val arr = JSONArray(file.readText())

        return (0 until arr.length()).map {
            arr.getString(it)
        }
    }

    fun save(context: Context, categories: List<String>) {

        val arr = JSONArray()

        categories.forEach {
            arr.put(it)
        }

        File(context.filesDir, FILE_NAME).writeText(arr.toString())
    }

    fun add(context: Context, newCategory: String) {

        val existing = load(context).toMutableList()

        if (!existing.contains(newCategory)) {

            existing.add(newCategory)

            save(context, existing)
        }
    }
}
*/

package com.example.yr_expenses.data

import android.content.Context
import org.json.JSONArray
import java.io.File

object CategoryStorage {

    private const val FILE_NAME = "categories.json"

    private val defaultCategories = listOf(
        "Food",
        "Ordering In",
        "Eating Out",
        "Instamart",
        "Produce",
        "Groceries",
        "Transport",
        "Sports",
        "Beauty",
        "Sin",
        "Misc",
        "Petrol",
        "Uncategorized"
    )

    fun load(context: Context): List<String> {

        val file = File(context.filesDir, FILE_NAME)

        if (!file.exists()) {
            save(context, defaultCategories)
            return defaultCategories
        }

        val arr = JSONArray(file.readText())

        return (0 until arr.length()).map {
            arr.getString(it)
        }
    }

    fun save(context: Context, categories: List<String>) {

        val arr = JSONArray()

        categories.forEach {
            arr.put(it)
        }

        File(context.filesDir, FILE_NAME).writeText(arr.toString())
    }

    fun add(context: Context, newCategory: String) {

        val existing = load(context).toMutableList()

        if (!existing.contains(newCategory)) {

            existing.add(newCategory)

            save(context, existing)
        }
    }

    fun remove(context: Context, category: String) {

        // Protect Uncategorized
        if (category == "Uncategorized") return

        val existing = load(context).toMutableList()

        existing.remove(category)

        save(context, existing)
    }
}