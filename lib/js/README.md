# emoji-reader

A simple tool to recognize Emoji in string.

> [Preview: https://emoji-reader.netlify.app](https://emoji-reader.netlify.app)

## Feature

- Support **Unicode 12** specification, [click here to view] [1]

- The 'Emoji' judgment based on **EBNF** state machine, which is easier to maintain than regular expressions

- Accurately judge the length of the string containing 'Emoji'

- Exact cut string safely without breaking 'Emoji'

## String Length

| Emoji | String.length | EmojiReader.getTextLength |
| :----:| :----: | :----: |
| ♀ | 1 | 1 |
| 🙂 | 2 | 1 |
|👱‍♂|5|1|
|🏳️‍🌈|6|1|
| 👨‍👩‍👦‍👦 | 11| 1 |

Within a string, an 'Emoji' consists of either one or more Unicode code points. 
A code point may consist of multiple characters (depending on whether the code point is greater than 0x010000).
Therefore, an 'Emoji' may consist of several characters.

Many businesses need to judge the number of words, such as the user's nickname can not be too long, 
the speech content has the word limit and so on. 
If you do not make special treatment for 'Emoji', it will not meet the user's expectation.

Use ``EmojiReader.getTextLength`` to obtain the length of the visual symbol of the text. 
The length of an 'Emoji' is 1.

```java
//Java
String strWithEmoji = "我是一个😃";
int error = strWithEmoji.length(); //6
int correct = EmojiReader.getTextLength(strWithEmoji); //5
```

```javascript
//JavaScript
const strWithEmoji = '我是一个😃';
const error = strWithEmoji.length; //6
const correct = require('emoji-reader').getTextLength(strWithEmoji); //5
```

## SubString

When the display text is too long, we usually omit the text at the end and add an ellipsis.

However, if the string contains 'Emoji', it is likely to cut the 'Emoji' into segments and turn it into garbled code. 

For example, the following string:

> "I am 🙂😐😎💏"

After invoke `String.subString(0, 8)` will become:

> "I am 🙂?"

Because the display of 'Emoji' is completed by the combination of multiple Unicode code points. 
After `subString`, the remaining Unicode code points will show mess symbol that cannot be displayed normally.

Using `EmojiReader.subSequence`, the String can be cropped correctly according to the visual expectation.

```java
//JavaScript
import EmojiReader from 'emoji-reader';
//Java
import com.yy.mobile.emoji.EmojiReader;

EmojiReader.subSequence("I am 🙂😐😎💏", 0, 8) == "I am 🙂😐😎"
```

## Install (for Js)

```
npm install --save emoji-reader
```

## Install (for Java/Android)

Add in `build.gradle` :

```Groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
    api 'com.github.YvesCheung.EmojiReader:lib:x.y.z'
}
```

## Source Code

[https://github.com/YvesCheung/EmojiReader][2]

  [1]: https://www.unicode.org/reports/tr51/
  [2]: https://github.com/YvesCheung/EmojiReader