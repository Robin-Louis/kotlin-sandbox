package be.fgov.onemrva.kotlin_sandbox.repository

import be.fgov.onemrva.kotlin_sandbox.model.CreateGameRequest
import be.fgov.onemrva.kotlin_sandbox.model.Game
import be.fgov.onemrva.kotlin_sandbox.model.UpdateGameRequest
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

@Repository
class GameRepository {
	private val idGenerator = AtomicLong(1)
	private val games = ConcurrentHashMap<Long, Game>()

	fun list(): List<Game> = games.values.sortedBy { it.id }

	fun find(id: Long): Game? = games[id]

	fun create(request: CreateGameRequest): Game {
		val now = Instant.now()
		val game = Game(
			id = idGenerator.getAndIncrement(),
			title = request.title,
			genre = request.genre,
			releaseYear = request.releaseYear,
			createdAt = now,
			updatedAt = now,
		)
		games[game.id] = game
		return game
	}

	fun update(id: Long, request: UpdateGameRequest): Game? {
		val existing = games[id] ?: return null
		val updated = existing.copy(
			title = request.title ?: existing.title,
			genre = request.genre ?: existing.genre,
			releaseYear = request.releaseYear ?: existing.releaseYear,
			updatedAt = Instant.now(),
		)
		games[id] = updated
		return updated
	}

	fun delete(id: Long): Boolean = games.remove(id) != null
}
