(defproject print-foo "1.0.2"
  :description "A set of useful print-debugging tools"
  :url "https://github.com/AlexBaranosky/print-foo"
  :license {:name "MIT License"
            :url "http://mit-license.org/"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [gui-diff "0.6.6"]]
  :profiles {:dev {:dependencies [[org.clojars.runa/conjure "2.2.0"]]
                   :plugins [[jonase/eastwood "0.0.2"]]}}
  :deploy-repositories [["clojars" {:url "https://clojars.org/repo/"
                                    :sign-releases false}]])
