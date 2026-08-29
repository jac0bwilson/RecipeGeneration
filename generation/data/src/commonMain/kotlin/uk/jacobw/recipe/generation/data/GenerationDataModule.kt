package uk.jacobw.recipe.generation.data

import ai.koog.http.client.ktor.KtorKoogHttpClient
import ai.koog.prompt.executor.llms.all.simpleGoogleAIExecutor
import ai.koog.prompt.executor.model.PromptExecutor
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import uk.jacobw.recipe.BuildKonfig

@ComponentScan
@Module
class GenerationDataModule {
    @Single
    fun providesPromptExecutor(): PromptExecutor = simpleGoogleAIExecutor(
        apiKey = BuildKonfig.GEMINI_API_KEY,
        httpClientFactory = KtorKoogHttpClient.Factory(),
    )
}
