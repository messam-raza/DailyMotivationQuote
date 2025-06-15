package com.messamraza.dailymotivationquote

import org.junit.Assert.*
import org.junit.Test

class QuoteTest {

    @Test
    fun `quote creation with all parameters`() {
        // Given
        val id = 1
        val text = "Test quote text"
        val author = "Test Author"

        // When
        val quote = Quote(id, text, author)

        // Then
        assertEquals(id, quote.id)
        assertEquals(text, quote.text)
        assertEquals(author, quote.author)
    }

    @Test
    fun `quote creation with default id`() {
        // Given
        val text = "Test quote text"
        val author = "Test Author"

        // When
        val quote = Quote(text = text, author = author)

        // Then
        assertEquals(0, quote.id) // Default value
        assertEquals(text, quote.text)
        assertEquals(author, quote.author)
    }

    @Test
    fun `quote equality test`() {
        // Given
        val quote1 = Quote(1, "Same text", "Same Author")
        val quote2 = Quote(1, "Same text", "Same Author")
        val quote3 = Quote(2, "Different text", "Different Author")

        // Then
        assertEquals(quote1, quote2)
        assertNotEquals(quote1, quote3)
    }

    @Test
    fun `quote toString contains all fields`() {
        // Given
        val quote = Quote(1, "Test quote", "Test Author")

        // When
        val result = quote.toString()

        // Then
        assertTrue(result.contains("1"))
        assertTrue(result.contains("Test quote"))
        assertTrue(result.contains("Test Author"))
    }
}
