package studio.breadkey.rankingservice.controller

import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import studio.breadkey.rankingservice.model.GameRecord
import studio.breadkey.rankingservice.model.Ranking
import studio.breadkey.rankingservice.service.RankingService

@RestController
@RequestMapping("/ranking")
class RankingServiceController(private val rankingService: RankingService, private val assembler: RankingAssembler) {
    @GetMapping("/{game}/top10")
    fun top10(@PathVariable game: String): CollectionModel<EntityModel<Ranking>> {
        val top10 = rankingService.findTop10(game)

        return assembler.toCollectionModel(
            top10
        ).add(
            linkTo<RankingServiceController> {
                top10(game)
            }.withSelfRel()
        )
    }

    @GetMapping("/{game}/{userId}")
    fun my(@PathVariable game: String, @PathVariable userId: String): EntityModel<Ranking> {
        return assembler.toModel(
            rankingService.findMy(game, userId)
        )
    }

    @PostMapping("/{game}")
    fun newRecord(
        @PathVariable game: String,
        @RequestBody newRecord: GameRecord
    ): ResponseEntity<EntityModel<Ranking>> {
        val rankingModel =
            assembler.toModel(rankingService.add(newRecord))

        return ResponseEntity.created(
            linkTo<RankingServiceController> { my(game, newRecord.userId) }.withRel { "$game/${newRecord.userId}" }
                .toUri()
        ).body(rankingModel)
    }
}