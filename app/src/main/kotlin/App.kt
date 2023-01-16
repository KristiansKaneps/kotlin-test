import components.calendar.Calendar
import csstype.TextAlign
import emotion.react.css
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1

val App = FC<Props> {
    div {
        h1 {
            css {
                textAlign = TextAlign.center
            }
            +"Calendar"
        }
        Calendar()
    }
}
