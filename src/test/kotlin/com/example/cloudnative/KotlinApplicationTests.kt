package com.example.cloudnative

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.security.access.SecurityConfig
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class KotlinApplicationTests {
    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var catRepository: CatRepository

    @BeforeEach
    fun before() {
        listOf("Felix", "Garfield", "Whiskers").forEach { name ->
            catRepository.save(Cat(name))
        }
    }

    @Test
    @WithMockUser
    fun catReflectedInRead() {
        val halJson = MediaType.parseMediaType("application/hal+json")

        mvc.perform(
                MockMvcRequestBuilders
                        .get("/cats")
        )
                .andExpect(status().isOk)
                .andExpect(content().contentType(halJson))
                .andExpect { mvcResult ->
                    val contentAsString = mvcResult.response.contentAsString
                    assertEquals("3",
                            contentAsString.split("totalElements")[1]
                                    .split(":")[1].trim()
                                    .split(",")[0])
                }
    }
}
