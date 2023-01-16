import kotlinx.browser.document
import kotlinx.js.import
import react.create
import react.dom.client.createRoot

fun main() {
    import<dynamic>("react-tooltip/dist/react-tooltip.css")

    val container = document.getElementById("root") ?: error("No element with id 'root' found")
    createRoot(container).render(App.create())
}
