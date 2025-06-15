package com.messamraza.dailymotivationquote.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QuoteDao {

    @Query("SELECT * FROM quotes ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomQuote(): Quote?

    @Query("SELECT * FROM quotes WHERE id = :id")
    suspend fun getQuoteById(id: Int): Quote?

    @Query("SELECT COUNT(*) FROM quotes")
    suspend fun getQuoteCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertQuotes(quotes: List<Quote>)

    @Query("SELECT * FROM quotes")
    suspend fun getAllQuotes(): List<Quote>
}
