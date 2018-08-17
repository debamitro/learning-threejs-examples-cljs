(defproject cljthreetest1 "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.946"]]
  :plugins [[lein-cljsbuild "1.1.5"]]
  :cljsbuild
           {:builds
             [{:source-paths ["src"]
                :compiler
                {:output-to "target/out.js"
                 :main cljthreetest1.core
                 :pretty-print true
                 :optimizations :simple}}]})
