package org.kinotic.system.api.model.llm;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * The provider whose API an {@link LlmConfiguration} calls.
 */
@Getter
@RequiredArgsConstructor
public enum LlmProviderType {
    /** Anthropic's Messages API, serving the Claude models. */
    ANTHROPIC(1.0),
    /** OpenAI's Chat Completions API, or a server compatible with it. */
    OPENAI(2.0);

    /**
     * The highest temperature the provider's API accepts; the lowest is 0.
     */
    private final double maxTemperature;
}
