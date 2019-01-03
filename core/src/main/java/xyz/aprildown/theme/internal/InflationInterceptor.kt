@file:Suppress("SpellCheckingInspection")

package xyz.aprildown.theme.internal

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.Switch
import androidx.annotation.IdRes
import androidx.appcompat.widget.*
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import xyz.aprildown.theme.InflationDelegate
import xyz.aprildown.theme.tint.decorate
import xyz.aprildown.theme.tint.decorateBorderlessButton
import xyz.aprildown.theme.tint.decorateDialogButton
import xyz.aprildown.theme.tint.decorateNormalButton
import xyz.aprildown.theme.utils.resId
import xyz.aprildown.theme.views.ThemeActionMenuItemView
import xyz.aprildown.theme.views.ThemeDrawerLayout
import xyz.aprildown.theme.views.ThemeToolbar

internal class InflationInterceptor(
    private val delegates: Array<out InflationDelegate>
) : LayoutInflater.Factory2 {

    override fun onCreateView(
        name: String,
        context: Context,
        attrs: AttributeSet?
    ): View? {
        return onCreateView(null, name, context, attrs)
    }

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet?
    ): View? {
        val viewId = context.resId(attrs, android.R.attr.id)
        var view: View?
        // Custom Views First
        for (delegate in delegates) {
            view = delegate.createView(context, attrs, name, viewId)
            if (view != null) return view
        }
        view = name.viewForName(context, attrs, viewId)
        return view
    }

    private fun String.viewForName(
        context: Context,
        attrs: AttributeSet?,
        @IdRes viewId: Int
    ): View? {
        return when (this) {
            "TextView", "$APPCOMPAT_WIDGET.AppCompatTextView" ->
                AppCompatTextView(context, attrs).decorate(attrs)
            "ImageView", "$APPCOMPAT_WIDGET.AppCompatImageView" ->
                AppCompatImageView(context, attrs).decorate(attrs)
            "Button", "$APPCOMPAT_WIDGET.AppCompatButton" ->
                AppCompatButton(context, attrs).apply {
                    if (viewId == android.R.id.button1 ||
                        viewId == android.R.id.button2 ||
                        viewId == android.R.id.button3
                    ) decorateDialogButton()
                    else if (isBorderlessButton(context, attrs)) decorateBorderlessButton()
                    else decorateNormalButton(attrs)
                }
            "EditText", "$APPCOMPAT_WIDGET.AppCompatEditText" ->
                AppCompatEditText(context, attrs).decorate(attrs)
            "Spinner", "$APPCOMPAT_WIDGET.AppCompatSpinner" ->
                AppCompatSpinner(context, attrs).decorate(attrs)
            "ImageButton", "$APPCOMPAT_WIDGET.AppCompatImageButton" ->
                AppCompatImageButton(context, attrs).decorate(attrs)
            "CheckBox", "$APPCOMPAT_WIDGET.AppCompatCheckBox" ->
                AppCompatCheckBox(context, attrs).decorate(attrs)
            "RadioButton", "$APPCOMPAT_WIDGET.AppCompatRadioButton" ->
                AppCompatRadioButton(context, attrs).decorate(attrs)
            "SeekBar", "$APPCOMPAT_WIDGET.AppCompatSeekBar" ->
                AppCompatSeekBar(context, attrs).decorate(attrs)

            "Switch" ->
                Switch(context, attrs).decorate(attrs)
            "ProgressBar" ->
                ProgressBar(context, attrs).decorate()
            "Toolbar", "$APPCOMPAT_WIDGET.Toolbar" ->
                ThemeToolbar(context, attrs)
            "$APPCOMPAT_VIEW.ActionMenuItemView" ->
                ThemeActionMenuItemView(context, attrs)
            "$APPCOMPAT_WIDGET.SwitchCompat" ->
                SwitchCompat(context, attrs).decorate(attrs)

            "ListView" ->
                ListView(context, attrs).decorate()
            "ScrollView" ->
                ScrollView(context, attrs).decorate()
            "$APPCOMPAT_WIDGET.RecyclerView" ->
                RecyclerView(context, attrs).decorate()
            "$ANDROIDX_WIDGET.NestedScrollView" ->
                NestedScrollView(context, attrs).decorate()
            "androidx.viewpager.widget.ViewPager" ->
                ViewPager(context, attrs).decorate()
            "androidx.drawerlayout.widget.DrawerLayout" ->
                ThemeDrawerLayout(context, attrs)
            "androidx.swiperefreshlayout.widget.SwipeRefreshLayout" ->
                SwipeRefreshLayout(context, attrs).decorate()
            else -> null
        }
    }

    companion object {

        private const val ANDROIDX_WIDGET = "androidx.core.widget"
        private const val APPCOMPAT_WIDGET = "androidx.appcompat.widget"
        private const val APPCOMPAT_VIEW = "androidx.appcompat.view"

        private fun isBorderlessButton(
            context: Context?,
            attrs: AttributeSet?
        ): Boolean {
            if (context == null || attrs == null) {
                return false
            }
            val backgroundRes = context.resId(attrs, android.R.attr.background)
            if (backgroundRes == 0) {
                return false
            }
            val resName = context.resources.getResourceEntryName(backgroundRes)
            return resName.endsWith("btn_borderless_material")
        }
    }
}
