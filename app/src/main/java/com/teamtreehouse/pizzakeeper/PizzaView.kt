package com.teamtreehouse.pizzakeeper

import android.content.Context
import android.graphics.*
import android.view.View
import com.teamtreehouse.pizzakeeper.data.Topping


class PizzaView(context: Context, val toppings: MutableMap<Topping, Boolean>) : View(context) {
  data class Position (var x: Float, var y: Float)
  val paint = Paint()

  override fun onDraw(canvas: Canvas) {
    val width = canvas.width.toFloat()
    val height = canvas.height.toFloat()
    val centerX = width / 2
    val centerY = height / 2
    val smallerDimension = if (width > height) height else width

    // Draw a big circle for the crust
    paint.color = 0xffdfa172.toInt()
    val pizzaRadius = smallerDimension / 2
    canvas.drawCircle(centerX, centerY, pizzaRadius, paint)

    // Draw a smaller circle for the pizza
    paint.color = 0xffebbc85.toInt()
    val smallerPizzaRadius = pizzaRadius * .85f
    canvas.drawCircle(centerX, centerY, smallerPizzaRadius, paint)

    // Draw toppings
    toppings.forEach {
      if (it.value) {
        drawTopping(canvas, smallerPizzaRadius, centerX, centerY, toppingBitmaps[it.key]!!)
      }
    }
  }

  fun drawTopping(canvas: Canvas, pizzaRadius: Float, centerX: Float, centerY: Float, bitmap: Bitmap) {
    val takenPositions = mutableListOf<Position>()
    val toppingRadius = pizzaRadius * .1f
    val toppingDiameter = (toppingRadius * 2).toInt()

    for (i in 1..36) {
      var position: Position
      var rect: Rect

      /*
      Pick a position for a topping. If it overlaps with an existing topping, pick a
      new position until we find one that doesn't overlap.
      */
      do {
        val distFromCenter = (pizzaRadius - toppingRadius - 5) * Math.random() // -5 to give some padding from the crust
        val pos = Math.random() * Math.PI * 2
        val x = Math.cos(pos) * distFromCenter
        val y = Math.sin(pos) * distFromCenter
        val left = (centerX + x.toFloat() - toppingRadius).toInt()
        val top = (centerY + y.toFloat() - toppingRadius).toInt()
        position = Position(left + toppingRadius, top + toppingRadius)
        rect = Rect(left, top, left + toppingDiameter, top + toppingDiameter)
      } while (isTouching(position, takenPositions, toppingDiameter.toFloat()))

      // Rotate the topping randomly around its center
      val randAngle = 360 * Math.random().toFloat()
      val matrix = Matrix()
      matrix.preScale(.25f, .25f)
      matrix.preRotate(randAngle, bitmap.width/2f, bitmap.height/2f)
      matrix.postTranslate(rect.left*1f, rect.top*1f)
      canvas.drawBitmap(bitmap, matrix, paint)
      takenPositions.add(position)
    }
  }

  private fun isTouching(position: Position, takenSpots: List<Position>, diameter: Float): Boolean {
    takenSpots.forEach {
      if (distBetweenPoints(position, it) < diameter)
        return true
    }
    return false
  }

  private fun distBetweenPoints(p1: Position, p2: Position) =
      Math.sqrt(Math.pow((p1.x - p2.x).toDouble(), 2.0) + Math.pow((p1.y - p2.y).toDouble(), 2.0)).toFloat()
}
