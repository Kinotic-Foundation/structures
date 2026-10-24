import type { ProgressBarDesignTokens } from '@primeuix/themes/types/progressbar';

 export default {
    root: {
        background: "{content.border.color}",
        borderRadius: "{content.border.radius}",
        height: "1.25rem"
    },
    // Progress fills in sky, the colour of everything in progress
    value: {
        background: "{sky.500}"
    },
    label: {
        color: "{primary.contrast.color}",
        fontSize: "0.75rem",
        fontWeight: "600"
    }
} satisfies ProgressBarDesignTokens;