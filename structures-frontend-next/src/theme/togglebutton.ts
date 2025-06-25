import type { ToggleButtonDesignTokens } from '@primeuix/themes/types/togglebutton';

 export default {
    root: {
        padding: "0.25rem",
        borderRadius: "{content.border.radius}",
        gap: "0.5rem",
        fontWeight: "500",
        disabledBackground: "{form.field.disabled.background}",
        disabledBorderColor: "{form.field.disabled.background}",
        disabledColor: "{form.field.disabled.color}",
        invalidBorderColor: "{form.field.invalid.border.color}",
        focusRing: {
            width: "{focus.ring.width}",
            style: "{focus.ring.style}",
            color: "{focus.ring.color}",
            offset: "{focus.ring.offset}",
            shadow: "{focus.ring.shadow}"
        },
        transitionDuration: "{form.field.transition.duration}",
        sm: {
            fontSize: "{form.field.sm.font.size}",
            padding: "0.25rem"
        },
        lg: {
            fontSize: "{form.field.lg.font.size}",
            padding: "0.25rem"
        }
    },
    icon: {
        disabledColor: "{form.field.disabled.color}"
    },
    content: {
        padding: "0.25rem 0.75rem",
        borderRadius: "{content.border.radius}",
        checkedShadow: "0 1px 2px 0 rgba(0, 0, 0, 0.02),0 1px 2px 0 rgba(0, 0, 0, 0.04)",
        sm: {
            padding: "0.25rem 0.75rem"
        },
        lg: {
            padding: "0.25rem 0.75rem"
        }
    },
    colorScheme: {
        light: {
            root: {
                background: "{surface.100}",
                checkedBackground: "{surface.100}",
                hoverBackground: "{surface.100}",
                borderColor: "{surface.100}",
                color: "{surface.500}",
                hoverColor: "{surface.700}",
                checkedColor: "{surface.900}",
                checkedBorderColor: "{surface.100}"
            },
            icon: {
                color: "{surface.500}",
                hoverColor: "{surface.700}",
                checkedColor: "{surface.900}"
            },
            content: {
                checkedBackground: "{surface.0}"
            }
        },
        dark: {
            root: {
                background: "{surface.950}",
                checkedBackground: "{surface.950}",
                hoverBackground: "{surface.950}",
                borderColor: "{surface.950}",
                color: "{surface.400}",
                hoverColor: "{surface.300}",
                checkedColor: "{surface.0}",
                checkedBorderColor: "{surface.950}"
            },
            icon: {
                color: "{surface.400}",
                hoverColor: "{surface.300}",
                checkedColor: "{surface.0}"
            },
            content: {
                checkedBackground: "{surface.800}"
            }
        }
    }
} satisfies ToggleButtonDesignTokens;