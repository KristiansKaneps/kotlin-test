@file:JsModule("react-tooltip")
@file:JsNonModule

package components.tooltip

import react.*

external interface Position {
    var x: Number
    var y: Number
}

external interface TooltipProps : Props {
    var className: String?
    var classNameArrow: String?
    var content: String?
    var html: String?
    var place: String?
    var offset: Number?
    var id: String?
    var anchorId: String?
    var variant: String?
    var wrapper: ReactElement<dynamic>?
    var children: Children?
    var events: Array<String>?
    var positionStrategy: String?
    var delayShow: Number?
    var delayHide: Number?
    var float: Boolean?
    var noArrow: Boolean?
    var clickable: Boolean?
    var style: CSSProperties?
    var position: Position?
    var isOpen: Boolean?
    var setIsOpen: ((Boolean) -> Unit)?
    var afterShow: (() -> Unit)?
    var afterHide: (() -> Unit)?
}

@JsName("Tooltip")
external val Tooltip: ComponentClass<TooltipProps>
