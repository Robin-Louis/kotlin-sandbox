package be.fgov.onemrva.kotlin_sandbox.model

import java.time.Instant

data class Review(
	val id: Long,
	val gameId: Long,
	val reviewer: String,
	val rating: Int,
	val comment: String,
	val createdAt: Instant,
	val updatedAt: Instant,
)
