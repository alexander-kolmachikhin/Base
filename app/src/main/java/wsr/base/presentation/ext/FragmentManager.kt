package wsr.base.presentation.ext

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit

inline fun <reified T : Fragment> FragmentManager.getFragment(tag: String? = T::class.java.name) =
    (findFragmentByTag(tag) ?: T::class.java.newInstance()) as T

inline fun <reified T : Fragment> FragmentManager.showFragment(
    @IdRes containerId: Int,
    arguments: Bundle? = null,
    addToBackStack: Boolean = true,
    tag: String? = T::class.java.name
) = commit {
    if (addToBackStack) addToBackStack(null)
    replace(
        containerId,
        getFragment<T>().apply { this.arguments = arguments },
        tag
    )
}