import type { DividerDesignTokens } from '@primeuix/themes/types/divider';

 export default {
    root: {
        borderColor: "{content.border.color}"
    },
    content: {
        background: "{content.background}",
        color: "{text.color}"
    },
    horizontal: {
        margin: "1rem 0",
        padding: "0",
        content: {
            padding: "0 0.5rem"
        }
    },
    vertical: {
        margin: "0 1rem",
        padding: "0",
        content: {
            padding: "0.5rem 0"
        }
    }
} satisfies DividerDesignTokens;