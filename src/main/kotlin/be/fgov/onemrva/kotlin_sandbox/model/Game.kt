package be.fgov.onemrva.kotlin_sandbox.model

import java.time.Instant

data class Game(
	val id: Long,
	val title: String,
	val genre: String,
	val releaseYear: Int,
	val createdAt: Instant,
	val updatedAt: Instant,
)
