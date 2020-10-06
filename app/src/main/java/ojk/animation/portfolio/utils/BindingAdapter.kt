package ojk.animation.portfolio.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.res.Resources
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.widget.LinearLayoutCompat
import android.graphics.Typeface
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout


object BindingAdapter {
    @BindingAdapter("android:srcGlide")
    @JvmStatic
    fun setImageViewResource(imageView: ImageView, imageUri: String?) {
        Glide.with(imageView.context).load(imageUri)
                .apply(RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                .into(imageView)
    }
    @BindingAdapter("android:srcGlide")
    @JvmStatic
    fun setImageViewResource(imageView: ImageView, imageUri: Int?) {
        Glide.with(imageView.context).load(imageUri)
                .apply(RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                .into(imageView)
    }

    @BindingAdapter(value = ["app:rectColor","app:rectRoundRadius"], requireAll = true)
    @JvmStatic
    fun setRoundedRectBackground(view : View, bgColor: String, roundRadius: Int ) {
        val gd = GradientDrawable()
        gd.setColor(Color.parseColor(bgColor))
        gd.cornerRadius = (roundRadius.toFloat() * Resources.getSystem().getDisplayMetrics().density);
        view.background = gd
    }

    @BindingAdapter(value = ["app:rectColor","app:rectRoundRadius", "app:strockColor", "app:strockWidth"], requireAll = true)
    @JvmStatic
    fun setRoundedRectBackground(view : View, bgColor: String, roundRadius: Int, strockColor: String, strockWidth: Int ) {
        val gd = GradientDrawable()
        gd.setColor(Color.parseColor(bgColor))
        gd.cornerRadius = (roundRadius.toFloat() * Resources.getSystem().getDisplayMetrics().density);
        gd.setStroke(strockWidth * Resources.getSystem().getDisplayMetrics().density.toInt(), Color.parseColor(strockColor))
        view.background = gd
    }


    @BindingAdapter(value = ["android:changeWeight"], requireAll = true)
    @JvmStatic
    fun changeWeight(view : View, weight : Float ) {
        val params = LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                weight)
        view.layoutParams = params
    }

    @BindingAdapter(value = ["android:constraintHorizontalChangeWeight"], requireAll = true)
    @JvmStatic
    fun constraintChangeWeight(view : View, weight : Float ) {
        val params = view.layoutParams as ConstraintLayout.LayoutParams
        params.horizontalWeight = weight
        view.layoutParams = params
        view.requestLayout()
    }

    @BindingAdapter(value = ["android:typeface"], requireAll = true)
    @JvmStatic
    fun setTypeface(v: TextView, style: String) {
        when (style) {
            "bold" -> v.setTypeface(null, Typeface.BOLD)
            else -> v.setTypeface(null, Typeface.NORMAL)
        }
    }


    @BindingAdapter(value = ["android:fadeVisible"], requireAll = true)
    @JvmStatic
    fun setFadeVisible(view: View, visible: Int) {
        view.animate().cancel()

        if (visible == View.VISIBLE ) {
            view.animate().alpha(1f).setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    view.setAlpha(1f)
                }
            })
        } else {
            view.animate().alpha(0f).setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    view.setAlpha(0f)
                }
            })
        }
    }

    @BindingAdapter( value = ["android:topBtmGradStart" ,"android:topBtmGradEnd" ], requireAll = true )
    @JvmStatic
    fun setTopBtmGradationBg( view: View , topBtmGradStart : Int , topBtmGradEnd : Int ){
        setTopBtmGradationBg(view, topBtmGradStart, 0, topBtmGradEnd, 0f, 0f)
    }

    @BindingAdapter( value = ["android:topBtmGradStart","android:topBtmGradCenter" ,"android:topBtmGradEnd" ], requireAll = true )
    @JvmStatic
    fun setTopBtmGradationBg( view: View , topBtmGradStart : Int , topBtmGradCenter : Int , topBtmGradEnd : Int ){
        setTopBtmGradationBg(view, topBtmGradStart, topBtmGradCenter, topBtmGradEnd, 0f, 0f)
    }

    @BindingAdapter( value = ["android:topBtmGradStart","android:topBtmGradCenter" ,"android:topBtmGradEnd","android:topBtmGradCenterX" ], requireAll = true )
    @JvmStatic
    fun setTopBtmGradationBg( view: View , startC : Int , centerC : Int , endC : Int , centerX : Float ){
        setTopBtmGradationBg(view, startC, centerC, endC, centerX, 0f)
    }

    @BindingAdapter( value = ["android:topBtmGradStart","android:topBtmGradCenter" ,"android:topBtmGradEnd","android:topBtmGradCenterX" ,"android:topBtmGradRadius" ], requireAll = true )
    @JvmStatic
    fun setTopBtmGradationBg( view: View , startC : Int , centerC : Int , endC : Int , centerX : Float , radius : Float ){
        val gd = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, if( centerC == -1 ) intArrayOf(startC,endC) else  intArrayOf(startC,centerC,endC) )
        if( centerX != 0f && startC != -1 ) gd.setGradientCenter( centerX,0f)
        gd.cornerRadius = radius
        view.setBackground(gd)
    }

    @BindingAdapter( value = ["app:btnSelect"] )
    @JvmStatic
    fun btnSelected( view: View , btnSelect : Boolean ){
        view.isSelected = btnSelect
    }



    @BindingAdapter(value = ["android:constraintBottomMarginChange"], requireAll = true)
    @JvmStatic
    fun constraintChangeBottomMargin(view : View, margin : Float ) {
        val params = view.layoutParams as ConstraintLayout.LayoutParams
        params.bottomMargin = margin.toInt()
        view.layoutParams = params
        view.requestLayout()
    }

    @BindingAdapter(value = ["android:constraintTopMarginChange"], requireAll = true)
    @JvmStatic
    fun constraintChangeTopMargin(view : View, margin : Float ) {
        val params = view.layoutParams as ConstraintLayout.LayoutParams
        params.topMargin = margin.toInt()
        view.layoutParams = params
        view.requestLayout()
    }
    @BindingAdapter(value = ["android:constraintStartMarginChange"], requireAll = true)
    @JvmStatic
    fun constraintStartMarginChange(view : View, margin : Float ) {
        val params = view.layoutParams as ConstraintLayout.LayoutParams
        params.marginStart = margin.toInt()
        view.layoutParams = params
        view.requestLayout()
    }
    @BindingAdapter(value = ["android:constraintEndMarginChange"], requireAll = true)
    @JvmStatic
    fun constraintEndMarginChange(view : View, margin : Float ) {
        val params = view.layoutParams as ConstraintLayout.LayoutParams
        params.marginEnd = margin.toInt()
        view.layoutParams = params
        view.requestLayout()
    }


    /**
     * layout_height, layout_width 기본 속성에 데이터바인딩은 에러가 나는 경우가 존재함
     * 위 값은 디폴트값으로 지정뒤 데이터바인딩용으로 아래 속성값에 값을 집어넣어서 처리 할 수 있도록 함
     */
    @BindingAdapter(value = ["app:layoutHeight"], requireAll = true)
    @JvmStatic
    fun setLayoutHeight(view : View, pixel : Float ) {
        val layoutParams = view.layoutParams
        layoutParams.height = pixel.toInt()
        view.layoutParams = layoutParams
    }

    @BindingAdapter(value = ["app:layoutWidth"], requireAll = true)
    @JvmStatic
    fun setLayoutWidth(view : View, pixel : Float ) {
        val layoutParams = view.layoutParams
        layoutParams.width = pixel.toInt()
        view.layoutParams = layoutParams
    }


    @BindingAdapter(value = ["app:onTap"], requireAll = true)
    @JvmStatic
    fun addOnTapListener(view : View, listener : View.OnClickListener) {
        view.setOnClickListener(listener);
    }
    @BindingAdapter(value = ["app:onTap"], requireAll = true)
    @JvmStatic
    fun addOnTapListener(view : ViewGroup, listener : View.OnClickListener) {
        view.setOnClickListener(listener);
    }


}