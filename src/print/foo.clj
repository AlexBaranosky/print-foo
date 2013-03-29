(ns print.foo
  "Macros for printing diagnostic information while debugging or developing."
  (:require [gui-diff.core :as gui-diff]))


(defn- single-destructuring-arg->form+name
  "Turns any one binding arg (which may be a destructuring binding) into a vector
  where the left elem is the arg with a possible :as added to it.
  And the rght side is the symbol referring to the arg itself."
  [arg-form]
  (let [as-symbol (gensym 'symbol-for-destructured-arg)
        snd-to-last-is-as? #(= :as (second (reverse %)))]
    (cond (and (vector? arg-form) (snd-to-last-is-as? arg-form))
          [arg-form (last arg-form)]

          (vector? arg-form)
          [(-> arg-form (conj :as) (conj as-symbol)) as-symbol]

          (and (map? arg-form) (contains? arg-form :as))
          [arg-form (:as arg-form)]

          (map? arg-form)
          [(assoc arg-form :as as-symbol) as-symbol]

          :else
          [arg-form arg-form])))

(defn- expand-arg [arg]
  (if (symbol? arg)
    arg
    (first (single-destructuring-arg->form+name arg))))

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
                    sym [test `(#'print-and-return "test: " '~test "\nvalue: " ~expr)]]
                sym)))

(defmacro print-defn
  "Diagnostic tool for printing the values at each step of a `defn`"
  [fn-name arg-vec & body]
  (let [new-arg-vec (vec (map expand-arg arg-vec))]
    `(defn ~fn-name ~new-arg-vec
       ~@(keep (fn [x]
                (cond (and (not= '& x) (symbol? x))
                      `(#'print-and-return '~x " " ~x)

                      (not= '& x)
                      (let [[form as] (single-destructuring-arg->form+name x)]
                        `(#'print-and-return '~x " " ~as))))
              new-arg-vec)
       nil
       (#'print-and-return "defn '" '~fn-name "' result: " (do ~@body)))))

(defmacro print-defn-
  "Diagnostic tool for printing the values at each step of a `defn-`"
  [fn-name arg-vec & body]
  (let [new-arg-vec (vec (map expand-arg arg-vec))]
    `(defn- ~fn-name ~new-arg-vec
       ~@(keep (fn [x]
                (cond (and (not= '& x) (symbol? x))
                      `(#'print-and-return '~x " " ~x)

                      (not= '& x)
                      (let [[form as] (single-destructuring-arg->form+name x)]
                        `(#'print-and-return '~x " " ~as))))
              new-arg-vec)
       nil
       (#'print-and-return "defn- '" '~fn-name "' result: " (do ~@body)))))

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

