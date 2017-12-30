package com.saosao.snow.oma

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.gamemainfragmentlayout.*
import java.util.LinkedHashMap

/**
 * Created by 19780 on 2017/12/30.
 */
class GameMainFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.gamemainfragmentlayout , null)
    }
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var xArray = arrayOf("2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018","2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027")//x轴
        linechars.setXY(xArray , 16f , 8 , 5)
        linechars.setXYColor(Color.parseColor("#686868"))
        linechars.setTextColor(Color.parseColor("#686868"))
        linechars1.setXY(xArray , 16f , 8 , 5)
        linechars1.setXYColor(Color.parseColor("#686868"))
        linechars1.setTextColor(Color.parseColor("#686868"))

        val map = LinkedHashMap<String, Int>()
        map.put("2010", 1)
        map.put("2011", 3)
        map.put("2012", 5)
        map.put("2013", 7)
        map.put("2014", 9)
        map.put("2015", 6)
        map.put("2016", 12)
        map.put("2017", 4)
        map.put("2018", 1)
        map.put("2019", 1)
        map.put("2020", 3)
        map.put("2021", 5)
        map.put("2022", 7)
        map.put("2023", 9)
        map.put("2024", 6)
        map.put("2025", 12)
        map.put("2026", 4)
        map.put("2027", 1)
        linechars.addLine(map , Color.parseColor("#FBD764") , 150 , "亏损")
        linechars1.addLine(map , Color.parseColor("#FBD764") , 150 , "亏损")

        val map2 = LinkedHashMap<String, Int>()
        map2.put("2010", 9)
        map2.put("2011", 15)
        map2.put("2012", 5)
        map2.put("2013", 10)
        map2.put("2014", 1)
        map2.put("2015", 5)
        map2.put("2016", 6)
        map2.put("2017", 8)
        map2.put("2018", 11)
        map2.put("2019", 9)
        map2.put("2020", 15)
        map2.put("2021", 5)
        map2.put("2022", 10)
        map2.put("2023", 1)
        map2.put("2024", 5)
        map2.put("2025", 6)
        map2.put("2026", 8)
        map2.put("2027", 11)
        linechars.addLine(map2 , Color.parseColor("#64C0B8") , 155 , "盈利")
        linechars1.addLine(map2 , Color.parseColor("#64C0B8") , 155 , "盈利")

//        val dm = DisplayMetrics()
//        // 取得窗口属性
//        activity.windowManager.defaultDisplay.getMetrics(dm)
//        dm.widthPixels
//        var params = linechars.layoutParams
//        params.width = dm.widthPixels *2
//        params.height = dm.heightPixels /2
//        linechars.layoutParams = params
//        linechars1.layoutParams = params
    }


}