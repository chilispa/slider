/*
 * Copyright 2013, Edmodo, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with the License.
 * You may obtain a copy of the License in the LICENSE file, or at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" 
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language 
 * governing permissions and limitations under the License. 
 */

package com.chili.slider

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import kotlin.math.ceil
import kotlin.math.floor
import android.util.TypedValue

/**
 * This class represents the underlying gray bar in the RangeBar (without the
 * thumbs).
 */
internal class Bar(val leftX: Float, private val mY: Float, private val barLength: Float, ctx: Context, steps: Int, barWeight: Float, BarColor: Int) {

    private val mPaint: Paint = Paint().apply {
        this.color = BarColor
        this.strokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, barWeight, ctx.resources.displayMetrics)
        this.isAntiAlias = true
        this.alpha = 75
    }

    /**
     * Get the x-coordinate of the right edge of the bar.
     *
     * @return x-coordinate of the right edge of the bar
     */
    val rightX: Float = leftX + barLength

    private val mDeltaMinMaxValue: Float = steps.toFloat()
    private val mTickDistance: Float = barLength / mDeltaMinMaxValue

    /**
     * Draws the bar on the given Canvas.
     *
     * @param canvas Canvas to draw on; should be the Canvas passed into {#link
     * View#onDraw()}
     */
    fun draw(canvas: Canvas) {
        canvas.drawLine(this.leftX, this.mY, this.rightX, this.mY, this.mPaint)
    }

    /**
     * Gets the x-coordinate of the nearest tick to the given x-coordinate.
     *
     * @return the x-coordinate of the nearest tick
     */
    fun getNearestTickCoordinate(thumb: Thumb?): Float {

        val nearestTickIndex = getNearestTickIndex(thumb)

        return this.leftX + nearestTickIndex * this.mTickDistance
    }

    /**
     * Gets the zero-based index of the nearest tick to the given thumb.
     *
     * @param thumb the Thumb to find the nearest tick for
     * @return the zero-based index of the nearest tick
     */
    fun getNearestTickIndex(thumb: Thumb?): Int {

        val leftPx = (thumb?.x ?: 0f) - this.leftX

        val value = (this.mDeltaMinMaxValue * (leftPx / this.barLength))

        return if (leftPx > (this.rightX / 2f)) {
            ceil(value)
        } else {
            floor(value)
        }.toInt()
    }

}
