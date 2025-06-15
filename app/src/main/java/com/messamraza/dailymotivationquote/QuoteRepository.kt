package com.messamraza.dailymotivationquote

class QuoteRepository(private val quoteDao: QuoteDao) {

    suspend fun getRandomQuote(): Quote? {
        return quoteDao.getRandomQuote()
    }

    suspend fun getTodaysQuote(): Quote? {
        // Get today's quote based on day of year to ensure same quote per day
        val dayOfYear = java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_YEAR)
        val quoteCount = quoteDao.getQuoteCount()

        return if (quoteCount > 0) {
            val quoteId = (dayOfYear % quoteCount) + 1
            quoteDao.getQuoteById(quoteId) ?: quoteDao.getRandomQuote()
        } else {
            null
        }
    }

    suspend fun getAllQuotes(): List<Quote> {
        return quoteDao.getAllQuotes()
    }
}
