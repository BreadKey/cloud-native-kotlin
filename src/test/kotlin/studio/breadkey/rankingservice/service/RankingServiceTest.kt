package studio.breadkey.rankingservice.service

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import studio.breadkey.rankingservice.model.GameRecord
import studio.breadkey.rankingservice.repository.RecordRepository

@SpringBootTest
internal class RankingServiceTest {
    @Autowired
    private lateinit var rankingService: RankingService
    @Autowired
    private lateinit var gameRecordRepository: RecordRepository

    @BeforeEach
    fun setUp() {
        listOf(
            GameRecord(
                "tetris", 500, "breadkey"
            ),
            GameRecord(
                "tetris", 1000, "breadkey"
            ),
            GameRecord(
                "tetris", 300, "kihoon94"
            )
        ).forEach { record ->
            gameRecordRepository.save(record)
        }
    }

    @AfterEach
    fun tearDown() {
        gameRecordRepository.deleteAll()
    }

    @Test
    fun findTop10() {
        val top10 = rankingService.findTop10("tetris")
        assertEquals("breadkey", top10.first().gameRecord.userId)
        assertEquals("kihoon94", top10.last().gameRecord.userId)
    }

    @Test
    fun findMy() {
        val kihoonsRanking = rankingService.findMy("tetris", "kihoon94")

        assertEquals(3, kihoonsRanking.number)
    }
}