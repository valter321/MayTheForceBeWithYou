package com.valter.maytheforcebewith_valterfrancisco.ui.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.valter.maytheforcebewith_valterfrancisco.R
import com.valter.maytheforcebewith_valterfrancisco.utils.hide
import com.valter.maytheforcebewith_valterfrancisco.utils.show
import kotlinx.android.synthetic.main.fragment_base.*
import kotlinx.android.synthetic.main.fragment_base.view.*
import kotlinx.android.synthetic.main.fragment_base_error.*
import kotlinx.android.synthetic.main.fragment_base_loading.*

/**
 * BaseFragment
 *
 * This fragment handles the logic to show/hide the content, loading and error views.
 * ViewStubs are used for the loading and error views to reduce memory usage and speed
 * up rendering by loading the views only when they are needed.
 */
abstract class BaseFragment : Fragment(), View.OnApplyWindowInsetsListener {

    @get:LayoutRes
    protected abstract val layout: Int

    private var horizontalMargin = 0

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_base, container, false)
        inflater.inflate(layout, view.frmContent)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setTranslationZ(
                view,
                requireFragmentManager().backStackEntryCount.toFloat()
        )

        view.setOnApplyWindowInsetsListener(this)
    }

    /**
     * Sets margin in the left and in the right of the ViewStubs.
     *
     * @param margin the margin to be applied.
     */
    protected fun setHorizontalMargin(margin: Int) {
        this.horizontalMargin = margin
    }

    /**
     * Shows the content view.
     */
    protected fun showContent() {
        hideError()
        hideLoading()
        frmContent.show()
    }

    /**
     * Shows the loading view.
     */
    protected open fun showLoading() {
        frmContent.hide()
        hideError()

        vwsLoad?.let { inflateLayout(it, R.layout.fragment_base_loading) }
            ?: frmLoading?.show()
    }

    /**
     * Shows the error view.
     *
     * @param throwable the error to be showed.
     * @param listener a callback to be invoked when the retry button is clicked.
     */
    protected fun showError(throwable: Throwable, listener: (() -> Unit)? = null) {
        throwable.printStackTrace()
        frmContent.hide()
        hideLoading()

        vwsError?.let { inflateLayout(it, R.layout.fragment_base_error) }
            ?: frmError?.show()

        // Searches for the the [Throwable] in the ErrorStates.
//        frmError.setError(errorResolver.find(throwable), listener)
    }

    private fun hideLoading() {
        frmLoading?.hide()
    }

    private fun hideError() {
        frmError?.hide()
    }

    private fun inflateLayout(viewStub: ViewStub, @LayoutRes layoutRes: Int) {
        viewStub.apply {
            layoutResource = layoutRes
        }.also {
            val layout = it.inflate()

            setHorizontalMargins(layout)
        }
    }

    // This part is needed so the horizontal margins work on Lollipop.
    // If the margins get set when the view gets created, they do not get applied on Lollipop devices.
    private fun setHorizontalMargins(layout: View) {
        layout.parent.let {
            val rootLayoutParams = layout.layoutParams as FrameLayout.LayoutParams
            rootLayoutParams.setMargins(
                    horizontalMargin,
                    rootLayoutParams.topMargin,
                    horizontalMargin,
                    rootLayoutParams.bottomMargin
            )
            layout.layoutParams = rootLayoutParams
        }
    }
}
