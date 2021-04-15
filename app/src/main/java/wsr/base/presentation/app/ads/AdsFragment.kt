package wsr.base.presentation.app.ads

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdView
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import wsr.base.R
import wsr.base.databinding.AdsBinding

class AdsFragment : Fragment(R.layout.ads) {

    private val ADMOB_NATIVE_AD_UNIT_ID = "ca-app-pub-3940256099942544/2247696110"
    private var currentNativeAd: NativeAd? = null

    private val binding: AdsBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            adView.loadAd(AdRequest.Builder().build())
            showInterstitialButton.setOnClickListener { showInterstitial() }
            refreshButton.setOnClickListener { refreshNativeAd() }
            showRewardedVideoButton.setOnClickListener { showRewardedVideoAd() }
        }

        refreshNativeAd()
    }

    private fun showInterstitial() {
        InterstitialAd.load(
            requireContext(),
            "ca-app-pub-3940256099942544/1033173712",
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d("AdsFragment", adError.message)
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d("AdsFragment", "Ad was loaded.")
                    interstitialAd.show(requireActivity())
                }
            }
        )
    }

    private fun showRewardedVideoAd() = with(requireActivity()) {
        RewardedAd.load(
            this,
            "ca-app-pub-3940256099942544/5224354917",
            AdRequest.Builder().build(),
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d("AdsFragment", adError.message)
                }

                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    Log.d("AdsFragment", "Video Ad was loaded.")
                    rewardedAd.show(requireActivity()) { item ->
                        Toast.makeText(
                            requireContext(),
                            "Rewarded! Type: ${item.type}, amount: ${item.amount}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
    }

    /**
     * Populates a [NativeAdView] object with data from a given
     * [NativeAd].
     *
     * @param nativeAd the object containing the ad's assets
     * @param adView the view to be populated
     */
    private fun populateNativeAdView(nativeAd: NativeAd, adView: NativeAdView) {
        // Set the media view.
        adView.mediaView = adView.findViewById<MediaView>(R.id.ad_media)

        // Set other ad assets.
        adView.headlineView = adView.findViewById(R.id.ad_headline)
        adView.bodyView = adView.findViewById(R.id.ad_body)
        adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)
        adView.iconView = adView.findViewById(R.id.ad_app_icon)
        adView.priceView = adView.findViewById(R.id.ad_price)
        adView.starRatingView = adView.findViewById(R.id.ad_stars)
        adView.storeView = adView.findViewById(R.id.ad_store)
        adView.advertiserView = adView.findViewById(R.id.ad_advertiser)

        // The headline and media content are guaranteed to be in every UnifiedNativeAd.
        (adView.headlineView as TextView).text = nativeAd.headline
        adView.mediaView.setMediaContent(nativeAd.mediaContent)

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.body == null) {
            adView.bodyView.visibility = View.INVISIBLE
        } else {
            adView.bodyView.visibility = View.VISIBLE
            (adView.bodyView as TextView).text = nativeAd.body
        }

        if (nativeAd.callToAction == null) {
            adView.callToActionView.visibility = View.INVISIBLE
        } else {
            adView.callToActionView.visibility = View.VISIBLE
            (adView.callToActionView as Button).text = nativeAd.callToAction
        }

        if (nativeAd.icon == null) {
            adView.iconView.visibility = View.GONE
        } else {
            (adView.iconView as ImageView).setImageDrawable(
                nativeAd.icon.drawable
            )
            adView.iconView.visibility = View.VISIBLE
        }

        if (nativeAd.price == null) {
            adView.priceView.visibility = View.INVISIBLE
        } else {
            adView.priceView.visibility = View.VISIBLE
            (adView.priceView as TextView).text = nativeAd.price
        }

        if (nativeAd.store == null) {
            adView.storeView.visibility = View.INVISIBLE
        } else {
            adView.storeView.visibility = View.VISIBLE
            (adView.storeView as TextView).text = nativeAd.store
        }

        if (nativeAd.starRating == null) {
            adView.starRatingView.visibility = View.INVISIBLE
        } else {
            (adView.starRatingView as RatingBar).rating = nativeAd.starRating!!.toFloat()
            adView.starRatingView.visibility = View.VISIBLE
        }

        if (nativeAd.advertiser == null) {
            adView.advertiserView.visibility = View.INVISIBLE
        } else {
            (adView.advertiserView as TextView).text = nativeAd.advertiser
            adView.advertiserView.visibility = View.VISIBLE
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.
        adView.setNativeAd(nativeAd)

        // Get the video controller for the ad. One will always be provided, even if the ad doesn't
        // have a video asset.
        val vc = nativeAd.mediaContent.videoController

        // Updates the UI to say whether or not this ad has a video asset.
        if (vc.hasVideoContent()) {
            // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
            // VideoController will call methods on this object when events occur in the video
            // lifecycle.
            vc.videoLifecycleCallbacks = object : VideoController.VideoLifecycleCallbacks() {
                override fun onVideoEnd() {
                    // Publishers should allow native ads to complete video playback before
                    // refreshing or replacing them with another ad in the same UI location.
                    binding.refreshButton.isEnabled = true
                    binding.videostatusText.text = "Video status: Video playback has ended."
                    super.onVideoEnd()
                }
            }
        } else {
            binding.videostatusText.text = "Video status: Ad does not contain a video asset."
            binding.refreshButton.isEnabled = true
        }
    }

    /**
     * Creates a request for a new native ad based on the boolean parameters and calls the
     * corresponding "populate" method when one is successfully returned.
     *
     */
    private fun refreshNativeAd() = with(requireActivity()) {
        binding.refreshButton.isEnabled = false

        val builder = AdLoader.Builder(this, ADMOB_NATIVE_AD_UNIT_ID)

        builder.forNativeAd { nativeAd ->
            // OnUnifiedNativeAdLoadedListener implementation.
            // If this callback occurs after the activity is destroyed, you must call
            // destroy and return or you may get a memory leak.
            var activityDestroyed = false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                activityDestroyed = isDestroyed
            }
            if (activityDestroyed || isFinishing || isChangingConfigurations) {
                nativeAd.destroy()
                return@forNativeAd
            }
            // You must call destroy on old ads when you are done with them,
            // otherwise you will have a memory leak.
            currentNativeAd?.destroy()
            currentNativeAd = nativeAd
            val adView = layoutInflater
                .inflate(R.layout.native_ad, null) as NativeAdView
            populateNativeAdView(nativeAd, adView)
            binding.adFrame.removeAllViews()
            binding.adFrame.addView(adView)
        }

        val videoOptions = VideoOptions.Builder()
            .setStartMuted(binding.startMutedCheckbox.isChecked)
            .build()

        val adOptions = NativeAdOptions.Builder()
            .setVideoOptions(videoOptions)
            .build()

        builder.withNativeAdOptions(adOptions)

        val adLoader = builder.withAdListener(object : AdListener() {
            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                val error =
                    """
           domain: ${loadAdError.domain}, code: ${loadAdError.code}, message: ${loadAdError.message}
          """"
                binding.refreshButton.isEnabled = true
                Toast.makeText(
                    requireContext(),
                    "Failed to load native ad with error $error",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }).build()

        adLoader.loadAd(AdRequest.Builder().build())

        binding.videostatusText.text = ""
    }

    override fun onDestroy() {
        currentNativeAd?.destroy()
        super.onDestroy()
    }
}