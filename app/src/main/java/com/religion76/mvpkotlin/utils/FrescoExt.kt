package com.religion76.mvpkotlin.utils

import android.graphics.drawable.Animatable
import android.net.Uri
import android.os.Build
import android.util.LayoutDirection
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.controller.ControllerListener
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.image.ImageInfo
import com.facebook.imagepipeline.postprocessors.IterativeBoxBlurPostProcessor
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.religion76.mvpkotlin.data.model.PostImageItem
import com.religion76.mvpkotlin.data.model.PostPreview
import timber.log.Timber

/**
 * Created by SunChao on 2019-08-14.
 */


//------ Common ------
fun SimpleDraweeView.loadImageWithResize(uri: Uri, width: Int, height: Int) {
    val request = ImageRequestBuilder.newBuilderWithSource(uri)
        .setResizeOptions(ResizeOptions(width, height))
        .build()
    val controller = Fresco.newDraweeControllerBuilder()
        .setOldController(controller)
        .setImageRequest(request)
        .build()
    this.controller = controller
}

fun SimpleDraweeView.loadImageWithBlur(uri: Uri, iterations: Int, blurRadius: Int) {

    val imageRequest = ImageRequestBuilder.newBuilderWithSource(uri)
        .setPostprocessor(IterativeBoxBlurPostProcessor(iterations, blurRadius))
        .build()

    val newController = Fresco.newDraweeControllerBuilder()
        .setOldController(controller)
        .setImageRequest(imageRequest)
        .build()

    controller = newController
}

fun SimpleDraweeView.setCornersRadii(topStart: Float, topEnd: Float, bottomStart: Float, bottomEnd: Float) {
    hierarchy?.roundingParams?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && layoutDirection == LayoutDirection.RTL) {
            it.setCornersRadii(topEnd, topStart, bottomStart, bottomEnd)
        } else {
            it.setCornersRadii(topStart, topEnd, bottomEnd, bottomStart)
        }
    }
}

fun SimpleDraweeView.loadImageWithRatio(url: String, ratio: Float) {
    Timber.d("url: $url, ratio: $ratio")

    val controller = Fresco.newDraweeControllerBuilder()
        .setOldController(controller)
        .setControllerListener(object : ControllerListener<ImageInfo> {
            override fun onFailure(id: String?, throwable: Throwable?) {
            }

            override fun onRelease(id: String?) {
            }

            override fun onSubmit(id: String?, callerContext: Any?) {
            }

            override fun onIntermediateImageSet(id: String?, imageInfo: ImageInfo?) {
            }

            override fun onIntermediateImageFailed(id: String?, throwable: Throwable?) {
            }

            override fun onFinalImageSet(id: String?, imageInfo: ImageInfo?, animatable: Animatable?) {
                val newLayoutParams = layoutParams
                newLayoutParams.height = (newLayoutParams.width * ratio).toInt()
                Timber.d("width: ${newLayoutParams.width}, height: ${newLayoutParams.height}")
                layoutParams = newLayoutParams
            }

        })
        .setUri(url)
        .build()

    setController(controller)
}

//------ App ------
fun SimpleDraweeView.loadRedditPostImage(preview: PostPreview) {
    val images = preview.images
    if (images.isEmpty()) return

    val windowWidth = CommonUtils.getWindowWidth(context)
    val resolutions = images[0].resolutions

    var prepareImage: PostImageItem? = null
    resolutions.forEach { resolution ->
        prepareImage = resolution
        if (resolution.width >= windowWidth) return@forEach
    }

    prepareImage?.let {
        val ratio = it.height.toFloat() / it.width.toFloat()
        loadImageWithRatio(it.url, ratio)
    }
}