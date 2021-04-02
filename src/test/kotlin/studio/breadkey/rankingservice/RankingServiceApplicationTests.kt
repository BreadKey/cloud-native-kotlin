package studio.breadkey.rankingservice

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import studio.breadkey.rankingservice.model.GameRecord
import studio.breadkey.rankingservice.model.Ranking

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@WithMockUser(username = "breadkey", roles = ["USER"])
class RankingServiceApplicationTest {
    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun addNewRecord() {
        val json = objectMapper.writeValueAsString(GameRecord("tetris", 10000, "breadkey"))

        mvc.perform(
            MockMvcRequestBuilders.post("/ranking/tetris").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isCreated)
            .andExpect { mvcResult ->
                val ranking = objectMapper.readValue(mvcResult.response.contentAsString, Ranking::class.java)

                assertEquals(1, ranking.number)
            }
    }
}
