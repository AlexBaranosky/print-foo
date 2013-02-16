## print-Foo

A Clojure library of print debugging macros.  

Just add "print-" to the front of a normal `->>`, `->`, `let`, etc and prints useful information. (More macros planned.)

## Leiningen

```clj
[print-foo "0.1.0"]
```

## Usage

```clojure
user=> (use 'print-foo.print-foo)
nil
user=> (print-let [a 2 b 3 c (+ a b) d (+ c 7)] c)
a 2
b 3
c 5
d 12
let-body-value 5
5
user=> (print-> 4 inc)
0-> 4
1-> 5
5
user=> (print->> [1 2 3] (mapv inc) (mapv dec))
0->> [1 2 3]
1->> [2 3 4]
2->> [1 2 3]
[1 2 3]
```

## Contributors

[Alex Baranosky](https://github.com/AlexBaranosky)
[Punit Rathore](https://github.com/punitrathore)

## License

Copyright © 2013 Alex Baranosky

Distributed under the MIT License
