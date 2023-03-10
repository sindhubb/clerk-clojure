(defproject clerk-clojure "0.1.0-SNAPSHOT"
  :description "Clerk + Clojure"
  :url "https://github.com/sindhubb/clerk-clojure"
  :dependencies [[metosin/compojure-api "2.0.0-alpha31"]
                 [org.clojure/clojure "1.10.1"]
                 [ring/ring-jetty-adapter "1.6.3"]
                 [io.github.nextjournal/clerk "0.12.707"]
                 [clj-http "3.12.3"]
                 [cheshire "5.9.0"]]
  :ring {:handler server/app
         :init server/init}
  :main ^:skip-aot server
  :uberjar-name "clerk+clojure.jar"
  :aliases {"make-docs" ["run" "-m" "make-docs"]
            "start-clerk" ["run" "-m" "start-clerk"]}
  :profiles {:dev {:dependencies
                   [[javax.servlet/javax.servlet-api "3.1.0"]]
                   :plugins
                   [[lein-ring "0.12.5"]]}
             :uberjar {:aot :all
                       :omit-source true}}
  :repl-options {;; Defaults to 30000 (30 seconds)
                 :timeout 120000})
