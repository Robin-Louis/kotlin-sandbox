package be.fgov.onemrva.kotlin_sandbox.controller

import be.fgov.onemrva.kotlin_sandbox.model.CreateGameRequest
import be.fgov.onemrva.kotlin_sandbox.model.Game
import be.fgov.onemrva.kotlin_sandbox.model.UpdateGameRequest
import be.fgov.onemrva.kotlin_sandbox.repository.GameRepository
import be.fgov.onemrva.kotlin_sandbox.repository.ReviewRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/games")
class GameController(
	private val gameRepository: GameRepository,
	private val reviewRepository: ReviewRepository,
) {
	@GetMapping
	fun listGames(): List<Game> = gameRepository.list()

	@GetMapping("/{id}")
	fun getGame(@PathVariable id: Long): Game =
		gameRepository.find(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found")

	@PostMapping
	fun createGame(@RequestBody request: CreateGameRequest): Game =
		gameRepository.create(request)

	@PutMapping("/{id}")
	fun updateGame(@PathVariable id: Long, @RequestBody request: UpdateGameRequest): Game =
		gameRepository.update(id, request)
			?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found")

	@DeleteMapping("/{id}")
	fun deleteGame(@PathVariable id: Long) {
		if (!gameRepository.delete(id)) {
			throw ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found")
		}
		reviewRepository.list(id).forEach { reviewRepository.delete(it.id) }
	}
}
