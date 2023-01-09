;; # Clojure 101
;; Here I try to list Q&A style, how you can do basic stuff in Clojure.

;; When you begin a new Clojure file, at the top of every file, you will see this:
(ns clj.hello-world)
;; This is a declaration of the namespace. Namespace allows your variables to stay contained within them. 

;; ### How to create a variable?
(def say "hello ")
;; In clojure we don't have variables like in other languages. We have symbols.
;; Symbols are like variables, but they are immutable. You can't change the value of a symbol.
;; But we can create a new symbol with the same name and a new value. This is called rebinding.
;; In other words that if you write a def again with name say then it is a new binding. 
;; ```clojure
;; (def say "bonjour ")
;; ```
;; But this is not same as changing the value, you cannot change it in a loop for example. It can only be
;; changed at lexical stage. (colloquially compile time)
;; But this practise is discouraged in clojure. You don't want to rebind, because it is confusing.

;; ### How to define a function?
(defn greet 
  "Creates a greeting"
  [name]
  (str say name))

;; Notice that you can add inline function documentation after the name of the function

;; ### How to call a function?
(greet "jane")

;; ### How to functional program in Clojure?
;; see "cocktail_recipes.clj"

;; __TL;DR__: Break down your function into small re-usable functions. That's pretty much functional way to program.  
