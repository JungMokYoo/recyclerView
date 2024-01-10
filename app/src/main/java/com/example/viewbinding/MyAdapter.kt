package com.example.viewbinding

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.viewbinding.databinding.ItemRecyclerviewBinding
import java.text.NumberFormat
import java.util.Locale

class MyAdapter(val mItems: MutableList<MyItem>) : RecyclerView.Adapter<MyAdapter.Holder>() {

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    interface ItemLongClick {
        fun onLongClick(view: View, position: Int)
    }

    var itemLongClick: ItemLongClick? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        Log.d("MyAdapter", "onCreateViewHolder()")
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        //Log.d("MyAdapter","onBindViewHolder()   position = $position")
        holder.itemView.setOnClickListener {  //클릭이벤트추가부분
            itemClick?.onClick(it, position)

            holder.iconImageView.setImageResource(mItems[position].aIcon)

            // 2초간 클릭했을 때 삭제
            val longClick = android.os.Handler()
            holder.itemView.setOnLongClickListener {
                longClick.postDelayed({
                    itemLongClick?.onLongClick(it, position)
                }, 2000)
                true
            }
            //val id = position
            //Log.d("MyAdapter"," #jjj    id= $id")
        }

        val comma =
            NumberFormat.getNumberInstance(Locale.getDefault()).format(mItems[position].price)

        holder.iconImageView.setImageResource(mItems[position].aIcon)
        holder.name.setText(mItems[position].aName)
        holder.addr.setText(mItems[position].aAddr)
        holder.price.text = comma
        holder.replypic.setImageResource(mItems[position].reply)
        holder.replynum.text = mItems[position].comment
        holder.likepic.setImageResource(mItems[position].heart)
        holder.likenum.text = mItems[position].like
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    inner class Holder(val binding: ItemRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val iconImageView = binding.iconItem
        val name = binding.textItem1
        val addr = binding.textItem2
        val price = binding.textItem3
        val replypic = binding.iconItem2
        val replynum = binding.textItem4
        val likepic = binding.iconItem3
        val likenum = binding.textItem5
    }

}