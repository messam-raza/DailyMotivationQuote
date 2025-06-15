package com.messamraza.dailymotivationquote

import android.app.Application
import com.messamraza.dailymotivationquote.data.QuoteDatabase
import com.messamraza.dailymotivationquote.repository.QuoteRepository

class QuoteApplication : Application() {

    val database by lazy { QuoteDatabase.getDatabase(this) }
    val repository by lazy { QuoteRepository(database.quoteDao()) }
}
