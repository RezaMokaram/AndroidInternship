package com.partSoftware.heliumplus.core.common

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.partSoftware.heliumplus.R
import com.partSoftware.heliumplus.core.data.Constants
import com.partSoftware.heliumplus.core.data.ErrorResponse
import com.partSoftware.heliumplus.core.data.ErrorsResponse
import com.partSoftware.heliumplus.core.networkUtils.ServerException
import com.partSoftware.heliumplus.databinding.LayoutSnackbarBinding
import com.partSoftware.heliumplus.features.article.ui.model.TagView
import retrofit2.Response

fun Context.showSnackBar(view: View, text: String) {
    val snackBar = Snackbar.make(view, "", Snackbar.LENGTH_LONG)
    val binding = LayoutSnackbarBinding.inflate(
        LayoutInflater.from(this), null, false
    )
    snackBar.view.setBackgroundColor(Color.TRANSPARENT)
    val snackBarLayout = snackBar.view as Snackbar.SnackbarLayout
    snackBarLayout.setPadding(0, 0, 0, 0)
    binding.tvMessageLayoutSnackBar.text = text
    snackBarLayout.addView(binding.root, 0)
    snackBar.show()
}

fun NavController.safeNavigateTo(directions: NavDirections) {
    try {
        navigate(directions)
    } catch (exception: Exception) {
        Log.e(Constants.ERROR_NAVIGATION_TAG, exception.message.toString())
    }
}

fun ChipGroup.addDynamicChips(
    tags: List<TagView>?,
    isSingleSelection: Boolean,
    isBottomSheet: Boolean
) {
    this.removeAllViews()
    this.isSingleSelection = isSingleSelection
    if (tags != null) {
        for (tag in tags) {
            val chip = Chip(context)
            chip.apply {
                text = tag.name
                id = tag.id ?: 0
                setChipStrokeColorResource(R.color.selector_chip_stroke)
                if (!isSingleSelection) {
                    isChecked = if (isBottomSheet)
                        tag.isSelected
                    else
                        tag.isChecked
                }

            }

            this.addView(chip)
        }
    }
}

fun ChipGroup.addDynamicChipsSearch(tags: List<TagView>?, styleResource: Int) {
    this.removeAllViews()
    if (tags != null) {
        for (tag in tags) {
            val chip = Chip(ContextThemeWrapper(context, styleResource), null, 0)
            chip.text = tag.name
            chip.id = tag.id ?: 0
            val chipDrawable = ChipDrawable.createFromAttributes(
                context,
                null,
                0,
                styleResource
            )
            chip.setChipDrawable(chipDrawable)
            this.addView(chip)
        }
    }
}

fun <T> Response<T>.bodyOrThrow(): T? {
    if (this.isSuccessful)
        return body()
    else {
        val gson = Gson()
        var errorMessage = ""
        val str = errorBody()?.string()
        try {
            val errorResponse = gson.fromJson(
                str,
                ErrorResponse::class.java
            )
            errorMessage = errorResponse.message
        } catch (e: Exception) {
            val errorsResponse = gson.fromJson(
                str,
                ErrorsResponse::class.java
            )
            errorsResponse.message?.forEach { error ->
                errorMessage += "-$error\n"
            }
        }
        throw ServerException(code(), errorMessage)
    }
}
