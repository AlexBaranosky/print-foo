(ns print.test.foo
  (:require [print.foo :refer :all]
            [clojure.test :refer :all]
            [conjure.core :refer :all]))

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