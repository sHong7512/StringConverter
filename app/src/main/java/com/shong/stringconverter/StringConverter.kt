package com.shong.stringconverter

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log

class StringConverter(){
    val TAG = this::class.java.simpleName + "_sHong"
    //텍스트 색상 변환
    internal fun colorChange(_str: String, str_span: String, colorString: String): SpannableString {
        var str = _str
        val spannableString = SpannableString(str)
        val startList = mutableListOf<Int>()
        val endList = mutableListOf<Int>()

        var index_buf = 0
        while (true){
            val start = str.indexOf(str_span)
            val end = start + str_span.length

            if (start < 0) break

            str = str.substring(end)

            startList.add(start + index_buf)
            endList.add(end + index_buf)
            index_buf += end
        }

        try{
            for (i in startList.indices){
                spannableString.setSpan(
                    ForegroundColorSpan(Color.parseColor(colorString)),
                    startList[i],
                    endList[i],
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }catch (e: Exception){
            Log.e(TAG, "SetSpan ERROR! $e")
        }

        return spannableString
    }

}