package com.messamraza.dailymotivationquote

import android.app.Application

class QuoteApplication : Application() {

    val database by lazy { QuoteDatabase.getDatabase(this) }
    val repository by lazy { QuoteRepository(database.quoteDao()) }
}
