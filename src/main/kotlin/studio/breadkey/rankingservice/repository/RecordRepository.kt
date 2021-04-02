package studio.breadkey.rankingservice.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import studio.breadkey.rankingservice.model.GameRecord

@Repository
interface RecordRepository : JpaRepository<GameRecord, Long> {
    fun findTop10ByGameOrderByScoreDesc(game: String): List<GameRecord>
}