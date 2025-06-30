import type { DataViewDesignTokens } from '@primeuix/themes/types/dataview';

 export default {
    root: {
        borderColor: "transparent",
        borderWidth: "0",
        borderRadius: "0",
        padding: "0"
    },
    header: {
        background: "{content.background}",
        color: "{content.color}",
        borderColor: "{content.border.color}",
        borderWidth: "1px",
        padding: "0.75rem 1rem",
        borderRadius: "0"
    },
    content: {
        background: "{content.background}",
        color: "{content.color}",
        borderColor: "transparent",
        borderWidth: "0",
        padding: "0",
        borderRadius: "0"
    },
    footer: {
        background: "{content.background}",
        color: "{content.color}",
        borderColor: "{content.border.color}",
        borderWidth: "1px",
        padding: "0.75rem 1rem",
        borderRadius: "0"
    },
    paginatorTop: {
        borderColor: "{content.border.color}",
        borderWidth: "1px"
    },
    paginatorBottom: {
        borderColor: "{content.border.color}",
        borderWidth: "1px"
    }
} satisfies DataViewDesignTokens;