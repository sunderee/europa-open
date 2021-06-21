package com.peteralexbizjak.europaopen.utils

import com.peteralexbizjak.europaopen.utils.json.deserialize
import com.peteralexbizjak.europaopen.utils.json.serialize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.junit.Assert.assertTrue
import org.junit.Test

internal class KotlinJSONUnitTest {
    private val test = "{\"userId\":1,\"id\":1,\"title\":\"test\",\"completed\":false}"

    @Serializable
    private data class TodoModel(
        @SerialName("userId")
        val userID: Int,

        @SerialName("id")
        val todoID: Int,

        @SerialName("title")
        val title: String,

        @SerialName("completed")
        val isCompleted: Boolean
    )

    @Test
    fun testSerialization() {
        val todo = TodoModel(0, 0, "null", true).serialize()
        println("Serialized TODO: $todo")
        assertTrue(todo.isNotEmpty())
    }

    @Test
    fun testDeserialization() {
        val todo = test.deserialize<TodoModel>()
        println("Deserialized TODO: $todo")
        assertTrue(todo.title == "test")
    }
}