package studio.breadkey.rankingservice.service

class GameRecordNotFoundException(game: String, userId: String) :
    RuntimeException("Could not find game record $game $userId")