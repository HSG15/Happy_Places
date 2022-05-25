package com.example.happyplaces.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.happyplaces.R
import com.example.happyplaces.activities.AddHappyPlaceActivity
import com.example.happyplaces.activities.MainActivity
import com.example.happyplaces.database.DatabaseHandler
import com.example.happyplaces.model.HappyPlaceModel
import kotlinx.android.synthetic.main.item_happy_places.view.*

//class HappyPlaceAdapter(
//    private val context : Context,
//    private var list: ArrayList<HappyPlaceModel>
//) : RecyclerView.Adapter<HappyPlaceAdapter.MyViewHolder>() {
//
//    private var onClickListener: OnClickListener? = null
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        return MyViewHolder(
//            LayoutInflater.from(context).inflate(
//                R.layout.item_happy_places,
//                parent,
//                false
//            )
//        )
//    }
//
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val model = list[position]
//        holder.bind(model)
//
////        holder.itemView.setOnClickListener {
////            if (onClickListener != null){
////                onClickListener!!.onClick(position, model)
////            }
////        }
//
//    }
//
//    override fun getItemCount() = list.size
//
//
//    fun setOnClickListener(onClickListener: OnClickListener) {
//        this.onClickListener = onClickListener
//    }
//
//    interface OnClickListener {
//        fun onClick(position: Int, model: HappyPlaceModel)
//    }
//
//     inner class MyViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView),
//        View.OnClickListener {
//        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
//        private val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
//        private val ivPlaceImage: ImageView = itemView.findViewById(R.id.iv_place_image)
//
//        fun bind(model: HappyPlaceModel) {
//            tvTitle.text = model.title
//            tvDescription.text = model.description
//            ivPlaceImage.setImageURI(Uri.parse(model.image))
//        }
//
//         override fun onClick(p0: View?) {
//             TODO("Not yet implemented")
//         }
//
//     }
//
//
//
//}


open class HappyPlaceAdapter(
    private val context: Context,
    private var list: ArrayList<HappyPlaceModel>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // TODO (Step 5: Add a variable for onClickListener interface.)
    // START
    private var onClickListener: OnClickListener? = null
    // END

    /**
     * Inflates the item views which is designed in xml layout file
     *
     * create a new
     * {@link ViewHolder} and initializes some private fields to be used by RecyclerView.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_happy_places,
                parent,
                false
            )
        )
    }

    /**
     * Binds each item in the ArrayList to a view
     *
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     *
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]

        if (holder is MyViewHolder) {
            holder.itemView.iv_place_image2.setImageURI(Uri.parse(model.image))
            holder.itemView.tvTitle.text = model.title
            holder.itemView.tvDescription.text = model.description

            // TODO (Step 8: Finally add an onclickListener to the item.)
            // START
            holder.itemView.setOnClickListener {

                if (onClickListener != null) {
                    onClickListener!!.onClick(position, model)
                }
            }
            // END
        }
    }

    /**
     * Gets the number of items in the list
     */
    override fun getItemCount(): Int {
        return list.size
    }

    fun notifyEditItem(activity: Activity, position: Int, requestCode: Int) {
        val intent = Intent(context, AddHappyPlaceActivity::class.java)
        intent.putExtra(MainActivity.EXTRA_PLACE_DETAILS, list[position])
        activity.startActivityForResult(intent, requestCode)
        notifyItemChanged(position)
    }

    fun removeAt(position: Int) {
        val dbHandler = DatabaseHandler(context)
        val isDelete = dbHandler.deleteHappyPlace(list[position])
        if (isDelete > 0){
            list.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    // TODO (Step 6: Create a function to bind the onclickListener)
    // START
    /**
     * A function to bind the onclickListener.
     */
    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    // END

    // TODO (Step 4: Create an interface for onclickListener)
    // START
    interface OnClickListener {
        fun onClick(position: Int, model: HappyPlaceModel)
    }
    // END

    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    private class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}