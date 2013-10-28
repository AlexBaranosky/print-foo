(defproject print-foo "0.4.1"
  :description "A set of useful print-debugging tools"
  :url "https://github.com/AlexBaranosky/print-foo"
  :license {:name "MIT License"
            :url "http://mit-license.org/"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [gui-diff "0.6.2"]]
  :profiles {:dev {:dependencies [[jonase/kibit "0.0.3"]
                                  [org.clojars.runa/conjure "2.2.0"]
                                  [slamhound "1.3.3"]]
                   :plugins [[jonase/eastwood "0.0.2"]]}
             :1.3.0 {:dependencies [[org.clojure/clojure "1.3.0"]]}
             :1.4.0 {:dependencies [[org.clojure/clojure "1.4.0"]]}
             :1.5.0 {:dependencies [[org.clojure/clojure "1.5.1"]]}}
   :aliases {"run-tests" ["with-profile" "1.3.0:1.4.0:1.5.0" "test"]
            "slamhound" ["run" "-m" "slam.hound"]})
