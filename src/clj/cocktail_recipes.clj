;; # Welcome! Let's make some cocktails!
;; Right now, in my bar cabinet there's some left over alcohol from last year's Valentine's day party. Let's find out what we can make using as many of the ingredients as possible. 

;; We will be using [The Cocktail DB](https://www.thecocktaildb.com/api.php) which is a free no-auth public api.

;; To make an HTTP call to this API, we will be using `clj-http` library. 
;; Before using any library, you have to require it at the top of the file.
(ns clj.cocktail-recipes
  (:require [clj-http.client :as client])
  (:import [javax.imageio ImageIO]
           [java.net URL]
           [java.io File]))

;; What do we have?

(def ingredients ["vodka", "bitters", "triple sec", "grenadine" "tobasco", "worcestershire"])

;; According to the API docs, we can search _by ingredient_

;; For code to be modular, store variables separate from the function
(def filter-url "http://thecocktaildb.com/api/json/v1/1/filter.php?i=")

;; Let's see what we can make with 'vodka'.
(defn get-recipes
  "Searches for cocktail recipes given ingredient"
  [ingredient]
  (client/get (str filter-url ingredient) {:as :json}))

(get-recipes (first ingredients))
;; Expand the above to see the result of the HTTP call. It has a lot of things we are not interested in. Fornuately, Clojure has nifty in-built methods. `first` gives you first item in a collection.

;; Let's make this more precise
(get-in (get-recipes (first ingredients)) [:body :drinks])

(def results (get-in (get-recipes (first ingredients)) [:body :drinks]))
;; That's more like it!

;; ## Multi-Ingredient search 
;; Remember our goal was to use up as many of our items in bar cabinet as possible. This means we will have to look for recipes that use them all. However, API docs makes this type of search a paid feature. 
{:nextjournal.clerk/visibility {:code :hide :result :show}}
(ImageIO/read (File. "./src/clj/img/gotta_pay.png"))
{:nextjournal.clerk/visibility {:code :show :result :show}}

;; We can get around this with help of Clojure. Let's us see how

(defn add-name [ingredient]
  (fn [drinks] {(keyword ingredient) drinks}))

;; Let us write a better implementation of `get-recipes`

(defn get-recipes-v2
  "Searches for cocktail recipes given ingredient"
  [ingredient]
  (->
   (client/get (str filter-url ingredient) {:as :json})
   (get-in [:body :drinks])
   ((add-name ingredient))))

;; You may see strange new syntax here. The ➡️ is a threading macro that takes output of an expression and inserts it as first input of next expressions. 

;; At this point, I encourage you to google terms that you are not familiar with. However, you needn't worry about them too much on what exactly these features are or how they work under the hood. Treat them like a black box for the time-being.

;; `get-in` allows you to extract values in nested maps
;; Notice that the last expression has _extra_ parenthesis around them, that's because we want the output of `add-name` function.

(def recipes (apply merge (map get-recipes-v2 ingredients)))

;; What's going on here? `map` returns a list of maps and because `merge` takes individual maps, we use `apply` to _apply_ merge on all of them.

(defn flatten-ingredients [[ingredient drinks]]
  (map (fn [drink] (assoc drink :ingredient ingredient)) drinks))

(def with-ingredients (apply concat (map flatten-ingredients recipes)))

(def drink-ids (group-by :idDrink with-ingredients))

(defn summarize [[id drinks]]
  {:idDrink id
   :strDrink (:strDrink (first drinks))
   :strDrinkThumb (:strDrinkThumb (first drinks))
   :count (count drinks)
   :ingredients (map :ingredient drinks)})

(def drink-summary (map summarize drink-ids))

;; Let us see how many ingredient possibilities are there
(set (map :count drink-summary))
;; We see there are recipes that use 1 ingredient, or 2 ingredients.
;; unfortunately nothing that uses 3 or more.

(def possible-drinks (filter #(= (:count %) 2) drink-summary))
;; here we filter recipes which use 2 ingredients

(defn get-thumbnail [drink]
  (ImageIO/read (URL. (:strDrinkThumb drink))))

(def recipe-url "http://thecocktaildb.com/api/json/v1/1/lookup.php?i=")

{:nextjournal.clerk/visibility {:code :hide :result :hide}}

(require '[nextjournal.clerk :as clerk])

(defn caption [text url]
  (clerk/html
   [:a {:href url}
    [:span.text-slate-500.text-xs.text-center.font-sans text]]))

(defn styled-image [title url image]
  (clerk/col {::clerk/opts {:width 100}} image (caption title url)))

(def images-col (map #(-> %
                          (get-thumbnail)
                          ((partial styled-image (:strDrink %)
                                    (str recipe-url (:idDrink %)))))
                     possible-drinks))

{:nextjournal.clerk/visibility {:code :hide :result :show}}

;; # Ta-da!
;; These are the recipes possible with exactly 2 ingredients
(apply clerk/row images-col)

;; Clicking on the title of the drink will take you to the recipe API page