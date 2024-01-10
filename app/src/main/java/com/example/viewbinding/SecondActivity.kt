package com.example.viewbinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.viewbinding.databinding.ActivitySecondBinding
import java.text.NumberFormat
import java.util.Locale


class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)
        val view = binding.root
//        setContentView(R.layout.activity_second)
        setContentView(view)


        val myItem: MyItem? = intent.getParcelableExtra("Post")
        val rcname = myItem?.aName!!.toInt()
        val rcAddr = myItem?.aAddr!!.toInt()
        val rcseller = myItem?.seller!!.toInt()
        val rcword = myItem?.words!!.toInt()
        val rcprice = myItem?.price!!.toInt()


        binding.iconItemMy.setImageResource(myItem!!.aIcon)
        binding.tvName.setText(rcseller)
        binding.textView.setText( rcAddr)
        binding.myTitle.setText(rcname)
        binding.myText.setText(rcword)
        binding.price.text = rcprice.toString()

        val comma2 = NumberFormat.getNumberInstance(Locale.getDefault()).format(rcprice)
        binding.price.text = comma2

        binding.backArrow.setOnClickListener {
           finish()
       }

    }

}