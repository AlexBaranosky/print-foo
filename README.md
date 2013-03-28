## print-foo

A Clojure library of print debugging macros.  

Just add "print-" to the front of a normal `->>`, `->`, `let`, `defn`, `defn-`, `cond`, `if` and prints useful information.

## Leiningen

```clj
[print-foo "0.3.2"]
```

## Usage

```clojure
user=> (use 'print.foo)
nil

user=> (print->> [1 2 3] (mapv inc) (mapv dec))
[1 2 3] [1 2 3]
(mapv inc) [2 3 4]
(mapv dec) [1 2 3]
[1 2 3]

user=> (print-> 1 inc dec inc dec)
1 1
inc 2
dec 1
inc 2
dec 1
1

user=> (print-let [a 2 b 3 c (+ a b) d (+ c 7)] c)
a 2
b 3
c 5
d 12
let result: 5
5

user=> (print-defn f [a b] (* a b))
#'user/f
user=> (f 3 4)
a 3
b 4
defn 'f' result: 12
12

user=> (print-defn- g [a b] (+ a b))
#'user/f
user=> (g 3 4)
a 3
b 4
defn 'g' result: 7
7

user=> (print-cond nil 2 3 4)
test: 3 value: 4
4

user=> (print-if (odd? 9) 1 2)
(odd? 9) true
1 1
1

user=> (print-sexp (str (+ 3 4) (+ 5 (* 6 2)) 4))
(+ 3 4) 7
(* 6 2) 12
(+ 5 (* 6 2)) 17
(str (+ 3 4) (+ 5 (* 6 2)) 4) "7174"
"7174"
```

## Contributors

*  [Alex Baranosky](https://github.com/AlexBaranosky)
*  [Punit Rathore](https://github.com/punitrathore)

## License

Copyright © 2013 Alex Baranosky

Distributed under the MIT License
