package com.messamraza.dailymotivationquote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.messamraza.dailymotivationquote.data.Quote
import com.messamraza.dailymotivationquote.repository.QuoteRepository
import com.messamraza.dailymotivationquote.viewmodel.QuoteViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class QuoteViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: QuoteRepository

    private lateinit var viewModel: QuoteViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = QuoteViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadTodaysQuote updates currentQuote with today's quote`() = runTest {
        // Given
        val expectedQuote = Quote(1, "Today's motivation", "Motivational Author")
        `when`(repository.getTodaysQuote()).thenReturn(expectedQuote)

        // When
        viewModel.loadTodaysQuote()

        // Then
        val result = viewModel.currentQuote.first()
        assertEquals(expectedQuote, result)
        verify(repository).getTodaysQuote()
    }

    @Test
    fun `loadTodaysQuote updates currentQuote with null when no quote available`() = runTest {
        // Given
        `when`(repository.getTodaysQuote()).thenReturn(null)

        // When
        viewModel.loadTodaysQuote()

        // Then
        val result = viewModel.currentQuote.first()
        assertNull(result)
        verify(repository).getTodaysQuote()
    }

    @Test
    fun `loadRandomQuote updates currentQuote with random quote`() = runTest {
        // Given
        val expectedQuote = Quote(2, "Random motivation", "Random Author")
        `when`(repository.getRandomQuote()).thenReturn(expectedQuote)

        // When
        viewModel.loadRandomQuote()

        // Then
        val result = viewModel.currentQuote.first()
        assertEquals(expectedQuote, result)
        verify(repository).getRandomQuote()
    }

    @Test
    fun `loadRandomQuote updates currentQuote with null when no quote available`() = runTest {
        // Given
        `when`(repository.getRandomQuote()).thenReturn(null)

        // When
        viewModel.loadRandomQuote()

        // Then
        val result = viewModel.currentQuote.first()
        assertNull(result)
        verify(repository).getRandomQuote()
    }

    @Test
    fun `currentQuote initial value is null`() = runTest {
        // When
        val result = viewModel.currentQuote.first()

        // Then
        assertNull(result)
    }
}
