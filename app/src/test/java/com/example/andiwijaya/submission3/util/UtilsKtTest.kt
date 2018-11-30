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

    @Test
    fun formatDateTimeToGMT() {
        assertEquals(
            "Sun Dec 02 01:30:00 SGT 2018",
            com.example.andiwijaya.submission3.util.formatDateTimeToGMT("2018-12-01", "17:30:00+00:00")
        )
    }

    @Test
    fun getDateOnly() {
        assertEquals(
            "Sun, 02 Dec 2018",
            com.example.andiwijaya.submission3.util.getDateOnly("Sun Dec 02 01:30:00 SGT 2018")
        )
    }

    @Test
    fun getTimeOnly() {
        assertEquals(
            "01:30",
            com.example.andiwijaya.submission3.util.getTimeOnly("Sun Dec 02 01:30:00 SGT 2018")
        )
    }
}