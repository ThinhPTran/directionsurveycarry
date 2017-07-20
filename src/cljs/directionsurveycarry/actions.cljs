(ns directionsurveycarry.actions)


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


