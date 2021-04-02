package studio.breadkey.rankingservice

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import studio.breadkey.rankingservice.model.GameRecord
import studio.breadkey.rankingservice.service.RankingService

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class RankingServiceApplicationTests {
    @Autowired
    private lateinit var rankingService: RankingService

    @BeforeEach
    fun before() {
        listOf(
            GameRecord(
                "tetris", 1000, "breadkey", "LYK"
            ),
            GameRecord(
                "tetris", 300, "kihoon94", "KKH"
            )
        ).forEach { record ->
            rankingService.add(record)
        }
    }

    @Test
    fun findTop10() {
        val top10 = rankingService.findTop10("tetris")
        assertEquals("breadkey", top10.first().userId)
        assertEquals("kihoon94", top10.last().userId)
    }
}
