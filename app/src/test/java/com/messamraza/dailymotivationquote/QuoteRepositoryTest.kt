package com.messamraza.dailymotivationquote

import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class QuoteRepositoryTest {

    @Mock
    private lateinit var quoteDao: QuoteDao

    private lateinit var repository: QuoteRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = QuoteRepository(quoteDao)
    }

    @Test
    fun `getRandomQuote returns quote from dao`() = runTest {
        // Given
        val expectedQuote = Quote(1, "Test quote", "Test Author")
        `when`(quoteDao.getRandomQuote()).thenReturn(expectedQuote)

        // When
        val result = repository.getRandomQuote()

        // Then
        assertEquals(expectedQuote, result)
        verify(quoteDao).getRandomQuote()
    }

    @Test
    fun `getRandomQuote returns null when dao returns null`() = runTest {
        // Given
        `when`(quoteDao.getRandomQuote()).thenReturn(null)

        // When
        val result = repository.getRandomQuote()

        // Then
        assertNull(result)
        verify(quoteDao).getRandomQuote()
    }

    @Test
    fun `getTodaysQuote returns quote based on day of year`() = runTest {
        // Given
        val expectedQuote = Quote(1, "Today's quote", "Today's Author")
        val quoteCount = 25
        `when`(quoteDao.getQuoteCount()).thenReturn(quoteCount)
        `when`(quoteDao.getQuoteById(anyInt())).thenReturn(expectedQuote)

        // When
        val result = repository.getTodaysQuote()

        // Then
        assertEquals(expectedQuote, result)
        verify(quoteDao).getQuoteCount()
        verify(quoteDao).getQuoteById(anyInt())
    }

    @Test
    fun `getTodaysQuote returns random quote when getQuoteById returns null`() = runTest {
        // Given
        val randomQuote = Quote(2, "Random quote", "Random Author")
        val quoteCount = 25
        `when`(quoteDao.getQuoteCount()).thenReturn(quoteCount)
        `when`(quoteDao.getQuoteById(anyInt())).thenReturn(null)
        `when`(quoteDao.getRandomQuote()).thenReturn(randomQuote)

        // When
        val result = repository.getTodaysQuote()

        // Then
        assertEquals(randomQuote, result)
        verify(quoteDao).getQuoteCount()
        verify(quoteDao).getQuoteById(anyInt())
        verify(quoteDao).getRandomQuote()
    }

    @Test
    fun `getTodaysQuote returns null when no quotes available`() = runTest {
        // Given
        `when`(quoteDao.getQuoteCount()).thenReturn(0)

        // When
        val result = repository.getTodaysQuote()

        // Then
        assertNull(result)
        verify(quoteDao).getQuoteCount()
        verify(quoteDao, never()).getQuoteById(anyInt())
    }

    @Test
    fun `getAllQuotes returns all quotes from dao`() = runTest {
        // Given
        val expectedQuotes = listOf(
            Quote(1, "Quote 1", "Author 1"),
            Quote(2, "Quote 2", "Author 2")
        )
        `when`(quoteDao.getAllQuotes()).thenReturn(expectedQuotes)

        // When
        val result = repository.getAllQuotes()

        // Then
        assertEquals(expectedQuotes, result)
        verify(quoteDao).getAllQuotes()
    }
}
