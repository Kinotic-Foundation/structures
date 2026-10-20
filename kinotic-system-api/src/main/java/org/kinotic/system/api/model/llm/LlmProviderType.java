package org.kinotic.system.api.model.llm;

/**
 * The provider whose API an {@link LlmConfiguration} calls.
 */
public enum LlmProviderType {
    /** Anthropic's Messages API, serving the Claude models. */
    ANTHROPIC,
    /** OpenAI's Chat Completions API, or a server compatible with it. */
    OPENAI
}
