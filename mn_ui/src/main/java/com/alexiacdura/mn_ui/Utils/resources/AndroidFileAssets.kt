package com.alexiacdura.mn_ui.Utils.resources

import android.content.Context
import com.alexiacdura.mn_core.resources.FileAssets

class AndroidFileAssets(private val context: Context) : FileAssets {
    override fun loadStringAsset(path: String): String {
        return context.assets.open(path).use { it.readBytes().toString(Charsets.UTF_8) }
    }
}