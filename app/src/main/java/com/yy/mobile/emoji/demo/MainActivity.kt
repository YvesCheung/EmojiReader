package com.yy.mobile.emoji.demo

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import com.yy.mobile.emoji.EmojiReader
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        et_input.addTextChangedListener(object : TextWatcher {
            @SuppressLint("SetTextI18n")
            override fun afterTextChanged(s: Editable?) {
                s ?: return

                val outputUnicode = EmojiReader.transToUnicode(s)
                tv_output.text = outputUnicode.joinToString()

                val textLength = EmojiReader.getTextLength(s)
                tv_text_len.text = "文本长度：$textLength"

                if (s.isNotEmpty()) {
                    val cutText = EmojiReader.subSequence(s, Math.min(s.length, 10))
                    tv_cut_output.text = cutText.toString()
                } else {
                    tv_cut_output.text = ""
                }

                if (s.isNotEmpty()) {
                    val isEmoji = EmojiReader.isEmojiOfCharIndex(s, s.length - 1)
                    tv_is_emoji.text = "最后输入的是否Emoji：" + if (isEmoji) "是" else "否"
                } else {
                    tv_is_emoji.text = ""
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //Do nothing.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //Do nothing.
            }
        })
    }
}
