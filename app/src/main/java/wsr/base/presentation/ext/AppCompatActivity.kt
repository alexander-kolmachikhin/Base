package wsr.base.presentation.ext

import android.app.Activity
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import wsr.base.R

inline fun <reified T : Fragment> AppCompatActivity.getFragment(tag: String? = T::class.java.name) =
    supportFragmentManager.getFragment<T>(tag)

inline fun <reified T : Fragment> AppCompatActivity.showFragment(
    @IdRes containerId: Int = R.id.fragmentContainerView,
    arguments: Bundle? = null,
    addToBackStack: Boolean = true,
    tag: String? = T::class.java.name
) = supportFragmentManager.showFragment<T>(containerId, arguments, addToBackStack, tag)