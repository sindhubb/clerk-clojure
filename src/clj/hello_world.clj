;; # Clojure 101
;; Here I try to list Q&A style, how you can do basic stuff in Clojure.

;; When you begin a new Clojure file, at the top of every file, you will see this:
(ns clj.hello-world)
;; This is a declaration of the namespace. Namespace allows your variables to stay contained within them. 

;; ### How to create a variable?
(def say "hello ")

;; ### How to define a function?
(defn greet 
  "prints a greeting"
  [name]
  (str say name))

;; Notice that you can add inline function documentation after the name of the function

;; ### How to call a function?
(greet "jane")

;; ### How to functional program in Clojure?
;; see "cocktail_recipes.clj"

;; __TL;DR__: Break down your function into small re-usable functions. That's pretty much functional way to program.  