import EmojiReader from 'emoji-reader'

test('emoji reader', () => {
    var text = "a😀ash😕s🙁sad☹️sd😣s😖asd😫sd😩"
    console.log(EmojiReader.isEmojiOfCharIndex(text, text.length - 1))
    console.log(EmojiReader.transToUnicode(text))
    console.log(EmojiReader.analyzeText(text))
});
