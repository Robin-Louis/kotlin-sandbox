package be.fgov.onemrva.kotlin_sandbox.model

data class CreateGameRequest(
	val title: String,
	val genre: String,
	val releaseYear: Int,
)

data class UpdateGameRequest(
	val title: String?,
	val genre: String?,
	val releaseYear: Int?,
)
