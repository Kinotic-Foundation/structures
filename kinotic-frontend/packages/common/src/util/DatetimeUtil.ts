

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
 * When a timestamp was, relative to nowMs, to the minute: "just now" and "5 minutes ago" within the
 * last hour, then the clock time: "today at 2:54 AM", "yesterday at 11:02 PM", "Oct 16 at 3:10 PM".
 * Returns an empty string when the timestamp is absent or unreadable.
 */
public static formatRelativeDate(dateStr: string | number | Date | null | undefined, nowMs: number = Date.now()): string {
    if (!dateStr) return ''

    const date = new Date(dateStr)
    if (isNaN(date.getTime())) return ''

    // a timestamp slightly ahead of this clock reads as just now
    const minutes = Math.max(0, Math.floor((nowMs - date.getTime()) / 60000))
    let ret: string
    if (minutes === 0) {
        ret = 'just now'
    } else if (minutes < 60) {
        ret = minutes === 1 ? '1 minute ago' : `${minutes} minutes ago`
    } else {
        const daysAgo = DatetimeUtil.calendarDaysAgo(date, nowMs)
        const day = daysAgo === 0 ? 'today' : daysAgo === 1 ? 'yesterday' : DatetimeUtil.formatCalendarDate(date, nowMs)
        ret = `${day} at ${date.toLocaleTimeString(undefined, { hour: 'numeric', minute: '2-digit' })}`
    }
    return ret
}

/** The calendar day of a timestamp, in words for today and yesterday: "Today", "Yesterday", "Sep 30". */
public static formatDayLabel(timestamp: number | string | Date): string {
    const day = new Date(timestamp)
    const nowMs = Date.now()
    const daysAgo = DatetimeUtil.calendarDaysAgo(day, nowMs)
    let ret: string
    if (daysAgo === 0) {
        ret = 'Today'
    } else if (daysAgo === 1) {
        ret = 'Yesterday'
    } else {
        ret = DatetimeUtil.formatCalendarDate(day, nowMs)
    }
    return ret
}

/** How many local calendar days before the day of nowMs a date falls: 0 today, 1 yesterday. */
private static calendarDaysAgo(date: Date, nowMs: number): number {
    const now = new Date(nowMs)
    const startOfToday = new Date(now.getFullYear(), now.getMonth(), now.getDate()).getTime()
    const startOfDay = new Date(date.getFullYear(), date.getMonth(), date.getDate()).getTime()
    // rounds away the hour a daylight-saving change adds to or removes from a day
    return Math.round((startOfToday - startOfDay) / 86400000)
}

/** A date's month and day, with the year when it differs from that of nowMs: "Sep 30", "Sep 30, 2025". */
private static formatCalendarDate(date: Date, nowMs: number): string {
    const year = date.getFullYear() === new Date(nowMs).getFullYear() ? undefined : 'numeric'
    return date.toLocaleDateString(undefined, { month: 'short', day: 'numeric', year })
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