import { LlmProviderType } from '@kinotic-ai/system-api'

/** The name the console shows for each LLM provider. */
export const LLM_PROVIDER_NAMES: Record<LlmProviderType, string> = {
    [LlmProviderType.ANTHROPIC]: 'Anthropic',
    [LlmProviderType.OPENAI]: 'OpenAI'
}
