## print-Foo

A Clojure library of print debugging macros.  

Just add "print-" to the front of a normal `->>`, `->`, `let`, `defn`, `defn-`, `cond`, `if` and prints useful information.

## Leiningen

```clj
[print-foo "0.2.1"]
```

## Usage

```clojure
user=> (use 'print-foo.print-foo)
nil

user=> (print->> [1 2 3] (mapv inc) (mapv dec))
0->> [1 2 3]
1->> [2 3 4]
2->> [1 2 3]
[1 2 3]

user=> (print-> 4 inc)
0-> 4
1-> 5
5

user=> (print-let [a 2 b 3 c (+ a b) d (+ c 7)] c)
a 2
b 3
c 5
d 12
let-body-value 5
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
```

## Contributors

*  [Alex Baranosky](https://github.com/AlexBaranosky)
*  [Punit Rathore](https://github.com/punitrathore)

## License

Copyright © 2013 Alex Baranosky

Distributed under the MIT License
