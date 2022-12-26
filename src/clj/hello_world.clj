;; # Clojure 101
;; Here I try to list Q&A style, how you can do basic stuff in Clojure.

;; When you begin a new Clojure file, at the top of every file, you will see this:
(ns clj.hello-world)
;; This is a declaration of the namespace. Namespace allows your variables to stay contained within them. 

;; Note that we use *kebab-case* for names in clojure. But for filenames we use *snake_case*.
;; `clj/hello_world.clj` has a namespace of `clj.hello-world`. Notice slashes became dots
;; and underscores became dashes.

;; ### How to create a symbol?
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

;; Sometimes we have a symbol which is a constant. This will cause clojure to directly
;; inline the value of the symbol in the compiled code.
(def ^:const pi 3.14159)
;; This `^:const` is called metadata. You can add as many metadata as you want to a symbol.

(:const (meta #'pi))

(:const (meta #'say))

;; metadatas are key value pairs, in this case :const is the key and true is the value.
;; We use this often to add documentation to our code.

;; ### How do we create a variable? 
;; This is complicated, and we usually don't need it. We can usually get away with writing all our state into a database. 
;; Clojure has different types of variables which we will cover in a later chapter.

;; ### How to define a function?
(defn greet 
  "Creates a greeting"
  [name]
  (str say name))

;; Notice that you can add inline function documentation after the name of the function. Note
;; this function does not print anything. It just returns a string. Such functions are called
;; pure functions because they don't have any effect on the world, also called side effect free.

;; ### How to call a function?
(greet "jane")

;; ### How to get documentation of a function?
;; Documentations are stored as a metadata, so you can get it as:
(:doc (meta #'greet))

;; But if you are in a REPL you can simply type
;; ```clojure
;; (doc greet)
;; -------------------------------
;; clj.hello-world/greet
;; ([name])
;; Creates a greeting
;; ```
;; How does doc work to get all the arguments of a function? As you might have guessed, 
;; it uses the metadata of the function. And one more technique called macros which will 
;; cover in a later chapter.

;; ### How to functional program in Clojure?
;; see "cocktail_recipes.clj"

;; __TL;DR__: Break down your function into small re-usable functions. That's pretty much functional way to program.  
