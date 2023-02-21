(ns  clj.variables
  (:require [nextjournal.clerk :as clerk]))

; # Recap
; Like we discussed in the previous section, we can use `def` to define 
; symbols not variables.
(def a "Old name" 
  {:first "Jane" :last "Smith"})

(def b "New name" 
  (assoc a :last "Jones"))

; Note that value of a hasn't changed.
a


b
; ## Structure Sharing

; Value of b as expected has last as "Jones". This is done efficiently
; without actually having to copy entire 'a' because it can share the
; internal structure with 'a'. This is also a benefit of immutability. 
; Since 'a' can't be changed, it can be shared without worrying about
; value of 'b' changing.
; This type of sharing is called https://en.wikipedia.org/wiki/Persistent_data_structure

; # Variables!
; But in real life we need changes to symbols. Not all clojure objects
; are immutable. Clojure is designed in such a way all these are 'references' 
; they just point to actual datastructures and not data themselves. 
; This is like pointers, you can point it to something new. 

; There are four of these. 
; 1. [Atoms](https://clojure.org/reference/atoms)
; 2. [Vars](https://clojure.org/reference/vars)
; 3. [Refs](https://clojure.org/reference/refs)
; 4. [Agents](https://clojure.org/reference/agents)

; ## Atoms
; Atoms are the simplest type of references in clojure. We can define
; an atom by simply using `atom` function. It takes the initial value of
; the atom as input. 
{:nextjournal.clerk/visibility {:result :hide}}
(def ^:nextjournal.clerk/no-cache 
  full-name 
  "Atom with initial value a" 
  (atom a))
{:nextjournal.clerk/visibility {:result :show}}

; There are two ways to check what is inside an atom. First is to use
; `deref` function.
(deref full-name)
; Second is to use `@` symbol. This is a 'reader macro' which expands
; deref. For our purposes we shall always use `@` because it is much simpler.
; All of atom, deref and `@` are defined in `clojure.core` namespace and doesn't
; need to be imported.
@full-name

; We change value of atom in two ways first, is to use `reset!` function.
{:nextjournal.clerk/visibility {:result :hide}}
(reset! full-name b)
{:nextjournal.clerk/visibility {:result :show}}
@full-name

; We can also use `swap!` function to change value of atom. Instead of saying
; what the new value is you tell how to update to get to the new value
{:nextjournal.clerk/visibility {:result :hide}}
(swap! full-name (fn [old-value] (assoc old-value :last "Mora")))
{:nextjournal.clerk/visibility {:result :show}}

@full-name

(def ^::clerk/no-cache counter "" (atom 0))

(deref counter)

(swap! counter inc)

(deref counter)