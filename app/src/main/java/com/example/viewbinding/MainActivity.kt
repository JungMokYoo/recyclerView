package com.example.viewbinding


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.viewbinding.databinding.ActivityMainBinding
import android.view.View
import android.view.animation.AlphaAnimation
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val dataList = mutableListOf<MyItem>()
        dataList.add(MyItem(R.drawable.sampleone, R.string.item_one, R.string.addr_one ,1000,R.drawable.rerere,"25",R.drawable.heart,"13",R.string.seller_one,R.string.comment_one))
        dataList.add(MyItem(R.drawable.sample2, R.string.item_two, R.string.addr_two ,20000,R.drawable.rerere,"28",R.drawable.heart,"8",R.string.seller_two,R.string.comment_two))
        dataList.add(MyItem(R.drawable.sample3,R.string.item_three , R.string.addr_three ,10000,R.drawable.rerere,"5",R.drawable.heart,"23",R.string.seller_three,R.string.comment_three))
        dataList.add(MyItem(R.drawable.sample4,R.string.item_four , R.string.addr_four ,10000,R.drawable.rerere,"17",R.drawable.heart,"14",R.string.seller_four,R.string.comment_four))
        dataList.add(MyItem(R.drawable.sample5, R.string.item_five, R.string.addr_five ,150000,R.drawable.rerere,"9",R.drawable.heart,"22",R.string.seller_five,R.string.comment_five))
        dataList.add(MyItem(R.drawable.sample6, R.string.item_six, R.string.addr_six ,50000,R.drawable.rerere,"16",R.drawable.heart,"25",R.string.seller_six,R.string.comment_six))
        dataList.add(MyItem(R.drawable.sample7, R.string.item_seven, R.string.addr_seven ,150000,R.drawable.rerere,"54",R.drawable.heart,"142",R.string.seller_seven,R.string.comment_seven))
        dataList.add(MyItem(R.drawable.sample8, R.string.item_eight, R.string.addr_eight ,180000,R.drawable.rerere,"7",R.drawable.heart,"31",R.string.seller_eight,R.string.comment_eight))
        dataList.add(MyItem(R.drawable.sample9, R.string.item_nine, R.string.addr_nine ,30000,R.drawable.rerere,"28",R.drawable.heart,"7",R.string.seller_nine,R.string.comment_nine))
        dataList.add(MyItem(R.drawable.sample10, R.string.item_ten, R.string.addr_ten ,190000,R.drawable.rerere,"6",R.drawable.heart,"40",R.string.seller_ten,R.string.comment_ten))



        val adapter = MyAdapter(dataList)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        adapter.itemClick = object : MyAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {

                val selectItem = dataList[position]
//               val name: Int = dataList[position].aName
//                Toast.makeText(this@MainActivity," $name 선택!", Toast.LENGTH_SHORT).show()

                val intent = Intent(this@MainActivity,SecondActivity::class.java).apply{
                    putExtra("Post",selectItem)

                }
                startActivity(intent)
            }
        }

        binding.bell.setOnClickListener{
            notification()
        }

        // 스크롤 상단이동
        val floatingbtn = binding.floatingbtn
        val fadeIn = AlphaAnimation(0f, 1f).apply { duration = 300 }
        val fadeOut = AlphaAnimation(1f, 0f).apply { duration = 300 }
        var isTop = true

        // fadeIn, fadeOut
        binding.recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(!binding.recyclerView.canScrollVertically(-1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    binding.floatingbtn.visibility = View.GONE
                    isTop = true
                } else if(isTop) {
                    floatingbtn.visibility = View.VISIBLE
                    isTop = false
                }
            }
        })

        floatingbtn.setOnClickListener {
            binding.recyclerView.smoothScrollToPosition(0)
        }

        //상품삭제
        adapter.itemLongClick = object : MyAdapter.ItemLongClick {
            override fun onLongClick(view: View, position: Int) {
                val itemRomove = dataList[position]
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("삭제")
                    .setMessage("삭제하시겠습니까?")
                    .setPositiveButton("삭제") { dialog, _ ->
                        dataList.remove(itemRomove)
                        adapter.notifyDataSetChanged()
                        dialog.dismiss()
                    }
                    .setNegativeButton("취소") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }


    }
    //노티피케이션
    fun notification(){
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val builder: NotificationCompat.Builder
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channelId="one-channel"
            val channelName="My Channel One"
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "My Channel One Description"
                setShowBadge(true)
                val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val audioAttributes = AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    //.setUsage(AudioAttributes.USAGE_ALARM)
                    .build()
                setSound(uri, audioAttributes)
                enableVibration(false)
            }
            manager.createNotificationChannel(channel)
            builder = NotificationCompat.Builder(this, channelId)
        }else {

            builder = NotificationCompat.Builder(this)
        }

       // val bitmap = BitmapFactory.decodeResource(resources, R.drawable.heart)

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        builder.run {
            setSmallIcon(R.mipmap.ic_launcher)
            setWhen(System.currentTimeMillis())
            setContentTitle("키워드 알림")
            setContentText("설정한 키워드에 대한 알림이 도착했습니다.!!")
                      //  addAction(R.mipmap.ic_launcher, "Action", pendingIntent)
        }

        manager.notify(11, builder.build())
    }
        //다이얼로그
    override fun onBackPressed() {
        var builder =AlertDialog.Builder(this)
        builder.setTitle("종료")
        builder.setMessage("정말 종료하시겠습니까?")
        builder.setIcon(R.drawable.rerere)
        builder.setPositiveButton("확인") { _, _ ->

                super.onBackPressed()
            }
        builder.setNegativeButton("취소", null)
        builder.show()
    }


}