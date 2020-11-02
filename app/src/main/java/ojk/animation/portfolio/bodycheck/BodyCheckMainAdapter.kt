package ojk.animation.portfolio.bodycheck

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ojk.animation.portfolio.R
import ojk.animation.portfolio.databinding.WidgetBodycheckSymptompsBinding


class BodyState(
    val title : String,
    val resourceId: Int
)

class BodyStateHolder(
    val view: WidgetBodycheckSymptompsBinding,
    val itemClick : (BodyState) -> Unit
) : RecyclerView.ViewHolder(view.root){
    lateinit var item : BodyState;
    fun binding( item : BodyState ){
        this.item = item
        view.imgSrc = item.resourceId
        view.title = item.title
        view.bg.transitionName = "bg${item.resourceId}"
        view.image.transitionName = "${item.resourceId}"
        view.root.setOnClickListener { itemClick(item) }
    }
}

class MyAdapter(val viewModel: BodyCheckMainFragment.BodyCheckMainViewModel) : RecyclerView.Adapter<BodyStateHolder>() {
    val itemList = arrayListOf(
        BodyState( "Fever" , R.drawable.img_face_fever ),
        BodyState( "Cough" , R.drawable.img_face_cough ),
        BodyState( "Sore troath" , R.drawable.img_face_sore_troath ),
        BodyState( "Tiredness" , R.drawable.img_face_headache ),
        BodyState( "Aches" , R.drawable.img_face_sore_troath_middle ),
        BodyState( "Shortness breath" , R.drawable.img_face_comportable )
    )
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BodyStateHolder {
        return BodyStateHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.widget_bodycheck_symptomps, parent, false )
        ) {
            viewModel.onSymptopmpsClick( it.resourceId )
        }
    }

    override fun getItemCount() = itemList.size;
    override fun onBindViewHolder(holder: BodyStateHolder, position: Int) = holder.binding(itemList[position]);
}
