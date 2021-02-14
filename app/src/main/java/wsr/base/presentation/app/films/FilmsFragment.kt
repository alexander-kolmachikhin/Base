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

    private val test =
        "https://r2---sn-n8v7snl7.googlevideo.com/videoplayback?expire=1613323910&ei=JgopYI3FHoeX2_gPw7yQuAc&ip=101.108.182.9&id=o-ABDy15k-3TkumYJeW0mozuKOP7N_JNym01wbNMgMGk2Q&itag=18&source=youtube&requiressl=yes&vprv=1&mime=video%2Fmp4&ns=_UWBo5-NzcEdRp5hfBLZP_QF&gir=yes&clen=17507883&ratebypass=yes&dur=197.183&lmt=1582694719677260&fvip=2&c=WEB&txp=2311222&n=itmdPEp5aXBZri0cfc&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cvprv%2Cmime%2Cns%2Cgir%2Cclen%2Cratebypass%2Cdur%2Clmt&sig=AOq0QJ8wRQIhAN4Mg9B_9-SLLZ_01Ipv91oJstr4ffHtVr0mP5wkVATQAiAY7n-6v4j45arzFdEq_mG8wvofjtVCuvY6DtUjNM5K3A%3D%3D&rm=sn-uvu-c33y7l,sn-uvu-c3367l,sn-30aer7z&req_id=e064275811e5a3ee&redirect_counter=3&cms_redirect=yes&ipbypass=yes&mh=n6&mip=109.174.112.171&mm=30&mn=sn-n8v7snl7&ms=nxu&mt=1613302105&mv=m&mvi=2&pl=17&lsparams=ipbypass,mh,mip,mm,mn,ms,mv,mvi,pl&lsig=AG3C_xAwRgIhAPNUJ3_y4KMYdQrZZZRG69QeT2nm4LPDIgXF9PTv_x4qAiEAkmzxDUfJqFDN49vo05MyVWCDrrtQrExVC8XsMAY9Ujw%3D"

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