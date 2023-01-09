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
;; Expand the above to see the result of the HTTP call. It has a lot of things we are not interested in.
;; Clojure has nifty in-built methods. `first` gives you first item in a collection.

;; Let's make this more precise
(get-in (get-recipes (first ingredients)) [:body :drinks])

(def results (get-in (get-recipes (first ingredients)) [:body :drinks]))
;; That's more like it!

;; ## Multi-Ingredient search 
;; Remember our goal was to use up as many of our items in bar cabinet as possible. This means we will have to look for recipes that use them all. However, API docs makes this type of search a paid feature. 

(ImageIO/read (File. "./src/clj/img/gotta_pay.png"))

(defn add-name [ingredient]
(fn [drinks] {(keyword ingredient) drinks}))

(defn get-recipes-v2
  "Searches for cocktail recipes given ingredient"
  [ingredient]
  (->
   (client/get (str filter-url ingredient) {:as :json})
   (get-in [:body :drinks])
   ((add-name ingredient))))

(def recipes (apply merge (map get-recipes-v2 ingredients)))

(defn flatten-ingredients [[ingredient drinks]]
  (map (fn [drink] (assoc drink :ingredient ingredient)) drinks))

(def with-ingredients (apply concat (map flatten-ingredients recipes)))

(def drink-ids (group-by :idDrink with-ingredients))

(defn summarize [[id drinks]]
  {
   :idDrink id 
   :strDrink (:strDrink (first drinks))
   :strDrinkThumb (:strDrinkThumb (first drinks))
   :count (count drinks)
   :ingredients (map :ingredient drinks)
   }
  )

(def drink-summary (map summarize drink-ids))

;; Let us see how many ingredient possibilities are there
(set (map :count drink-summary))
;; We see there are recipes that use 1 ingredient, or 2 ingredients.
;; unfortunately nothing that uses 3 or more.

(def possible-drinks (filter #(= (:count %) 2) drink-summary))

(defn get-thumbnail [drink]
  (ImageIO/read (URL. (:strDrinkThumb drink))))

(def recipe-url "http://thecocktaildb.com/api/json/v1/1/lookup.php?i=")

(defn get-recipe-instructions [drink-id]
  (->
   (client/get (str recipe-url drink-id) {:as :json})
   (get-in [:body :drinks 0 :strInstructions])))

{:nextjournal.clerk/visibility {:code :hide :result :hide}}
(require '[nextjournal.clerk :as clerk])

(defn caption [text url]
  (clerk/html
   [:a {:href url}
    [:span.text-slate-500.text-xs.text-center.font-sans text]
    ]
   ))

(defn styled-image [title url image ]
  (clerk/col image (caption title url)))

(def images-col (map #(-> %
                          (get-thumbnail)
                          ((partial styled-image (:strDrink %)
                                    (str recipe-url (:idDrink %)))))
                     possible-drinks))
{:nextjournal.clerk/visibility {:code :hide :result :show}}

(apply clerk/row images-col)
