package com.shong.stringconverter

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val stringConverter = StringConverter(this)
        val textView = findViewById<TextView>(R.id.textView)
        textView.setText(stringConverter.colorChange(textView.text.toString(), "sHong", "#00FF00"))

        val textView2 = findViewById<TextView>(R.id.textView2)
        textView2.setText(stringConverter.fontChange(textView2.text.toString(), "sHong"))

        val txt = "- 아이유 : https://www.melon.com/artist/timeline.htm?artistId=261143   " +
                "- 블랙핑크 : https://www.melon.com/artist/timeline.htm?artistId=995169"
        findViewById<TextView>(R.id.hyperlinkTextView).text = stringConverter.urlHyperLink(txt)
    }

    //기획안대로 정렬
//    fun sortForProposal(contactList: MutableList<Contact>): MutableList<Contact>{
//        //rep 분류
//        val erasedContactList = mutableListOf<Contact>()
//        for (c in contactList) {
//            if (c.mobile?.isNotEmpty() == true){
//                c.phoneRepType = 0
//                erasedContactList.add(c)
//            } else if (c.work?.isNotEmpty() == true){
//                c.phoneRepType = 1
//                erasedContactList.add(c)
//            } else if (c.home?.isNotEmpty() == true){
//                c.phoneRepType = 2
//                erasedContactList.add(c)
//            }
//        }
//
//        //정렬
//        val rawMap = mutableMapOf<String, MutableList<Contact>>()
//        for (c in erasedContactList) {
//            val iw = wordOperator.getInitial(c.name).initalWord
//            rawMap[iw]?.add(c) ?: run { rawMap[iw] = mutableListOf(c) }
//        }
//        val sortedKeyList = rawMap.keys.sortedWith(Comparator(stringSortation::compare))
//        val sortedContactList = mutableListOf<Contact>()
//        for (key in sortedKeyList) {
//            sortedContactList += rawMap[key]?.sortedWith(Comparator(stringSortation::compareContact))
//                ?: continue
//        }
//        return sortedContactList
//    }
}