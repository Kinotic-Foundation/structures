/**
 * The provider whose API an LlmConfiguration calls.
 */
export enum LlmProviderType {
    /** Anthropic's Messages API, serving the Claude models. */
    ANTHROPIC = 'ANTHROPIC',
    /** OpenAI's Chat Completions API, or a server compatible with it. */
    OPENAI = 'OPENAI'
}
