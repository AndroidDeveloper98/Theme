package xyz.aprildown.theme.internal

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.AttrRes
import xyz.aprildown.theme.utils.safeResourceName

internal class AttrWizard(
    private val context: Context,
    private val attrs: AttributeSet?
) {

    fun getRawValue(@AttrRes attrId: Int): String {
        if (attrs == null || attrId == 0) {
            return ""
        }

        val res = context.resources
        val attrName = res.safeResourceName(attrId)
        val attrIndex = attrs.indexOfAttr(context) { it == attrName }
        if (attrIndex == -1) {
            return ""
        }

        val attrValue = attrs.getAttributeValue(attrIndex)
        return when {
            attrValue.startsWith('@') || attrValue.startsWith('?') -> {
                val rawId = attrValue.substring(1)
                var resName = if (rawId.all { it.isDigit() }) {
                    val id = rawId.toInt()
                    if (id == 0) {
                        return ""
                    }
                    res.safeResourceName(id)
                } else {
                    rawId
                }
                if (!resName.startsWith("android")) {
                    resName = resName.substring(resName.indexOf(':') + 1)
                }
                "${attrValue[0]}$resName"
            }
            else -> attrValue
        }
    }
}

private fun AttributeSet.indexOfAttr(
    context: Context,
    matcher: (String) -> (Boolean)
): Int {
    for (i in 0 until attributeCount) {
        val nameResource = getAttributeNameResource(i)
        val literalName = context.resources.safeResourceName(nameResource)
        if (matcher(literalName)) {
            return i
        }
    }
    return -1
}
