; namespace is extracted into a separate src folder in order to be reused in composite app examples
(ns directionsurveycarry.views
  (:require [directionsurveycarry.actions :as actions]
            [cljs.core.match :refer-macros [match]]
            [reagent.ratom :refer [reaction]]))

(def -initial-model {:val1 0
                     :val2 0})

(defn -on-signal
  [model signal _dispatch-signal dispatch-action]
  (match signal
         ; start and stop are required because counter is used as a subapp in another example
         :on-start nil
         :on-stop nil

         :on-increment1
         (dispatch-action :increment1)

         :on-decrement1
         (dispatch-action :decrement1)

         :on-increment2
         (dispatch-action :increment2)

         :on-decrement2
         (dispatch-action :decrement2)

         :on-increment-if-odd
         (when (odd? (:val @model))
           (dispatch-action :increment))

         :on-increment-async
         (.setTimeout js/window #(dispatch-action :increment) 1000)))

(defn -on-action
  [model action]
  (match action
         :increment1 (actions/onincrementaction1 model)
         :decrement1 (actions/ondecrementaction1 model)
         :increment2 (actions/onincrementaction2 model)
         :decrement2 (actions/ondecrementaction2 model)))

(defn view-model
  [model]
  {:counter1 (reaction (str "#" (:val1 @model)))
   :counter2 (reaction (str "#" (:val2 @model)))})

(defn view
  [{:keys [counter1 counter2] :as _view-model} dispatch]
  [:div
   [:div (str "counter1: " @counter1)] " "
   [:div (str "counter2: " @counter2)] " "
   [:button {:on-click #(dispatch :on-increment1)} "+1"] " "
   [:button {:on-click #(dispatch :on-decrement1)} "-1"] " "
   [:button {:on-click #(dispatch :on-increment2)} "+2"] " "
   [:button {:on-click #(dispatch :on-decrement2)} "-2"] " "
   [:button {:on-click #(dispatch :on-increment-if-odd)} "Increment if odd"] " "
   [:button {:on-click #(dispatch :on-increment-async)} "Increment async"]])

(def blueprint {:initial-model -initial-model
                :on-signal     -on-signal
                :on-action     -on-action})
