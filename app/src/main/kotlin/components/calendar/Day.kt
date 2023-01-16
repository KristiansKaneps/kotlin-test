package components.calendar

import components.tooltip.Tooltip
import csstype.*
import emotion.react.css
import kotlinx.js.jso
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.ul
import types.Holiday
import kotlin.js.Date

val HIGHLIGHT_COLOR = rgba(0, 0, 0, 0.1)
val TRANSPARENT = rgba(0, 0, 0, 0.0)

external interface DayProps : Props {
    var date: Date
    var holidays: List<Holiday>
    var isHighlighted: Boolean?
    var outsideMonth: Boolean?
    var onClick: ((Date) -> Unit)?
}

val Day = FC<DayProps> {
    div {
        css {
            display = Display.flex
            flexDirection = FlexDirection.column
            justifyContent = JustifyContent.center
            alignItems = AlignItems.center
            transition = "all ease-in-out .1s".unsafeCast<Transition>()
            borderColor = rgba(0, 0, 0, 0.1)
            borderStyle = LineStyle.solid
            borderWidth = 1.px
            cursor = Cursor.pointer
        }
        id = it.date.toISOString()
        style = jso {
            backgroundColor =
                if (it.isHighlighted == true) HIGHLIGHT_COLOR else TRANSPARENT
            if (it.outsideMonth == true)
                color = rgba(0, 0, 0, 0.3)
        }
        onClick = { _ -> it.onClick?.invoke(it.date) }
        Tooltip {
            anchorId = it.date.toISOString()
            html = (
                    "<center>${it.date.toLocaleDateString()}</center>" + it.holidays.map { holiday -> holiday.localName }
                        .joinToString(
                            "<br>"
                        )
                    )
        }
        div {
            css {
                display = Display.flex
                justifyContent = JustifyContent.center
                alignItems = AlignItems.center
                width = "100%".unsafeCast<Width>()
                height = "50%".unsafeCast<Height>()
                fontWeight = FontWeight.bold
                fontSize = 1.25.em
            }
            +it.date.getDate().toString()
        }
        div {
            css {
                width = "100%".unsafeCast<Width>()
                height = "50%".unsafeCast<Height>()
                overflow = Overflow.hidden
                display = Display.flex
                flexDirection = FlexDirection.column
                justifyContent = JustifyContent.flexEnd
            }
            ul {
                css {
                    listStyleType = "circle".unsafeCast<ListStyleType>()
                    padding = "0 0 0 1em".unsafeCast<Padding>()
                    margin = "0".unsafeCast<Margin>()
                }
                it.holidays.forEach { holiday ->
                    li {
                        css {
                            color = rgba(0, 0, 0, 0.5)
                            fontSize = 0.8.em
                        }
                        key = holiday.name
                        +holiday.localName
                    }
                }
            }
        }
    }
}
