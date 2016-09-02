(ns print.foo.preloads.devtools
  (:require [devtools.preload]
            [print.foo :include-macros true]))

(enable-console-print!)

(set! print.foo/*print-fn* (aget js/console "log"))


