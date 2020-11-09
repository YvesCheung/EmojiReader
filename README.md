# EmojiReader

一个能在字符串中识别出 Emoji 的简单工具

---

[![](https://jitpack.io/v/YvesCheung/EmojiReader.svg)](https://jitpack.io/#YvesCheung/EmojiReader)


> [点此预览效果： https://emoji-reader.netlify.app](https://emoji-reader.netlify.app)

## 特性
- 支持 **Unicode12** 规范，[点此查看][1]
- 基于 **EBNF** 状态机的 `Emoji` 判断，比正则表达式更易维护
- 准确判断含有 `Emoji` 的字符串长度
- 准确切割字符串不会断开 `Emoji`

## 长度判断

| Emoji | String.length | EmojiReader.getTextLength |
| :----:| :----: | :----: |
| ♀ | 1 | 1 |
| 🙂 | 2 | 1 |
|👱‍♂|5|1|
|🏳️‍🌈|6|1|
| 👨‍👩‍👦‍👦 | 11| 1 |

在字符串中，一个 `Emoji` 由一个或多个 Unicode 码点（CodePoint）组成，一个码点可能由多个字符组成（取决于码点是否大于 0x010000），因此一个 `Emoji` 可能由数个字符组成。

![](https://i.imgur.com/80mqGiP.png)

很多业务都需要有字数的判断，比如用户昵称不能过长，发言内容有字数限制等等。如果不对 `Emoji` 进行特殊处理，往往会出现不符合用户预期的情况。

使用 ``EmojiReader.getTextLength`` 可以获取到文本的可视符号的长度，一个 `Emoji` 的长度为1。

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

## 表情切割

当显示文本过长时，通常我们会省略末尾的文本，并加上省略号。

但如果字符串中含有 `Emoji` ，切割字符串就很可能把 `Emoji` 切段，变成乱码。比如下面这个字符串：

> "我是🙂😐😎💏"

经过 String.subString(0, 5) 处理后：

> "我是🙂?"

因为多个 Unicode 码点共同组合才能完成一个 `Emoji` 的展示，通过切割后剩下的 Unicode 码点会表现出无法正常显示的乱码。

使用 `EmojiReader.subSequence` 可以按照一个 `Emoji` 长度为1来进行符合视觉预期的裁剪。

```java
//JavaScript
import EmojiReader from 'emoji-reader'
//Java
import com.yy.mobile.emoji.EmojiReader

EmojiReader.subSequence("我是🙂😐😎💏", 0, 5) == "我是🙂😐😎"
```

## 安装 (Javascript)

```
npm install --save emoji-reader
```

## 安装 (Java/Android)

1. 根目录的 build.gradle 添加：
    ```Groovy
    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
    ```

2. 使用的模块的 build.gradle 中添加：
    ```Groovy
    dependencies {
        api 'com.github.YvesCheung.EmojiReader:lib:x.y.z'
    }
    ```

    > 其中x.y.z 版本替换为 [![](https://jitpack.io/v/YvesCheung/EmojiReader.svg)](https://jitpack.io/#YvesCheung/EmojiReader)

## 原理

Unicode 规范文档中给出了 `Emoji` 的语法，是一个EBNF范式的表达：

```
possible_emoji :=
    flag_sequence
    | zwj_element (\x{200D} zwj_element)+
     
flag_sequence :=
    \p{RI} \p{RI}
     
zwj_element :=
    \p{Emoji} emoji_modification?

emoji_modification :=
    \p{EMod}
    | \x{FE0F} \x{20E3}?
    | tag_modifier

tag_modifier :=
    [\x{E0020}-\x{E007E}]+ \x{E007F}
```

这里简单地解释一下：

#### Emoji只有三种形式

#### 第一种是国旗类的，由两个国家区域符组成

- 两个区域符号组成国旗的样例

![](https://i.imgur.com/xpq4Yna.png)

#### 第二种是由表情专属的码点加修饰符组成(修饰符可选)

- 单个码点组成的样例

![](https://i.imgur.com/qst8FhQ.png)

- 码点加上修饰符的样例（此例中修饰符为 \uFE0F \20E3）

![](https://i.imgur.com/RdAGQL9.png)


#### 第三种是由多个第二种表情通过连接符组成

- 多个（码点 修饰符）相连的样例（连接符为 \u200D）

![](https://i.imgur.com/I5QNPGq.png)

- 经典的全家福

![](https://i.imgur.com/JTZknIw.png)

通过全家福可以发现，**\u1F469** 和 **\u1F466** 都是独立的 `Emoji` 码点，可以表现出一个人像，当他们通过 **\u200D** 连接符组合后，就可以表现出一个多人像的新 `Emoji` 。

一个工程师 **\u1F477** 和一个女性别 **\2640 \FE0F** 组合起来，就可以表现出一个女工程师的新 `Emoji` 。

可选的修饰符 **\uFE0F** **\u20E3** 等等跟在独立的 `Emoji` 码点后面，可以起修改表现颜色/表现性别等作用。

通过修饰符和连接符就能把 `Emoji` 码点组合出千变万化的表情。

## 许可证

    Copyright 2019 YvesCheung
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
        http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


  [1]: https://www.unicode.org/reports/tr51/
