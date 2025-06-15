package com.messamraza.dailymotivationquote

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Quote::class],
    version = 1,
    exportSchema = false
)
abstract class QuoteDatabase : RoomDatabase() {

    abstract fun quoteDao(): QuoteDao

    companion object {
        @Volatile
        private var INSTANCE: QuoteDatabase? = null

        fun getDatabase(context: Context): QuoteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    QuoteDatabase::class.java,
                    "quote_database"
                )
                    .addCallback(QuoteDatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class QuoteDatabaseCallback : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        populateDatabase(database.quoteDao())
                    }
                }
            }
        }

        private suspend fun populateDatabase(quoteDao: QuoteDao) {
            val quotes = listOf(
                Quote(text = "The only way to do great work is to love what you do.", author = "Steve Jobs"),
                Quote(text = "Life is what happens to you while you're busy making other plans.", author = "John Lennon"),
                Quote(text = "The future belongs to those who believe in the beauty of their dreams.", author = "Eleanor Roosevelt"),
                Quote(text = "It is during our darkest moments that we must focus to see the light.", author = "Aristotle"),
                Quote(text = "The only impossible journey is the one you never begin.", author = "Tony Robbins"),
                Quote(text = "In the end, we will remember not the words of our enemies, but the silence of our friends.", author = "Martin Luther King Jr."),
                Quote(text = "The purpose of our lives is to be happy.", author = "Dalai Lama"),
                Quote(text = "Life is really simple, but we insist on making it complicated.", author = "Confucius"),
                Quote(text = "The way to get started is to quit talking and begin doing.", author = "Walt Disney"),
                Quote(text = "Don't let yesterday take up too much of today.", author = "Will Rogers"),
                Quote(text = "You learn more from failure than from success. Don't let it stop you. Failure builds character.", author = "Unknown"),
                Quote(text = "It's not whether you get knocked down, it's whether you get up.", author = "Vince Lombardi"),
                Quote(text = "If you are working on something that you really care about, you don't have to be pushed. The vision pulls you.", author = "Steve Jobs"),
                Quote(text = "People who are crazy enough to think they can change the world, are the ones who do.", author = "Rob Siltanen"),
                Quote(text = "Failure will never overtake me if my determination to succeed is strong enough.", author = "Og Mandino"),
                Quote(text = "Entrepreneurs are great at dealing with uncertainty and also very good at minimizing risk. That's the classic entrepreneur.", author = "Mohnish Pabrai"),
                Quote(text = "We may encounter many defeats but we must not be defeated.", author = "Maya Angelou"),
                Quote(text = "Knowing is not enough; we must apply. Wishing is not enough; we must do.", author = "Johann Wolfgang von Goethe"),
                Quote(text = "Imagine your life is perfect in every respect; what would it look like?", author = "Brian Tracy"),
                Quote(text = "We generate fears while we sit. We overcome them by action.", author = "Dr. Henry Link"),
                Quote(text = "Whether you think you can or think you can't, you're right.", author = "Henry Ford"),
                Quote(text = "Security is mostly a superstition. Life is either a daring adventure or nothing.", author = "Helen Keller"),
                Quote(text = "The man who has confidence in himself gains the confidence of others.", author = "Hasidic Proverb"),
                Quote(text = "The only person you are destined to become is the person you decide to be.", author = "Ralph Waldo Emerson"),
                Quote(text = "Go confidently in the direction of your dreams. Live the life you have imagined.", author = "Henry David Thoreau")
            )
            quoteDao.insertQuotes(quotes)
        }
    }
}
