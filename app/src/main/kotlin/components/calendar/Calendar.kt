package components.calendar

import csstype.*
import emotion.react.css
import helpers.CalendarInstance
import react.*
import react.dom.html.ReactHTML.div
import types.Holiday
import kotlin.js.Date

external interface CalendarProps : Props {
    var date: Date?
}

val Calendar = FC<CalendarProps> {
    val (calendar, _) = useState(CalendarInstance(it.date ?: Date()))
    val (holidays, setHolidays) = useState(emptyList<Holiday>())
    val (selectedDay, setSelectedDay) = useState<Date?>(null)

    useEffect(calendar) {
        calendar.fetchHolidays().then { holidays -> setHolidays(holidays) }
    }

    val offset = useMemo(calendar) { calendar.getDateOffset() }
    val overflow = useMemo(calendar) { calendar.getDateOverflow() }

    div {
        css {
            display = Display.grid
            gridTemplateColumns = "repeat(7,1fr)".unsafeCast<GridTemplateColumns>()
            gridTemplateRows = "repeat(6, minmax(100px, 1fr))".unsafeCast<GridTemplateRows>()
        }

        val daysInPreviousMonth = calendar.getDaysInPreviousMonth()
        val daysInCurrentMonth = calendar.getDaysInMonth()
        val daysInNextMonth = calendar.getDaysInNextMonth()
        val prevMonthYear = calendar.getPreviousMonthYear()
        val monthYear = calendar.getYear()
        val nextMonthYear = calendar.getNextMonthYear()
        val prevMonth = calendar.getPreviousMonth()
        val month = calendar.getMonth()
        val nextMonth = calendar.getNextMonth()

        for (i in (daysInPreviousMonth - offset)..daysInPreviousMonth) {
            Day {
                val date = Date(prevMonthYear, prevMonth, i)
                this.key = date.toISOString()
                this.date = date
                this.holidays = emptyList()
                this.isHighlighted = false
                this.outsideMonth = true
            }
        }
        for (i in (1 + offset)..(calendar.getDaysInMonth() + offset)) {
            Day {
                val date = Date(monthYear, month, i - offset)
                this.key = date.toISOString()
                this.date = date
                this.holidays = holidays.filter { calendar.isSameDay(Date(it.date), date) }
                this.isHighlighted = selectedDay?.getDate() == date.getDate()
                this.onClick = { setSelectedDay(it) }
                this.outsideMonth = false
            }
        }
        for (i in 1..overflow) {
            Day {
                val date = Date(nextMonthYear, nextMonth, i)
                this.key = date.toISOString()
                this.date = date
                this.holidays = emptyList()
                this.isHighlighted = false
                this.outsideMonth = true
            }
        }
    }
}
