package com.mibaldi.architectcoders2.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.mibaldi.architectcoders2.R
import com.mibaldi.architectcoders2.databinding.ActivityMainBinding
import com.mibaldi.architectcoders2.ui.common.launchAndCollect
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel : MainViewModel by viewModels()
    lateinit var binding : ActivityMainBinding
    private lateinit var mainState: MainState
    private val adapter = MoviesAdapter {
       mainState.onMovieClicked(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainState = this.buildMainState()
        binding.recycler.adapter = adapter
        launchAndCollect(viewModel.state){
            binding.movies = it.movies
            binding.error = it.error?.let(mainState::errorToString)
        }
        viewModel.onUiReady()
        mainState.requestLocationPermission {
            viewModel.onUiReady()
        }

    }

}