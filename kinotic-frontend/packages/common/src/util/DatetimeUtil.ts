

export default class DatetimeUtil {
    public static formatDateFromEpoch(timeInMills: number): string {
        let ret: string = ''
        if (timeInMills !== 0) {
            const [date, time] = new Date(Number(timeInMills)).toLocaleString('en-US', { hour12: false }).split(', ')
            ret = date + ' ' + time
        }
        return ret
    }

    public static formatDate(dateTime: string): string {
        let ret: string = ''
        if (dateTime?.length > 0) {
            const [date, time] = new Date(dateTime).toLocaleString('en-US', { hour12: false }).split(', ')
            ret = date + ' ' + time
        }
        return ret
    }
/**
 * How long ago a timestamp was, measured against nowMs: "just now", "10 min ago", "1 hr 30 min ago",
 * "1 day ago", "3 days ago". Returns an empty string when the timestamp is absent or unreadable.
 */
public static formatRelativeDate(dateStr: string | number | Date, nowMs: number = Date.now()): string {
    if (!dateStr) return ''

    const date = new Date(dateStr)
    if (isNaN(date.getTime())) return ''

    // a timestamp slightly ahead of this clock reads as just now
    const totalMinutes = Math.max(0, Math.floor((nowMs - date.getTime()) / 60000))
    const hours = Math.floor(totalMinutes / 60)
    const minutes = totalMinutes % 60
    const days = Math.floor(hours / 24)
    let ret: string
    if (totalMinutes === 0) {
        ret = 'just now'
    } else if (hours === 0) {
        ret = `${minutes} min ago`
    } else if (days === 0) {
        ret = minutes > 0 ? `${hours} hr ${minutes} min ago` : `${hours} hr ago`
    } else {
        ret = days === 1 ? '1 day ago' : `${days} days ago`
    }
    return ret
}

/** The calendar day of a timestamp, in words for today and yesterday: "Today", "Yesterday", "Sep 30". */
public static formatDayLabel(timestamp: number | string | Date): string {
    const day = new Date(timestamp)
    const today = new Date()
    const yesterday = new Date(today.getFullYear(), today.getMonth(), today.getDate() - 1)
    let ret: string
    if (day.toDateString() === today.toDateString()) {
        ret = 'Today'
    } else if (day.toDateString() === yesterday.toDateString()) {
        ret = 'Yesterday'
    } else {
        ret = day.toLocaleDateString(undefined, { month: 'short', day: 'numeric' })
    }
    return ret
}

/** Renders a timestamp (epoch millis or an ISO-8601 string) as the 24-hour locale time of day. */
public static formatTime(timestamp: number | string): string {
    return new Date(timestamp).toLocaleTimeString('en-US', { hour12: false })
}

/** Renders a timestamp (epoch millis or an ISO-8601 string) as the locale date, or an em dash when absent. */
public static formatLocaleDate(timestamp: number | string | null): string {
    return timestamp ? new Date(timestamp).toLocaleDateString() : '—'
}

/**
 * Formats the elapsed time between two timestamps, measuring against nowMs while finished is
 * absent. Returns an em dash when started is absent or unreadable.
 */
public static formatDuration(started: number | string | Date | null, finished: number | string | Date | null, nowMs: number = Date.now()): string {
    let ret: string
    const startedMs = DatetimeUtil.toEpochMillis(started)
    if (startedMs === null) {
        ret = '—'
    } else {
        const totalSeconds = Math.max(0, Math.floor(((DatetimeUtil.toEpochMillis(finished) ?? nowMs) - startedMs) / 1000))
        const hours = Math.floor(totalSeconds / 3600)
        const minutes = Math.floor((totalSeconds % 3600) / 60)
        const seconds = totalSeconds % 60
        if (hours > 0) {
            ret = `${hours}h ${minutes}m`
        } else if (minutes > 0) {
            ret = `${minutes}m ${seconds}s`
        } else {
            ret = `${seconds}s`
        }
    }
    return ret
}

/** Epoch millis of a timestamp (epoch millis, an ISO-8601 string, a Date), or null when absent or unreadable. */
public static toEpochMillis(value: number | string | Date | null): number | null {
    let ret: number | null
    if (!value) {
        ret = null
    } else {
        const millis = new Date(value).getTime()
        ret = isNaN(millis) ? null : millis
    }
    return ret
}

/** Renders a timestamp (epoch millis or an ISO-8601 string) as the locale date and time, or an em dash when absent. */
public static formatDateTime(timestamp: number | string | null): string {
    return timestamp ? new Date(timestamp).toLocaleString() : '—'
}

public static formatMonthDayYear(dateStr: string | number | Date): string {
    if (!dateStr) return ''

    const date = new Date(dateStr)
    if (isNaN(date.getTime())) return ''

    return date.toLocaleDateString('en-US', {
        year: 'numeric',
        month: 'short',
        day: 'numeric'
    })
}

}