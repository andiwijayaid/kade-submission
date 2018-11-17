package com.example.andiwijaya.submission3.api

import org.junit.Test

import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class ApiRepositoryTest {

    @Test
    fun doRequest() {

        val apiRepository = mock(ApiRepository::class.java)
        val url = "https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php?id=4238"

        apiRepository.doRequest(url)
        verify(apiRepository).doRequest(url)
    }

}