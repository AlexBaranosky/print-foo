## print-foo

A Clojure library of print debugging macros.  

Just add "print-" to the front of a normal `->>`, `->`, `let`, `defn`, `defn-`, `cond`, `if` and prints useful information.

## Leiningen

```clj
[print-foo "0.4.2"]
```

## Usage

```clojure
print.foo=> (use 'print.foo)
nil

print.foo=> (print->> [1 2 3] (mapv inc) (mapv dec))
[1 2 3] [1 2 3]
(mapv inc) [2 3 4]
(mapv dec) [1 2 3]
[1 2 3]

print.foo=> (print-> 1 inc dec inc dec)
1 1
inc 2
dec 1
inc 2
dec 1
1

print.foo=> (print-let [a 2 b 3 c (+ a b) d (+ c 7)] c)
a 2
b 3
c 5
d 12
let result: 5
5

print.foo=> (print-defn f [a b] (* a b))
#'user/f
print.foo=> (f 3 4)
a 3
b 4
defn 'f' result: 12
12

print.foo=> (print-defn- g [a b] (+ a b))
#'user/f
print.foo=> (g 3 4)
a 3
b 4
defn 'g' result: 7
7

print.foo=> (print-cond nil 2 3 4)
test: 3 value: 4
4

print.foo=> (print-if (odd? 9) 1 2)
(odd? 9) true
1 1
1

print.foo=> (print-sexp (str (+ 3 4) (+ 5 (* 6 2)) 4))
3 3
4 4
(+ 3 4) 7
5 5
6 6
2 2
(* 6 2) 12
(+ 5 (* 6 2)) 17
4 4
(str (+ 3 4) (+ 5 (* 6 2)) 4) "7174"


;; `print-sexp` replaces normal code like ->, ->>, let, cond, if, 
;;  etc, where possible with print.foo versions

print.foo=> (pprint (macroexpand '(print-sexp (str (+ 3 4) (-> 5 (* 6) (* 2)) 4))))
(print.foo/print-and-return
 '(str (+ 3 4) (-> 5 (* 6) (* 2)) 4)
 " "
 (str
  (print.foo/print-and-return
   '(+ 3 4)
   " "
   (+
    (print.foo/print-and-return '3 " " 3)
    (print.foo/print-and-return '4 " " 4)))
  (print.foo/print-> 5 (* 6) (* 2))
  (print.foo/print-and-return '4 " " 4)))


print.foo=> (+ 1 2 (print-and-return "ONE:: " (inc 4)))
ONE:: 4
7

print.foo> (+ 1 2 (tap (inc 4)))
 *** 5
8

```

## Author

*  [Alex Baranosky](https://github.com/AlexBaranosky)

## License

Copyright © 2013 Alex Baranosky

Distributed under the MIT License
