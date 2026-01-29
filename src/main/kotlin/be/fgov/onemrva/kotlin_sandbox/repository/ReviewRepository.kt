package be.fgov.onemrva.kotlin_sandbox.repository

import be.fgov.onemrva.kotlin_sandbox.model.CreateReviewRequest
import be.fgov.onemrva.kotlin_sandbox.model.Review
import be.fgov.onemrva.kotlin_sandbox.model.UpdateReviewRequest
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

@Repository
class ReviewRepository {
	private val idGenerator = AtomicLong(1)
	private val reviews = ConcurrentHashMap<Long, Review>()

	fun list(gameId: Long?): List<Review> = reviews.values
		.filter { gameId == null || it.gameId == gameId }
		.sortedBy { it.id }

	fun find(id: Long): Review? = reviews[id]

	fun create(request: CreateReviewRequest): Review {
		val now = Instant.now()
		val review = Review(
			id = idGenerator.getAndIncrement(),
			gameId = request.gameId,
			reviewer = request.reviewer,
			rating = request.rating,
			comment = request.comment,
			createdAt = now,
			updatedAt = now,
		)
		reviews[review.id] = review
		return review
	}

	fun update(id: Long, request: UpdateReviewRequest): Review? {
		val existing = reviews[id] ?: return null
		val updated = existing.copy(
			gameId = request.gameId ?: existing.gameId,
			reviewer = request.reviewer ?: existing.reviewer,
			rating = request.rating ?: existing.rating,
			comment = request.comment ?: existing.comment,
			updatedAt = Instant.now(),
		)
		reviews[id] = updated
		return updated
	}

	fun delete(id: Long): Boolean = reviews.remove(id) != null
}
