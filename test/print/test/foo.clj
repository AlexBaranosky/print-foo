(ns print.test.foo
  (:use [print.foo]
        [clojure.test]))

(print-defn a [])
(print-defn b [x y] (+ x y))
(print-defn c [x & y] (apply + x y))
(print-defn d [[x y]] (+ x y))
(print-defn f [x [y] {:keys [d]}] (+ x y d))
(print-defn g [x [y] {:keys [d] :as e}] (+ x y d (count e)))

(print-defn- a2 [])
(print-defn- b2 [x y] (+ x y))
(print-defn- c2 [x & y] (apply + x y))
(print-defn- d2 [[x y]] (+ x y))
(print-defn- f2 [x [y] {:keys [d]}] (+ x y d))
(print-defn- g2 [x [y] {:keys [d] :as e}] (+ x y d (count e)))

(deftest test-print-and-return
  (is (= 2 (inc (print-and-return "ONE:: " (inc 0))))))

(deftest test-tap
  (is (= 2 (inc (tap "ONE??:: " (inc 0))))))

(deftest test-look
  (is (= 2 (inc (look (inc 0))))))

(deftest test-print-print-defn
  (a)
  (b 3 7)
  (c 3 7 11)
  (d [3 7])
  (f 3 [7] {:d 11})
  (g 3 [7] {:d 11}))

(deftest test-print-print-defn-
  (a2)
  (b2 3 7)
  (c2 3 7 11)
  (d2 [3 7])
  (f2 3 [7] {:d 11})
  (g2 3 [7] {:d 11}))



(comment
  (use 'print.foo)
  (:require-macros [print.foo :refer [print-and-return tap look print-> print-sexp print->> print-> print-let print-defn print-cond
                                      print-cond-> print-cond->> print-if middleware->]])

  (+ 1 2 (print-and-return "ONE::" (inc 4)))
  (- 1000 (tap "RESULT::" (+ 1 2 (inc 4))))
  (do (def a (inc 4))
      (- 1000 (+ 1 2 (look a))))
  (- 1000 (look (+ 1 2 (inc 4))))
  (print-sexp (str (+ 3 4) (+ 5 (* 6 2)) 4))
  (print-->> [1 2 3] (mapv inc) (mapv dec))
  (print--> 1 inc dec inc dec)
  (print-let [a 2 b 3 c (+ a b) d (+ c 7)] c)
  (do (print-defn f [a b] (* a b))
      (f 3 4))
  (print-cond nil 2 3 4)
  (print-cond-> {}
                (pos? 1) (assoc :a 1)
                (pos? 2) (merge {:b 2})
                (neg? 2) (merge {:c 3}))
  (print-cond->> [1 2 3]
                 (pos? 1) (map inc)
                 (neg? 2) (map dec))
  (print-if (odd? 9) 1 2)

  (do (defn m1 [x]
        (println x)
        x)
      (defn m2 [x]
        (fn [y]
          (x (assoc y :m2 true))))
      (defn m3 [x]
        (fn [y]
          (x (assoc y :m3 true))))

      ((middleware-> {:timings? true
                      :get-in   [:session]}
                     m1
                     m2
                     m3)

        {:session {:token 1}})))