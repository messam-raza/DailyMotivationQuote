package com.messamraza.dailymotivationquote

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel: QuoteViewModel by viewModels {
        QuoteViewModelFactory((application as QuoteApplication).repository)
    }

    private lateinit var quoteText: MaterialTextView
    private lateinit var authorText: MaterialTextView
    private lateinit var newQuoteButton: MaterialButton
    private lateinit var shareButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupClickListeners()
        observeQuote()

        // Load today's quote
        viewModel.loadTodaysQuote()
    }

    private fun initViews() {
        quoteText = findViewById(R.id.quoteText)
        authorText = findViewById(R.id.authorText)
        newQuoteButton = findViewById(R.id.newQuoteButton)
        shareButton = findViewById(R.id.shareButton)
    }

    private fun setupClickListeners() {
        newQuoteButton.setOnClickListener {
            viewModel.loadRandomQuote()
        }

        shareButton.setOnClickListener {
            shareQuote()
        }
    }

    private fun observeQuote() {
        lifecycleScope.launch {
            viewModel.currentQuote.collect { quote ->
                quote?.let {
                    quoteText.text = "\"${it.text}\""
                    authorText.text = "- ${it.author}"
                }
            }
        }
    }

    private fun shareQuote() {
        val currentQuote = viewModel.currentQuote.value
        currentQuote?.let { quote ->
            val shareText = "\"${quote.text}\"\n\n- ${quote.author}\n\nShared from Daily Motivation App"
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, shareText)
            }
            startActivity(Intent.createChooser(shareIntent, "Share Quote"))
        }
    }
}
