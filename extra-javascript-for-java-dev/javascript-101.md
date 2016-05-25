# the language

## about

>loosely typed, class free, object oriented, dynamic, world's most misunderstood language that has nothing to do with java

## why learn it

* easy to learn
* very fast, very productive
* universal and very popular
* cross platform, heh
* you'll have to use it anyhow

---

# the catch

> JavaScript is most despised because it isn't SOME OTHER LANGUAGE.
>
> -- <cite>Douglas Crockford</cite>

Some StackOverflow wisdom

> Q: What is the difference between Java and Javascript?

>  A: Java and Javascript are similar like Car and Carpet are similar.

>  A: One is essentially a toy, designed for writing small pieces of code,  
> and traditionally used and abused by inexperienced programmers.
> The other is a scripting language for web browsers.

* JS has almost nothing in common with Java
* many below-avg programmers use it & many don't bother to learn it first
* most people are forced to use it and therefore get annoyed by it
* shift from typesafe language to dynamic is brutal

---

# types

* Number - only one type, 64 double precision, ~ double in java,  `var count = 1, percentage = 14.3, awesomeness = 9.9e+10;`
* String - 16bit Unicode, `var catDoes = 'Miow', dogDoes = "Wof,wof", multiline = 'first\nsecond', uni = '\u0041',  `
* Boolean `var cool = true`
* Object
    * function
    * Array `var mixed = ['apple', 'orange', 13, new Date()]`
    * Date `var now = new Date()`
    * RegExp
    * Math
* `undefined` & `null`

----

# type coercion

JS will try to convert & match different types "to help you"

```javascript
console.log(8 * null)
// → 0
console.log("5" - 1)
// → 4
console.log("5" + 1)
// → 51
console.log("five" * 2)
// → NaN
console.log(false == 0)
// → true
```

## Boolean conversion

```javascript
"" == false && 0 == false && NaN == false && undefined == false && null == false
//true
```

----

# common type functions

## strings
```javascript
'word'.length; //4
'word'.toUpperCase();//WORD
'word'.replace('o', 'a');//ward
typeof ''; //'string'

```

---

# Objects

* Closer to hashmap, `var obj = new Object(), other = {};`
* always passed by reference

```javascript
var o = { //object literal syntax
  name: 'Bond',
  'job' : 'agent' //quotes are optional, but it looks familiar... JSON! Bingo!
};

o.code = '007'; //o = {name: 'Bond', job='agent', code='007'}
o['name'] === 'Bond'; //true
o.mission;//undefined
o.mission.objective;//error

```   

---

# prototypal inheritance

You extend other objects(prototypes)

---

# arrays

Special kind of object, length != size `var a = ne Array(), var b = [];`

```javascript
var a = ['Gödel', 'Escher', 'Bach']; //object literal syntax
a[1]; // 'Escher';
a[2] = 'replicant'; //a = ['Gödel', 'Escher', 'replicant'];
a[10] = 'appender';
a.length; //11, a = ['Gödel', 'Escher', 'replicant', undefined x 7, 'appender']
a.forEach(function(current,index, array) {console.log(current);});
a=['a', 'aa', ]; a.push('aaa');//a =['a','aa','aaa']
b=['b','bb','bbb']; var c = a.concat(b);//c =['a','aa','aaa','b','bb','bbb']
c.join('-');//a-aa-aaa-b-bb-bbb
c.slice(2,4);//['aaa','b']
c.splice(3,1);//c =['a','aa','aaa','bb','bbb']
c.pop();//'bbb' ;

```
---

# functions

```javascript
var f1 = function(param1) {
  return param1 + '!';
}

function f2(param1) {
  return param1 + '!';
}
```
