package studio.breadkey.rankingservice.service

import org.springframework.stereotype.Service
import studio.breadkey.rankingservice.model.GameRecord
import studio.breadkey.rankingservice.repository.RecordRepository

@Service
class RankingService(private val recordRepository: RecordRepository) {
    fun add(gameRecord: GameRecord) {
        recordRepository.save(gameRecord)
    }

    fun findTop10(game: String) =
        recordRepository.findTop10ByGameOrderByScoreDesc(game)
}