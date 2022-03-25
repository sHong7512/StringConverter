package com.shong.stringconverter

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.EditText

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

    //EditText 전용 색상 변환
    internal fun colorChangeForEditText(et: EditText, str_span: String, colorString: String): SpannableString {
        var str = et.text.toString()
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

        et.text?.let { editable ->
            // binding.editText.text 에 추가된 모든 span 들을 리스트로 얻는다.
            val spans = editable.getSpans(0, editable.length, ForegroundColorSpan::class.java)
            spans.forEach { span ->
                editable.removeSpan(span)
            }
        }

        try{
            for (i in startList.indices){
                et.text.setSpan(ForegroundColorSpan(Color.parseColor(colorString)),
                    startList[i],
                    endList[i],
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }catch (e: Exception){
            Log.e(TAG, "EditText SetSpan ERROR! $e")
        }

        return spannableString
    }

}