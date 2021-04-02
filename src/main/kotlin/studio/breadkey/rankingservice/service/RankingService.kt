package studio.breadkey.rankingservice.service

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import studio.breadkey.rankingservice.model.GameRecord
import studio.breadkey.rankingservice.model.Ranking
import studio.breadkey.rankingservice.repository.RecordRepository

@Service
class RankingService(private val recordRepository: RecordRepository) {
    fun add(newRecord: GameRecord): Ranking {
        newRecord.id = null
        val record = recordRepository.save(newRecord)

        val lastRankingPage = recordRepository.findAllByGame(
            record.game,
            PageRequest.of(MAX_RANKING_NUMBER / RANKING_PAGE_SIZE, RANKING_PAGE_SIZE, Sort.by("score").descending())
        )

        val lastRanking = lastRankingPage.lastOrNull()

        return if (lastRanking == null || lastRanking.score < record.score) {
            findRanking(record.game) { r -> r.id == record.id }!!
        } else {
            Ranking(null, record)
        }
    }

    fun findTop10(game: String): List<Ranking> =
        recordRepository.findTop10ByGameOrderByScoreDesc(game)
            .mapIndexed { index, gameRecord -> Ranking(index + 1, gameRecord) }

    fun findMy(game: String, userId: String): Ranking {
        val ranking = findRanking(game) { record ->
            record.userId == userId
        }

        return ranking ?: Ranking(null,
            recordRepository.findTopByGameAndUserIdOrderByScoreDesc(game, userId).orElseThrow {
                GameRecordNotFoundException(game, userId)
            }
        )
    }

    private fun findRanking(game: String, matcher: (record: GameRecord) -> Boolean): Ranking? {
        for (pageNumber in 0 until (MAX_RANKING_NUMBER / RANKING_PAGE_SIZE)) {
            val page = recordRepository.findAllByGame(
                game,
                PageRequest.of(pageNumber, RANKING_PAGE_SIZE, Sort.by("score").descending())
            )

            page.forEachIndexed { index, record ->
                if (matcher(record)) {
                    return Ranking(pageNumber * RANKING_PAGE_SIZE + index + 1, record)
                }
            }
        }

        return null
    }

    companion object {
        private const val MAX_RANKING_NUMBER = 1000
        private const val RANKING_PAGE_SIZE = 100
    }
}