(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', 'kotlin'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('kotlin'));
  else {
    if (typeof kotlin === 'undefined') {
      throw new Error("Error loading module 'EmojiReader-lib'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'EmojiReader-lib'.");
    }root['EmojiReader-lib'] = factory(typeof this['EmojiReader-lib'] === 'undefined' ? {} : this['EmojiReader-lib'], kotlin);
  }
}(this, function (_, Kotlin) {
  'use strict';
  var $$importsForInline$$ = _.$$importsForInline$$ || (_.$$importsForInline$$ = {});
  var toBoxedChar = Kotlin.toBoxedChar;
  var unboxChar = Kotlin.unboxChar;
  var toChar = Kotlin.toChar;
  var IllegalArgumentException_init = Kotlin.kotlin.IllegalArgumentException_init;
  var Kind_OBJECT = Kotlin.Kind.OBJECT;
  var toList = Kotlin.kotlin.collections.toList_7wnvza$;
  var binarySearch = Kotlin.kotlin.collections.binarySearch_sr7qim$;
  var IndexOutOfBoundsException = Kotlin.kotlin.IndexOutOfBoundsException;
  var getOrNull = Kotlin.kotlin.collections.getOrNull_yzln2o$;
  var IntRange = Kotlin.kotlin.ranges.IntRange;
  var setOf = Kotlin.kotlin.collections.setOf_i5x0yv$;
  var get_lastIndex = Kotlin.kotlin.collections.get_lastIndex_55thoc$;
  var plus = Kotlin.kotlin.collections.plus_khz7k3$;
  var Kind_CLASS = Kotlin.Kind.CLASS;
  var joinToString = Kotlin.kotlin.collections.joinToString_fmv235$;
  var collectionSizeOrDefault = Kotlin.kotlin.collections.collectionSizeOrDefault_ba2ldo$;
  var ArrayList_init = Kotlin.kotlin.collections.ArrayList_init_ww73n8$;
  var ArrayList_init_0 = Kotlin.kotlin.collections.ArrayList_init_287e2$;
  var String_0 = Kotlin.kotlin.text.String_4hbowm$;
  var Math_0 = Math;
  var NullPointerException = Kotlin.kotlin.NullPointerException;
  var IndexOutOfBoundsException_init = Kotlin.kotlin.IndexOutOfBoundsException_init;
  var String_1 = Kotlin.kotlin.text.String_8chfmy$;
  function Character() {
    Character_instance = this;
    this.MAX_CODE_POINT = 1114111;
    this.MIN_SUPPLEMENTARY_CODE_POINT = 65536;
    this.MIN_HIGH_SURROGATE = toBoxedChar(55296);
    this.MAX_HIGH_SURROGATE = toBoxedChar(56319);
    this.MIN_LOW_SURROGATE = toBoxedChar(56320);
    this.MAX_LOW_SURROGATE = toBoxedChar(57343);
    this.MIN_SURROGATE = this.MIN_HIGH_SURROGATE;
    this.MAX_SURROGATE = this.MAX_LOW_SURROGATE;
  }
  Character.prototype.charCount_za3lpa$ = function (codePoint) {
    return codePoint >= 65536 ? 2 : 1;
  };
  Character.prototype.codePointAt_905azu$ = function (seq, index) {
    var index_0 = index;
    var c1 = seq.charCodeAt(index_0);
    if (this.isHighSurrogate_s8itvh$(c1) && (index_0 = index_0 + 1 | 0, index_0) < seq.length) {
      var c2 = seq.charCodeAt(index_0);
      if (this.isLowSurrogate_s8itvh$(c2)) {
        return this.toCodePoint_o3jtqy$(c1, c2);
      }}return c1 | 0;
  };
  Character.prototype.toCodePoint_o3jtqy$ = function (high, low) {
    return ((high | 0) << 10) + (low | 0) + -56613888 | 0;
  };
  Character.prototype.isHighSurrogate_s8itvh$ = function (ch) {
    return ch >= unboxChar(this.MIN_HIGH_SURROGATE) && (ch | 0) < 56320;
  };
  Character.prototype.isLowSurrogate_s8itvh$ = function (ch) {
    return ch >= unboxChar(this.MIN_LOW_SURROGATE) && (ch | 0) < 57344;
  };
  Character.prototype.toChars_za3lpa$ = function (codePoint) {
    var tmp$;
    if (this.isBmpCodePoint_za3lpa$(codePoint)) {
      tmp$ = Kotlin.charArrayOf(toChar(codePoint));
    } else if (this.isValidCodePoint_za3lpa$(codePoint)) {
      var result = Kotlin.charArray(2);
      this.toSurrogates_id94fi$(codePoint, result, 0);
      tmp$ = result;
    } else {
      throw IllegalArgumentException_init();
    }
    return tmp$;
  };
  Character.prototype.toChars_id94fi$ = function (codePoint, dst, dstIndex) {
    var tmp$;
    if (this.isBmpCodePoint_za3lpa$(codePoint)) {
      dst[dstIndex] = toChar(codePoint);
      tmp$ = 1;
    } else if (this.isValidCodePoint_za3lpa$(codePoint)) {
      this.toSurrogates_id94fi$(codePoint, dst, dstIndex);
      tmp$ = 2;
    } else {
      throw IllegalArgumentException_init();
    }
    return tmp$;
  };
  Character.prototype.toSurrogates_id94fi$ = function (codePoint, dst, index) {
    dst[index + 1 | 0] = unboxChar(this.lowSurrogate_za3lpa$(codePoint));
    dst[index] = unboxChar(this.highSurrogate_za3lpa$(codePoint));
  };
  Character.prototype.isValidCodePoint_za3lpa$ = function (codePoint) {
    var plane = codePoint >>> 16;
    return plane < 17;
  };
  Character.prototype.isBmpCodePoint_za3lpa$ = function (codePoint) {
    return codePoint >>> 16 === 0;
  };
  Character.prototype.highSurrogate_za3lpa$ = function (codePoint) {
    return toBoxedChar(toChar((codePoint >>> 10) + 55232 | 0));
  };
  Character.prototype.lowSurrogate_za3lpa$ = function (codePoint) {
    return toBoxedChar(toChar((codePoint & 1023) + 56320 | 0));
  };
  Character.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Character',
    interfaces: []
  };
  var Character_instance = null;
  function Character_getInstance() {
    if (Character_instance === null) {
      new Character();
    }return Character_instance;
  }
  function EmojiReader() {
    EmojiReader_instance = this;
  }
  EmojiReader.prototype.getTextLength_6bul2c$ = function (str) {
    var sm = new EmojiReader$StateMachine();
    sm.read_905azu$(str);
    return sm.getCurrentCharSize();
  };
  EmojiReader.prototype.analyzeText_6bul2c$ = function (str) {
    var sm = new EmojiReader$StateMachine();
    sm.read_905azu$(str);
    var $receiver = sm.getCharList();
    var destination = ArrayList_init(collectionSizeOrDefault($receiver, 10));
    var tmp$;
    tmp$ = $receiver.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      var tmp$_0 = destination.add_11rb$;
      var tmp$_1;
      var accumulator = 0;
      tmp$_1 = item.codePoint.iterator();
      while (tmp$_1.hasNext()) {
        var element = tmp$_1.next();
        accumulator = accumulator + Character_getInstance().charCount_za3lpa$(element) | 0;
      }
      var length = accumulator;
      tmp$_0.call(destination, new EmojiReader$Node(item.startIndex, length, item.isEmoji, toList(item.codePoint)));
    }
    return destination;
  };
  EmojiReader.prototype.isEmojiOfVisionIndex_905azu$ = function (str, idx) {
    return this.isEmojiOfVisionIndex_wdjqw3$(this.analyzeText_6bul2c$(str), idx);
  };
  EmojiReader.prototype.isEmojiOfVisionIndex_wdjqw3$ = function (nodeList, idx) {
    return nodeList.get_za3lpa$(idx).isEmoji;
  };
  EmojiReader.prototype.isEmojiOfCharIndex_905azu$ = function (str, idx) {
    return this.isEmojiOfCharIndex_wdjqw3$(this.analyzeText_6bul2c$(str), idx);
  };
  function EmojiReader$isEmojiOfCharIndex$lambda(closure$idx) {
    return function (node) {
      if (closure$idx < node.startIndex)
        return 1;
      else if (closure$idx >= (node.startIndex + node.length | 0))
        return -1;
      else
        return 0;
    };
  }
  EmojiReader.prototype.isEmojiOfCharIndex_wdjqw3$ = function (nodeList, idx) {
    var visionIdx = binarySearch(nodeList, void 0, void 0, EmojiReader$isEmojiOfCharIndex$lambda(idx));
    if (visionIdx < 0) {
      return false;
    }return this.isEmojiOfVisionIndex_wdjqw3$(nodeList, visionIdx);
  };
  EmojiReader.prototype.subSequence_905azu$ = function (str, end) {
    return this.subSequence_3peag4$(str, 0, end);
  };
  EmojiReader.prototype.subSequence_3peag4$ = function (str, start, end) {
    var tmp$, tmp$_0, tmp$_1, tmp$_2;
    if (start < 0 || end > str.length) {
      throw new IndexOutOfBoundsException('The index should be in range [0,' + str.length + '],' + ('but actually start = ' + start + ' and end = ' + end + '.'));
    }if (start > end) {
      throw new IndexOutOfBoundsException('The start index should be not bigger than end,' + ('but actually start = ' + start + ' and end = ' + end + '.'));
    }if (start === end) {
      return '';
    }var sm = new EmojiReader$StateMachine();
    sm.read_905azu$(str, start + end | 0);
    var charList = sm.getCharList();
    tmp$_0 = (tmp$ = getOrNull(charList, start)) != null ? tmp$.startIndex : null;
    if (tmp$_0 == null) {
      return '';
    }var startIdx = tmp$_0;
    var tmp$_3;
    if ((tmp$_1 = getOrNull(charList, end - 1 | 0)) != null) {
      var tmp$_4 = tmp$_1.startIndex;
      var tmp$_5;
      var accumulator = 0;
      tmp$_5 = tmp$_1.codePoint.iterator();
      while (tmp$_5.hasNext()) {
        var element = tmp$_5.next();
        accumulator = accumulator + Character_getInstance().charCount_za3lpa$(element) | 0;
      }
      tmp$_3 = tmp$_4 + accumulator | 0;
    } else
      tmp$_3 = null;
    var endIdx = tmp$_3;
    if (endIdx == null) {
      tmp$_2 = Kotlin.subSequence(str, startIdx, str.length);
    } else {
      tmp$_2 = Kotlin.subSequence(str, startIdx, endIdx);
    }
    return tmp$_2;
  };
  EmojiReader.prototype.transToUnicode_6bul2c$ = function (str) {
    var result = ArrayList_init_0();
    var i = 0;
    while (i < str.length) {
      var codePoint = Character_getInstance().codePointAt_905azu$(str, i);
      result.add_11rb$('U+' + Integer_getInstance().toHexString_za3lpa$(codePoint));
      i = i + Character_getInstance().charCount_za3lpa$(codePoint) | 0;
    }
    return result;
  };
  EmojiReader.prototype.forEachCodePoint_0 = function ($receiver, action) {
    var i = 0;
    while (i < $receiver.length) {
      var codePoint = Character_getInstance().codePointAt_905azu$($receiver, i);
      action(codePoint);
      i = i + Character_getInstance().charCount_za3lpa$(codePoint) | 0;
    }
  };
  function EmojiReader$StateMachine() {
    EmojiReader$StateMachine$Companion_getInstance();
    this.emojiModifier_0 = plus(plus(setOf([65038, 65039, 8419]), EmojiReader$StateMachine$Companion_getInstance().ModifierTagRange), EmojiReader$StateMachine$Companion_getInstance().ModifierSkinTone);
    this.charUnitList_0 = ArrayList_init_0();
    this.currentIndex_0 = 0;
    this.currentCodePoint_0 = 0;
    this.currentChar_0 = new EmojiReader$InnerNode(0);
    this.currentState_0 = 0;
  }
  function EmojiReader$StateMachine$Companion() {
    EmojiReader$StateMachine$Companion_instance = this;
    this.Joiner = 8205;
    this.ModifierBlack = 65038;
    this.ModifierColorFul = 65039;
    this.ModifierKeyCap = 8419;
    this.ModifierTagRange = new IntRange(917536, 917631);
    this.ModifierSkinTone = setOf([127995, 127996, 127997, 127998, 127999]);
    this.STATE_DEFAULT = 0;
    this.STATE_EMOJI = 1;
    this.STATE_PRE_EMOJI = 16;
    this.STATE_NATIONAL_FLAG = 257;
    this.STATE_EMOJI_MODIFIER = 4097;
    this.STATE_EMOJI_JOIN = 65536;
  }
  EmojiReader$StateMachine$Companion.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var EmojiReader$StateMachine$Companion_instance = null;
  function EmojiReader$StateMachine$Companion_getInstance() {
    if (EmojiReader$StateMachine$Companion_instance === null) {
      new EmojiReader$StateMachine$Companion();
    }return EmojiReader$StateMachine$Companion_instance;
  }
  EmojiReader$StateMachine.prototype.endChar_0 = function () {
    this.currentState_0 = 0;
    if (!this.currentChar_0.codePoint.isEmpty()) {
      this.charUnitList_0.add_11rb$(this.currentChar_0);
      this.currentChar_0 = new EmojiReader$InnerNode(this.currentIndex_0);
    }};
  EmojiReader$StateMachine.prototype.assertEmoji_0 = function () {
    this.currentChar_0.isEmoji = true;
  };
  EmojiReader$StateMachine.prototype.moveToNext_0 = function () {
    this.currentChar_0.codePoint.add_11rb$(this.currentCodePoint_0);
    this.currentIndex_0 = this.currentIndex_0 + Character_getInstance().charCount_za3lpa$(this.currentCodePoint_0) | 0;
  };
  EmojiReader$StateMachine.prototype.moveToPrev_0 = function () {
    var lastCodePoint = this.currentChar_0.codePoint.removeAt_za3lpa$(get_lastIndex(this.currentChar_0.codePoint));
    this.currentIndex_0 = this.currentIndex_0 - Character_getInstance().charCount_za3lpa$(lastCodePoint) | 0;
  };
  EmojiReader$StateMachine.prototype.read_905azu$ = function (str, end) {
    if (end === void 0)
      end = str.length;
    while (this.currentIndex_0 < str.length) {
      this.currentCodePoint_0 = Character_getInstance().codePointAt_905azu$(str, this.currentIndex_0);
      if (this.currentState_0 === 65536)
        if (this.isEmojiCodePoint_0(this.currentCodePoint_0)) {
          this.currentState_0 = 1;
          this.moveToNext_0();
        } else {
          this.moveToPrev_0();
          this.endChar_0();
        }
       else if (this.currentState_0 === 257)
        if (this.isRegionalIndicator_0(this.currentCodePoint_0)) {
          this.moveToNext_0();
          this.assertEmoji_0();
          this.endChar_0();
        } else {
          this.assertEmoji_0();
          this.endChar_0();
        }
       else if (this.currentState_0 === 16)
        if (this.emojiModifier_0.contains_11rb$(this.currentCodePoint_0)) {
          this.currentState_0 = 4097;
          this.moveToNext_0();
        } else {
          this.endChar_0();
        }
       else if ((this.currentState_0 & 1) !== 0)
        if (8205 === this.currentCodePoint_0) {
          this.currentState_0 = 65536;
          this.moveToNext_0();
        } else if (this.emojiModifier_0.contains_11rb$(this.currentCodePoint_0)) {
          this.currentState_0 = 4097;
          this.moveToNext_0();
        } else {
          this.assertEmoji_0();
          this.endChar_0();
        }
       else if (this.isRegionalIndicator_0(this.currentCodePoint_0)) {
        this.currentState_0 = 257;
        this.moveToNext_0();
      } else if (this.maybeEmojiCodePoint_0(this.currentCodePoint_0)) {
        this.currentState_0 = 16;
        this.moveToNext_0();
      } else if (this.isEmojiCodePoint_0(this.currentCodePoint_0)) {
        this.currentState_0 = 1;
        this.moveToNext_0();
      } else {
        this.moveToNext_0();
        this.endChar_0();
      }
      if (this.getCurrentCharSize() >= end) {
        break;
      }}
    if (this.currentState_0 !== 0) {
      if ((this.currentState_0 & 1) !== 0) {
        this.assertEmoji_0();
      }this.endChar_0();
    }};
  EmojiReader$StateMachine.prototype.getCurrentIndex = function () {
    return this.currentIndex_0;
  };
  EmojiReader$StateMachine.prototype.getCurrentCharSize = function () {
    return this.charUnitList_0.size;
  };
  EmojiReader$StateMachine.prototype.getCharList = function () {
    return this.charUnitList_0;
  };
  EmojiReader$StateMachine.prototype.isEmojiCodePoint_0 = function (codePoint) {
    return 127488 <= codePoint && codePoint <= 131071 || (8986 <= codePoint && codePoint <= 9215) || (9312 <= codePoint && codePoint <= 9471) || (9472 <= codePoint && codePoint <= 12287) || (12800 <= codePoint && codePoint <= 13055) || this.isSpecialSymbol_0(codePoint);
  };
  EmojiReader$StateMachine.prototype.isSpecialSymbol_0 = function (codePoint) {
    return codePoint === 12336 || codePoint === 169 || codePoint === 174 || codePoint === 8482;
  };
  EmojiReader$StateMachine.prototype.maybeEmojiCodePoint_0 = function (codePoint) {
    return 0 <= codePoint && codePoint <= 57 || (8592 <= codePoint && codePoint <= 8703);
  };
  EmojiReader$StateMachine.prototype.isRegionalIndicator_0 = function (codePoint) {
    return 126976 <= codePoint && codePoint <= 127487;
  };
  EmojiReader$StateMachine.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'StateMachine',
    interfaces: []
  };
  function EmojiReader$InnerNode(startIndex, isEmoji, codePoint) {
    if (isEmoji === void 0)
      isEmoji = false;
    if (codePoint === void 0) {
      codePoint = ArrayList_init_0();
    }this.startIndex = startIndex;
    this.isEmoji = isEmoji;
    this.codePoint = codePoint;
  }
  EmojiReader$InnerNode.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'InnerNode',
    interfaces: []
  };
  EmojiReader$InnerNode.prototype.component1 = function () {
    return this.startIndex;
  };
  EmojiReader$InnerNode.prototype.component2 = function () {
    return this.isEmoji;
  };
  EmojiReader$InnerNode.prototype.component3 = function () {
    return this.codePoint;
  };
  EmojiReader$InnerNode.prototype.copy_8bvjuj$ = function (startIndex, isEmoji, codePoint) {
    return new EmojiReader$InnerNode(startIndex === void 0 ? this.startIndex : startIndex, isEmoji === void 0 ? this.isEmoji : isEmoji, codePoint === void 0 ? this.codePoint : codePoint);
  };
  EmojiReader$InnerNode.prototype.toString = function () {
    return 'InnerNode(startIndex=' + Kotlin.toString(this.startIndex) + (', isEmoji=' + Kotlin.toString(this.isEmoji)) + (', codePoint=' + Kotlin.toString(this.codePoint)) + ')';
  };
  EmojiReader$InnerNode.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.startIndex) | 0;
    result = result * 31 + Kotlin.hashCode(this.isEmoji) | 0;
    result = result * 31 + Kotlin.hashCode(this.codePoint) | 0;
    return result;
  };
  EmojiReader$InnerNode.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.startIndex, other.startIndex) && Kotlin.equals(this.isEmoji, other.isEmoji) && Kotlin.equals(this.codePoint, other.codePoint)))));
  };
  function EmojiReader$Node(startIndex, length, isEmoji, codePoint) {
    this.startIndex = startIndex;
    this.length = length;
    this.isEmoji = isEmoji;
    this.codePoint = codePoint;
  }
  function EmojiReader$Node$toString$lambda(it) {
    return Integer_getInstance().toHexString_za3lpa$(it);
  }
  EmojiReader$Node.prototype.toString = function () {
    return 'Node(startIndex=' + this.startIndex + ', ' + ('length=' + this.length + ', ') + ('isEmoji=' + this.isEmoji + ', ') + ('codePoint=' + joinToString(this.codePoint, void 0, void 0, void 0, void 0, void 0, EmojiReader$Node$toString$lambda) + ')');
  };
  EmojiReader$Node.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Node',
    interfaces: []
  };
  EmojiReader$Node.prototype.component1 = function () {
    return this.startIndex;
  };
  EmojiReader$Node.prototype.component2 = function () {
    return this.length;
  };
  EmojiReader$Node.prototype.component3 = function () {
    return this.isEmoji;
  };
  EmojiReader$Node.prototype.component4 = function () {
    return this.codePoint;
  };
  EmojiReader$Node.prototype.copy_907e12$ = function (startIndex, length, isEmoji, codePoint) {
    return new EmojiReader$Node(startIndex === void 0 ? this.startIndex : startIndex, length === void 0 ? this.length : length, isEmoji === void 0 ? this.isEmoji : isEmoji, codePoint === void 0 ? this.codePoint : codePoint);
  };
  EmojiReader$Node.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.startIndex) | 0;
    result = result * 31 + Kotlin.hashCode(this.length) | 0;
    result = result * 31 + Kotlin.hashCode(this.isEmoji) | 0;
    result = result * 31 + Kotlin.hashCode(this.codePoint) | 0;
    return result;
  };
  EmojiReader$Node.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.startIndex, other.startIndex) && Kotlin.equals(this.length, other.length) && Kotlin.equals(this.isEmoji, other.isEmoji) && Kotlin.equals(this.codePoint, other.codePoint)))));
  };
  EmojiReader.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'EmojiReader',
    interfaces: []
  };
  var EmojiReader_instance = null;
  function EmojiReader_getInstance() {
    if (EmojiReader_instance === null) {
      new EmojiReader();
    }return EmojiReader_instance;
  }
  function Integer() {
    Integer_instance = this;
    this.digits = Kotlin.charArrayOf(48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122);
  }
  Integer.prototype.toHexString_za3lpa$ = function (i) {
    return this.toUnsignedString0_0(i, 4);
  };
  Integer.prototype.toUnsignedString0_0 = function (val, shift) {
    var mag = 32 - this.numberOfLeadingZeros_za3lpa$(val) | 0;
    var a = (mag + (shift - 1) | 0) / shift | 0;
    var chars = Math_0.max(a, 1);
    var buf = Kotlin.charArray(chars);
    this.formatUnsignedInt_mlst62$(val, shift, buf, 0, chars);
    return String_0(buf);
  };
  Integer.prototype.numberOfLeadingZeros_za3lpa$ = function (i) {
    var i_0 = i;
    if (i_0 === 0)
      return 32;
    var n = 1;
    if (i_0 >>> 16 === 0) {
      n = n + 16 | 0;
      i_0 = i_0 << 16;
    }if (i_0 >>> 24 === 0) {
      n = n + 8 | 0;
      i_0 = i_0 << 8;
    }if (i_0 >>> 28 === 0) {
      n = n + 4 | 0;
      i_0 = i_0 << 4;
    }if (i_0 >>> 30 === 0) {
      n = n + 2 | 0;
      i_0 = i_0 << 2;
    }n = n - (i_0 >>> 31) | 0;
    return n;
  };
  Integer.prototype.formatUnsignedInt_mlst62$ = function (val, shift, buf, offset, len) {
    var val_0 = val;
    var charPos = len;
    var radix = 1 << shift;
    var mask = radix - 1 | 0;
    do {
      buf[offset + (charPos = charPos - 1 | 0, charPos) | 0] = this.digits[val_0 & mask];
      val_0 = val_0 >>> shift;
    }
     while (val_0 !== 0 && charPos > 0);
    return charPos;
  };
  Integer.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Integer',
    interfaces: []
  };
  var Integer_instance = null;
  function Integer_getInstance() {
    if (Integer_instance === null) {
      new Integer();
    }return Integer_instance;
  }
  function StringFactory() {
    StringFactory_instance = this;
  }
  StringFactory.prototype.newStringFromCodePoints_q8imh4$ = function (codePoints, offset, count) {
    if (codePoints == null) {
      throw new NullPointerException('codePoints == null');
    }if ((offset | count) < 0 || count > (codePoints.length - offset | 0)) {
      throw IndexOutOfBoundsException_init();
    }var value = Kotlin.charArray(count * 2 | 0);
    var end = offset + count | 0;
    var length = 0;
    for (var i = offset; i < end; i++) {
      length = length + Character_getInstance().toChars_id94fi$(codePoints[i], value, length) | 0;
    }
    return String_1(value, 0, length);
  };
  StringFactory.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'StringFactory',
    interfaces: []
  };
  var StringFactory_instance = null;
  function StringFactory_getInstance() {
    if (StringFactory_instance === null) {
      new StringFactory();
    }return StringFactory_instance;
  }
  function String_2(codePoints, offset, length) {
    return StringFactory_getInstance().newStringFromCodePoints_q8imh4$(codePoints, offset, length);
  }
  function encodeString$lambda(codePoint) {
    return encodeString_0(codePoint);
  }
  function encodeString($receiver) {
    return joinToString($receiver, '', void 0, void 0, void 0, void 0, encodeString$lambda);
  }
  function encodeString_0($receiver) {
    return String_2($receiver, 0, $receiver.length);
  }
  function getTextLength(str) {
    return EmojiReader_getInstance().getTextLength_6bul2c$(str);
  }
  function isEmojiOfVisionIndex(str, idx) {
    return EmojiReader_getInstance().isEmojiOfVisionIndex_905azu$(str, idx);
  }
  function isEmojiOfCharIndex(str, idx) {
    return EmojiReader_getInstance().isEmojiOfCharIndex_905azu$(str, idx);
  }
  function subSequence(str, start, end) {
    return EmojiReader_getInstance().subSequence_3peag4$(str, start, end);
  }
  function analyzeText(str) {
    return EmojiReader_getInstance().analyzeText_6bul2c$(str);
  }
  function transToUnicode(str) {
    return joinToString(EmojiReader_getInstance().transToUnicode_6bul2c$(str));
  }
  var package$com = _.com || (_.com = {});
  var package$yy = package$com.yy || (package$com.yy = {});
  var package$mobile = package$yy.mobile || (package$yy.mobile = {});
  var package$emoji = package$mobile.emoji || (package$mobile.emoji = {});
  Object.defineProperty(package$emoji, 'Character', {
    get: Character_getInstance
  });
  $$importsForInline$$['EmojiReader-lib'] = _;
  EmojiReader.prototype.Node = EmojiReader$Node;
  Object.defineProperty(package$emoji, 'EmojiReader', {
    get: EmojiReader_getInstance
  });
  Object.defineProperty(package$emoji, 'Integer', {
    get: Integer_getInstance
  });
  Object.defineProperty(package$emoji, 'StringFactory', {
    get: StringFactory_getInstance
  });
  package$emoji.String_dhoe8q$ = String_2;
  package$emoji.encodeString_iwh6ni$ = encodeString;
  package$emoji.encodeString_3dg7px$ = encodeString_0;
  _.getTextLength = getTextLength;
  _.isEmojiOfVisionIndex = isEmojiOfVisionIndex;
  _.isEmojiOfCharIndex = isEmojiOfCharIndex;
  _.subSequence = subSequence;
  _.analyzeText = analyzeText;
  _.transToUnicode = transToUnicode;
  Kotlin.defineModule('EmojiReader-lib', _);
  return _;
}));

//# sourceMappingURL=EmojiReader-lib.js.map
