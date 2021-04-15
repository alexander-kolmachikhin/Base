package wsr.base.presentation.app.films

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import org.koin.android.ext.android.inject
import wsr.base.R
import wsr.base.databinding.FilmsBinding

class FilmsFragment : Fragment(R.layout.films) {

    private val test = "https://vk.com/video393832024_456239524"
    private val binding: FilmsBinding by viewBinding()
    private val player by lazy {
        SimpleExoPlayer.Builder(requireContext()).build().also {
            it.setMediaItem(MediaItem.fromUri(test))
            it.prepare()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.playerView.player = player
        player.seekTo(savedPosition)
    }

    override fun onResume() {
        super.onResume()
        player.play()
    }

    override fun onPause() {
        super.onPause()
        savedPosition = player.currentPosition
        player.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }

    companion object {
        private var savedPosition = 0L
    }
}