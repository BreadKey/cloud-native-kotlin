package studio.breadkey.rankingservice.repository

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import studio.breadkey.rankingservice.model.GameRecord
import java.util.*

@Repository
interface RecordRepository : JpaRepository<GameRecord, Long> {
    fun findTop10ByGameOrderByScoreDesc(game: String): List<GameRecord>
    fun findTopByGameAndUserIdOrderByScoreDesc(game: String, userId: String): Optional<GameRecord>
    fun findAllByGame(game: String): List<GameRecord>
    fun findAllByGame(game: String, pageable: Pageable): List<GameRecord>
}