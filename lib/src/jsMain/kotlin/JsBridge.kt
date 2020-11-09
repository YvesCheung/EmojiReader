import com.yy.mobile.emoji.EmojiReader

@JsName("getTextLength")
fun getTextLength(str: CharSequence) = EmojiReader.getTextLength(str)

@JsName("isEmojiOfVisionIndex")
fun isEmojiOfVisionIndex(str: CharSequence, idx: Int): Boolean = EmojiReader.isEmojiOfVisionIndex(str, idx)

@JsName("isEmojiOfCharIndex")
fun isEmojiOfCharIndex(str: CharSequence, idx: Int): Boolean = EmojiReader.isEmojiOfCharIndex(str, idx)

@JsName("subSequence")
fun subSequence(str: CharSequence, start: Int, end: Int): CharSequence = EmojiReader.subSequence(str, start, end)

@JsName("analyzeText")
fun analyzeText(str: CharSequence): List<EmojiReader.Node> = EmojiReader.analyzeText(str)

@JsName("transToUnicode")
fun transToUnicode(str: CharSequence): String = EmojiReader.transToUnicode(str).joinToString()


