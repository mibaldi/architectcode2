package com.mibaldi.architectcoders2.ui.main

import android.Manifest
import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.mibaldi.architectcoders2.R
import com.mibaldi.architectcoders2.domain.Movie
import com.mibaldi.architectcoders2.ui.common.PermissionRequester
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun Fragment.buildMainState(
    context: Context = requireContext(),
    scope: CoroutineScope = viewLifecycleOwner.lifecycleScope,
    navController: NavController = findNavController(),
    locationPermissionRequester: PermissionRequester = PermissionRequester(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
) = MainState(context, scope, navController, locationPermissionRequester)
fun Activity.buildMainState(
    context: Context = this,
    scope: CoroutineScope = GlobalScope,
    navController: NavController? = null,
    locationPermissionRequester: PermissionRequester = PermissionRequester(
        null,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
) = MainState(context, scope, navController, locationPermissionRequester)
class MainState(
    private val context: Context,
    private val scope: CoroutineScope,
    private val navController: NavController?,
    private val locationPermissionRequester: PermissionRequester
) {
    fun onMovieClicked(movie: Movie) {
        Toast.makeText(context,movie.posterPath,Toast.LENGTH_SHORT).show()
        //val action = MainFragmentDirections.actionMainToDetail(movie.id)
        //navController.navigate(action)
    }

    fun requestLocationPermission(afterRequest: (Boolean) -> Unit) {
        scope.launch {
            val result = locationPermissionRequester.request()
            afterRequest(result)
        }
    }

    fun errorToString(error: com.mibaldi.architectcoders2.domain.Error) = when (error) {
        com.mibaldi.architectcoders2.domain.Error.Connectivity -> context.getString(R.string.connectivity_error)
        is com.mibaldi.architectcoders2.domain.Error.Server -> context.getString(R.string.server_error) + error.code
        is com.mibaldi.architectcoders2.domain.Error.Unknown -> context.getString(R.string.unknown_error) + error.message
    }

}