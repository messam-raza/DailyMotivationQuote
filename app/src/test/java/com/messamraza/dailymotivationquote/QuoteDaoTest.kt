package com.messamraza.dailymotivationquote

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class QuoteDaoTest {

    private lateinit var database: QuoteDatabase
    private lateinit var quoteDao: QuoteDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            QuoteDatabase::class.java
        ).allowMainThreadQueries().build()

        quoteDao = database.quoteDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertAndGetQuotes() = runTest {
        // Given
        val quotes = listOf(
            Quote(text = "Test quote 1", author = "Author 1"),
            Quote(text = "Test quote 2", author = "Author 2"),
            Quote(text = "Test quote 3", author = "Author 3")
        )

        // When
        quoteDao.insertQuotes(quotes)
        val allQuotes = quoteDao.getAllQuotes()

        // Then
        assertEquals(3, allQuotes.size)
        assertTrue(allQuotes.any { it.text == "Test quote 1" && it.author == "Author 1" })
        assertTrue(allQuotes.any { it.text == "Test quote 2" && it.author == "Author 2" })
        assertTrue(allQuotes.any { it.text == "Test quote 3" && it.author == "Author 3" })
    }

    @Test
    fun getQuoteCount() = runTest {
        // Given
        val quotes = listOf(
            Quote(text = "Quote 1", author = "Author 1"),
            Quote(text = "Quote 2", author = "Author 2")
        )

        // When
        quoteDao.insertQuotes(quotes)
        val count = quoteDao.getQuoteCount()

        // Then
        assertEquals(2, count)
    }

    @Test
    fun getQuoteById() = runTest {
        // Given
        val quotes = listOf(
            Quote(text = "First quote", author = "First Author"),
            Quote(text = "Second quote", author = "Second Author")
        )
        quoteDao.insertQuotes(quotes)

        // When
        val quote = quoteDao.getQuoteById(1)

        // Then
        assertNotNull(quote)
        assertEquals("First quote", quote?.text)
        assertEquals("First Author", quote?.author)
    }

    @Test
    fun getQuoteByIdNotFound() = runTest {
        // When
        val quote = quoteDao.getQuoteById(999)

        // Then
        assertNull(quote)
    }

    @Test
    fun getRandomQuote() = runTest {
        // Given
        val quotes = listOf(
            Quote(text = "Random quote 1", author = "Random Author 1"),
            Quote(text = "Random quote 2", author = "Random Author 2"),
            Quote(text = "Random quote 3", author = "Random Author 3")
        )
        quoteDao.insertQuotes(quotes)

        // When
        val randomQuote = quoteDao.getRandomQuote()

        // Then
        assertNotNull(randomQuote)
        assertTrue(quotes.any { it.text == randomQuote?.text && it.author == randomQuote.author })
    }

    @Test
    fun getRandomQuoteFromEmptyDatabase() = runTest {
        // When
        val randomQuote = quoteDao.getRandomQuote()

        // Then
        assertNull(randomQuote)
    }

    @Test
    fun insertDuplicateQuotesIgnored() = runTest {
        // Given
        val originalQuotes = listOf(
            Quote(id = 1, text = "Original quote", author = "Original Author")
        )
        val duplicateQuotes = listOf(
            Quote(id = 1, text = "Duplicate quote", author = "Duplicate Author")
        )

        // When
        quoteDao.insertQuotes(originalQuotes)
        quoteDao.insertQuotes(duplicateQuotes) // Should be ignored due to OnConflictStrategy.IGNORE

        val allQuotes = quoteDao.getAllQuotes()

        // Then
        assertEquals(1, allQuotes.size)
        assertEquals("Original quote", allQuotes[0].text)
        assertEquals("Original Author", allQuotes[0].author)
    }
}
