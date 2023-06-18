

export default class DatetimeUtil {
    public static formatDateFromEpoch(timeInMills: number): string {
        let ret: string = ''
        if (timeInMills !== 0) {
            const [date, time] = new Date(Number(timeInMills)).toLocaleString('en-US', {hour12: false}).split(', ')
            ret = date + ' ' + time
        }
        return ret
    }

    public static formatDate(dateTime: string): string {
        let ret: string = ''
        if (dateTime.length > 0) {
            const [date, time] = new Date(dateTime).toLocaleString('en-US', {hour12: false}).split(', ')
            ret = date + ' ' + time
        }
        return ret
    }
}