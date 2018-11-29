package com.example.andiwijaya.submission3.util

import org.junit.Test

import org.junit.Assert.*

class UtilsKtTest {

    @Test
    fun formatDate() {
        assertEquals(
            "Sun, 25 Nov 18",
            formatDate("2018-11-25")
        )
    }

    @Test
    fun replaceSemicolonWithNewRow() {
        assertEquals(
            "AA\nBB\nCC",
            com.example.andiwijaya.submission3.util.replaceSemicolonWithNewRow("AA;BB;CC")
        )
    }
}