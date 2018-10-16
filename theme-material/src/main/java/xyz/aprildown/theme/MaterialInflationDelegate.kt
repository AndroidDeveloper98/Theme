@file:Suppress("SpellCheckingInspection")

package xyz.aprildown.theme

import android.content.Context
import android.util.AttributeSet
import android.view.View
import xyz.aprildown.theme.internal.InflationDelegate
import xyz.aprildown.theme.views.*

class MaterialInflationDelegate : InflationDelegate {
    override fun createView(
        context: Context,
        attrs: AttributeSet?,
        name: String,
        viewId: Int
    ): View? = when (name) {
        "Button", "$APPCOMPAT_WIDGET.AppCompatButton" ->
            if (viewId == R.id.snackbar_action) {
                ThemeSnackBarButton(context, attrs)
            } else null
        "$APPCOMPAT_WIDGET.RecyclerView" ->
            ThemeRecyclerView(context, attrs)
        "$GOOGLE_MATERIAL.textfield.TextInputLayout" ->
            ThemeTextInputLayout(context, attrs)
        "$GOOGLE_MATERIAL.textfield.TextInputEditText" ->
            ThemeTextInputEditText(context, attrs)
        "$GOOGLE_MATERIAL.tabs.TabLayout" ->
            ThemeTabLayout(context, attrs)
        "$GOOGLE_MATERIAL.navigation.NavigationView" ->
            ThemeNavigationView(context, attrs)
        "$GOOGLE_MATERIAL.bottomnavigation.BottomNavigationView" ->
            ThemeBottomNavigationView(context, attrs)
        "$GOOGLE_MATERIAL.floatingactionbutton.FloatingActionButton" ->
            ThemeFloatingActionButton(context, attrs)
        "androidx.coordinatorlayout.widget.CoordinatorLayout" ->
            ThemeCoordinatorLayout(context, attrs)
        "androidx.swiperefreshlayout.widget.SwipeRefreshLayout" ->
            ThemeSwipeRefreshLayout(context, attrs)
        else -> null
    }

    companion object {
        private const val APPCOMPAT_WIDGET = "androidx.appcompat.widget"
        private const val GOOGLE_MATERIAL = "com.google.android.material"
    }
}