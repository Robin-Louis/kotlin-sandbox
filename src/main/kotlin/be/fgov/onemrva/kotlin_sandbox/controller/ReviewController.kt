package be.fgov.onemrva.kotlin_sandbox.controller

import be.fgov.onemrva.kotlin_sandbox.model.CreateReviewRequest
import be.fgov.onemrva.kotlin_sandbox.model.Review
import be.fgov.onemrva.kotlin_sandbox.model.UpdateReviewRequest
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
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/reviews")
class ReviewController(
	private val reviewRepository: ReviewRepository,
	private val gameRepository: GameRepository,
) {
	@GetMapping
	fun listReviews(@RequestParam(required = false) gameId: Long?): List<Review> =
		reviewRepository.list(gameId)

	@GetMapping("/{id}")
	fun getReview(@PathVariable id: Long): Review =
		reviewRepository.find(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found")

	@PostMapping
	fun createReview(@RequestBody request: CreateReviewRequest): Review {
		ensureGameExists(request.gameId)
		ensureRatingValid(request.rating)
		return reviewRepository.create(request)
	}

	@PutMapping("/{id}")
	fun updateReview(@PathVariable id: Long, @RequestBody request: UpdateReviewRequest): Review {
		request.gameId?.let { ensureGameExists(it) }
		request.rating?.let { ensureRatingValid(it) }
		return reviewRepository.update(id, request)
			?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found")
	}

	@DeleteMapping("/{id}")
	fun deleteReview(@PathVariable id: Long) {
		if (!reviewRepository.delete(id)) {
			throw ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found")
		}
	}

	private fun ensureGameExists(gameId: Long) {
		if (gameRepository.find(gameId) == null) {
			throw ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found")
		}
	}

	private fun ensureRatingValid(rating: Int) {
		if (rating !in 1..10) {
			throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Rating must be between 1 and 10")
		}
	}
}
