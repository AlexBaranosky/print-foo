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
  (let [counter-sym (gensym "counter")
        logger `(#(do (#'print-and-return @~counter-sym "-> " %)
                      (swap! ~counter-sym inc)
                      %))]
    `(let [~counter-sym (atom 1)]
       (-> ~@(interpose logger body) ~logger))))

(defmacro print->>
  "Diagnostic tool for printing the values at each step of a `->>`"
  [& body]
  (cons '->> (interleave body (map #(list `#'print-and-return (str % "->> ")) (range)))))

(defmacro print-let
  "Diagnostic tool for printing the values at each step of a `let`"
  [bindings & body]
  (let [firsts (take-nth 2 bindings)
        seconds (take-nth 2 (rest bindings))]
    `(let ~(vec (interleave firsts
                            (map (fn [lhs rhs]
                                   `(foo '~lhs " " ~rhs))
                                 firsts
                                 seconds)))
       (#'print-and-return "let-body-value " (do ~@body)))))

