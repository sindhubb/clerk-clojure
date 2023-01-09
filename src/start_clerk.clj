(ns start-clerk
  (:require [nextjournal.clerk :as clerk])
  (:gen-class))

(defn -main
  [port]
  (clerk/serve! {:port (Integer/parseInt port) :watch-paths ["src/clj"]}))
