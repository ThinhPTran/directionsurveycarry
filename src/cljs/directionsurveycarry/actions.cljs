(ns directionsurveycarry.actions
  (:require [directionsurveycarry.db :as mydb]))


(defn onincrementaction1 [model]
  (.log js/console "Doing something on incrementaction1!!!")
  (update model :val1 inc))

(defn ondecrementaction1 [model]
  (.log js/console "Doing something on decrementaction1!!!")
  (update model :val1 dec))

(defn onincrementaction2 [model]
  (.log js/console "Doing something on incrementaction2!!!")
  (-> model
    (update :val1 inc)
    (update :val2 inc)))

(defn ondecrementaction2 [model]
  (.log js/console "Doing something on decrementaction2!!!")
  (-> model
    (update :val1 dec)
    (update :val2 dec)))

(defn donothing [model]
  model)

(defn clickchange [model]
  (swap! mydb/appstate assoc :test "aaa!!!!")
  model)

(defn update-something [model data]
  (.log js/console (str "appstate: " data))
  model)

