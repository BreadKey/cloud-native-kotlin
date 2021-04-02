package studio.breadkey.rankingservice.controller

import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.RepresentationModelAssembler
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.stereotype.Component
import studio.breadkey.rankingservice.model.Ranking

@Component
class RankingAssembler : RepresentationModelAssembler<Ranking, EntityModel<Ranking>> {
    override fun toModel(ranking: Ranking): EntityModel<Ranking> = EntityModel.of(
        ranking,
        linkTo<RankingServiceController> {
            top10(ranking.gameRecord.game)
        }.withRel("${ranking.gameRecord.game}/top10")
    )
}