package wsr.base.presentation.ext

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import wsr.base.R

inline fun <reified T : Fragment> Fragment.getFragment(tag: String? = T::class.java.name) =
    childFragmentManager.getFragment<T>(tag)

inline fun <reified T : Fragment> Fragment.showFragment(
    @IdRes containerId: Int = R.id.fragmentContainerView,
    arguments: Bundle? = null,
    addToBackStack: Boolean = true,
    tag: String? = T::class.java.name
) = childFragmentManager.showFragment<T>(containerId, arguments, addToBackStack, tag)

fun Fragment.listenChildResult(key: String, onResult: (Bundle) -> Unit) =
    childFragmentManager.listenChildResult(key, viewLifecycleOwner, onResult)