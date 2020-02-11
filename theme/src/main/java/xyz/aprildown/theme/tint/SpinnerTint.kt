package xyz.aprildown.theme.tint

import androidx.appcompat.widget.AppCompatSpinner
import androidx.core.view.ViewCompat
import xyz.aprildown.theme.R
import xyz.aprildown.theme.utils.toColorStateList

internal class SpinnerTint : BaseTint<AppCompatSpinner>(
    attrs = R.styleable.Theme_Spinner,
    defStyleAttr = R.attr.spinnerStyle,
    onTint = {
        // R.style.Widget_AppCompat_Spinner
        val spinner = view
        matchThemeColor(R.styleable.Theme_Spinner_backgroundTint)?.let {
            ViewCompat.setBackgroundTintList(spinner, it.toColorStateList())
        }
    }
)