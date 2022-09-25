package com.udacity.asteroidradar

import android.media.Image
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.main.AsteroidsAdapter

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

/////////////////////////////
@BindingAdapter("fillList")
fun fill(recyclerView: RecyclerView,list: List<Asteroid>?) {
val adapter=recyclerView.adapter as AsteroidsAdapter
    adapter.submitList(list)
}
/////////
@BindingAdapter("imageOfTheDay")  // set image
fun imageOfTheDay(imageView: ImageView,url:String?)
{
    if(url!=null)
    Picasso.
    get().
    load(url).
    placeholder(R.drawable.placeholder_picture_of_day ).
    error(R.drawable.ic_broken_image).
    into(imageView)
}
//////////// progressbar
@BindingAdapter("progressStatus")
fun progressStatus(view: View, image: com.udacity.asteroidradar.domain.Image?) {
    view.visibility =
        if (image==null)
            View.VISIBLE
        else
            View.GONE
}
@BindingAdapter("description")
fun potentiallyHazardous(imageView: ImageView,asteroid: Asteroid)
{
    if(asteroid.isPotentiallyHazardous)
        imageView.contentDescription= "Potentially Hazardous Asteroid"
    else
        imageView.contentDescription="not Potentially Hazardous Asteroid"
}