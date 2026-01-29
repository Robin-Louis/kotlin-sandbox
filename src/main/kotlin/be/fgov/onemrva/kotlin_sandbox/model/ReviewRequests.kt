package be.fgov.onemrva.kotlin_sandbox.model

data class CreateReviewRequest(
	val gameId: Long,
	val reviewer: String,
	val rating: Int,
	val comment: String,
)

data class UpdateReviewRequest(
	val gameId: Long?,
	val reviewer: String?,
	val rating: Int?,
	val comment: String?,
)
