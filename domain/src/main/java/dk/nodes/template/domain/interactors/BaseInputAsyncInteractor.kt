package dk.nodes.template.domain.interactors

interface BaseInputAsyncInteractor<I, O> {
    suspend operator fun invoke(input: I): O
}