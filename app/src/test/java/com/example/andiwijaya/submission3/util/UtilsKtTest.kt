package com.example.andiwijaya.submission3.util

import org.junit.Assert.assertEquals
import org.junit.Test

class UtilsKtTest {

    @Test
    fun replaceSemicolonWithNewRow() {
        assertEquals(
            "AA\nBB\nCC",
            com.example.andiwijaya.submission3.util.replaceSemicolonWithNewRow("AA;BB;CC")
        )
    }
}