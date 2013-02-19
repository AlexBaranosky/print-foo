(ns print-foo.print-foo
  "Macros for printing diagnostic information while debugging or developing."
  (:require [gui-diff.core :as gui-diff]))


(defn- print-and-return [& xs]
  (when (seq (butlast xs))
    (print (apply str (butlast xs))))
  (gui-diff/p (last xs))
  (last xs))

(defmacro print->
  "Diagnostic tool for printing the values at each step of a `->`"
  [& body]
  (let [print-forms (map #(list `(fn [x#] (#'print-and-return '~% " " x#))) body)]
    (cons '-> (interleave body print-forms))))

(defmacro print->>
  "Diagnostic tool for printing the values at each step of a `->>`"
  [& body]
  (let [print-forms (map (fn [x] `(#'print-and-return '~x " ")) body)]
    (cons '->> (interleave body print-forms))))

(defmacro print-let
  "Diagnostic tool for printing the values at each step of a `let`"
  [bindings & body]
  (let [firsts (take-nth 2 bindings)
        seconds (take-nth 2 (rest bindings))]
    `(let ~(vec (interleave firsts
                            (map (fn [lhs rhs]
                                   `(#'print-and-return '~lhs " " ~rhs))
                                 firsts
                                 seconds)))
       (#'print-and-return "let result: " (do ~@body)))))

(defmacro print-if
  "Diagnostic tool for printing the values at each step of an `if`"
  [test expr1 expr2]
  `(if (#'print-and-return '~test " " ~test)
     (#'print-and-return '~expr1 " " ~expr1)
     (#'print-and-return '~expr2 " " ~expr2)))

(defmacro print-cond
  "Diagnostic tool for printing the values at each step of a `cond`"
  [& body]
  (cons 'cond (for [[test expr] (partition 2 body)
                    sym [test `(#'print-and-return "test: " ~test "\nvalue: " ~expr)]]
                sym)))

(defmacro print-defn
  "Diagnostic tool for printing the values at each step of a `defn`"
  [fn-name arg-vec & body]
  `(defn ~fn-name ~arg-vec
     ~@(map (fn [x] `(#'print-and-return '~x " " ~x)) arg-vec)
     nil
     (#'print-and-return "defn '" '~fn-name "' result: " (do ~@body))))

(defmacro print-defn-
  "Diagnostic tool for printing the values at each step of a `defn-`"
  [fn-name arg-vec & body]
  `(defn- ~fn-name ~arg-vec
     ~@(map (fn [x] `(#'print-and-return '~x " " ~x)) arg-vec)
     (#'print-and-return "defn- '" '~fn-name "' result: " (do ~@body))))

(defmacro print-sexp
  "Diagnostic tool for printing the values at each step of a given s-expression"
  [sexp]
  (if-not (list? sexp)
    sexp
    `(#'print-and-return
      '~sexp
      " "
      ~(map-indexed (fn [idx x]
                      (if (zero? idx)
                        x
                        `(print-sexp ~x)))
                    sexp))))

