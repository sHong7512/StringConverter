package com.shong.stringconverter

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.TypefaceSpan
import android.util.Log
import android.widget.EditText
import androidx.core.content.res.ResourcesCompat.getFont
import java.net.MalformedURLException
import java.net.URISyntaxException
import java.net.URL
import java.util.regex.Matcher
import java.util.regex.Pattern

class StringConverter(val context: Context) {
    val TAG = this::class.java.simpleName + "_sHong"

    //텍스트 색상 변환
    internal fun colorChange(_str: String, str_span: String, colorString: String): SpannableString {
        var str = _str
        val spannableString = SpannableString(str)
        val startList = mutableListOf<Int>()
        val endList = mutableListOf<Int>()

        var index_buf = 0
        while (true) {
            val start = str.indexOf(str_span)
            val end = start + str_span.length

            if (start < 0) break

            str = str.substring(end)

            startList.add(start + index_buf)
            endList.add(end + index_buf)
            index_buf += end
        }

        try {
            for (i in startList.indices) {
                spannableString.setSpan(
                    ForegroundColorSpan(Color.parseColor(colorString)),
                    startList[i],
                    endList[i],
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        } catch (e: Exception) {
            Log.e(TAG, "SetSpan ERROR! $e")
        }

        return spannableString
    }

    //텍스트 폰트 변환
    internal fun fontChange(_str: String, str_span: String): SpannableString {
        var str = _str
        val spannableString = SpannableString(str)
        val startList = mutableListOf<Int>()
        val endList = mutableListOf<Int>()

        var index_buf = 0
        while (true) {
            val start = str.indexOf(str_span)
            val end = start + str_span.length

            if (start < 0) break

            str = str.substring(end)

            startList.add(start + index_buf)
            endList.add(end + index_buf)
            index_buf += end
        }

        try {
            val typeface: Typeface = Typeface.create(
                getFont(context, R.font.pretendard_extrabold),
                Typeface.BOLD
            )
            if (Build.VERSION.SDK_INT >= 28) {
                for (i in startList.indices) {
                    spannableString.setSpan(
                        TypefaceSpan(typeface),
                        startList[i],
                        endList[i],
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            } else {
                for (i in startList.indices) {
                    spannableString.setSpan(
                        CustomTypefaceSpan(typeface),
                        startList[i],
                        endList[i],
                        Spannable.SPAN_EXCLUSIVE_INCLUSIVE
                    )
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "SetSpan ERROR! $e")
        }

        return spannableString
    }

    //문자 변경
    internal fun wordChange(_str: String, str_span: String, newWord: String): String {
        var str = _str
        while (true) {
            val start = str.indexOf(str_span)
            val end = start + str_span.length

            if (start < 0) break

            val str_s = str.substring(0, start)
            val str_e = str.substring(end)
            str = str_s + newWord + str_e
        }

        return str
    }

    //EditText 전용 색상 변환
    internal fun colorChangeForEditText(
        et: EditText,
        str_span: String,
        colorString: String
    ): SpannableString {
        var str = et.text.toString()
        val spannableString = SpannableString(str)
        val startList = mutableListOf<Int>()
        val endList = mutableListOf<Int>()

        var index_buf = 0
        while (true) {
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

        try {
            for (i in startList.indices) {
                et.text.setSpan(
                    ForegroundColorSpan(Color.parseColor(colorString)),
                    startList[i],
                    endList[i],
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        } catch (e: Exception) {
            Log.e(TAG, "EditText SetSpan ERROR! $e")
        }

        return spannableString
    }

    //EditText 전용 문자 삽입 + 색상 변경
    fun insertAndColorString(et: EditText, str_insert: String, colorString: String) {
        val selection_s = et.selectionStart
        val selection_e = et.selectionEnd
        val str = et.text.toString()
        val str_s = str.substring(0, selection_s)
        val str_e = str.substring(selection_e)

        et.setText("$str_s$str_insert$str_e")
        et.setSelection(selection_s, selection_s + str_insert.length)

        colorChangeForEditText(
            et,
            "%수신자명%",
            colorString
        )
    }

    fun isUrl(text: String?): Boolean {
        val p: Pattern = Pattern.compile("^(?:https?:\\/\\/)?(?:www\\.)?[a-zA-Z0-9./]+$")
        val m: Matcher = p.matcher(text)
        if (m.matches()) return true
        var u: URL? = null
        try {
            u = URL(text)
        } catch (e: MalformedURLException) {
            Log.e(TAG, "$e")
            return false
        }
        try {
            u.toURI()
        } catch (e: URISyntaxException) {
            Log.e(TAG, "$e")
            return false
        }
        return true
    }

    fun urlHyperLink(_text: String): String {
        var text = _text
        var tmpText = text //원본을 저장
        var start = text.indexOf("http") //http문자열 위치
        var space = 0 //공백 위치
        var bodyText = ""
        var tailText = ""
        var check = false
        val urlList = ArrayList<String>()
        if (start >= 0) { //http가 존재한다면 true를 입력
            check = true
        }
        while (check) { //http가 존재한다면 실행
            bodyText = text.substring(start) //http부터 문자열 자름
            space = bodyText.indexOf(" ") //자른 문자열에서 공백위치 확인
            if (space > 0) { //공백이 존재한다면
                tailText = bodyText.substring(space) //공백 뒤 문자열 저장
                bodyText = bodyText.substring(0, space) //http 주소부분만 잘라서 tailText 부분에 입력
            } else {
                tailText = "" //공백이 없으니 뒤 문자열이 없음
            }
            urlList.add(bodyText) //http 주소를 ArrayList에 저장
            start = tailText.indexOf("http") //뒤에 http 주소가 존재하는지 확인
            if (start >= 0) {
                check = true
                text = tailText //존재한다면 뒷부분을 text에 입력
            } else {
                check = false
            }
        }
        for (url in urlList) { //ArrayList에 넣었던 http주소를 for문으로 실행
            //tmpText 문자열에서 url부분 찾아서 머리+몸통(href추가)+꼬리 만들어서 href가 삽입된 문자열 완성
            tmpText = (tmpText.substring(0, tmpText.indexOf(url)) //머리
                    + "<a href=\"" + url + "\" target=\"_blank\">" + url + "</a>" //몸통
                    + tmpText.substring(tmpText.indexOf(url) + url.length)) //꼬리
        }
        return tmpText
    }

}