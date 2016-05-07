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
