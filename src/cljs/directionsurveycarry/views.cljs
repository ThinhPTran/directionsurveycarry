; namespace is extracted into a separate src folder in order to be reused in composite app examples
(ns directionsurveycarry.views
  (:require [goog.dom :as gdom]
            [directionsurveycarry.actions :as actions]
            [directionsurveycarry.db :as mydb]
            [cljs.core.match :refer-macros [match]]
            [reagent.ratom :refer [reaction]]))

(def -initial-model {:inputtext ""
                     :localname ""})

(defn -on-signal
  [model signal _dispatch-signal dispatch-action]
  (match signal
         ; start and stop are required because counter is used as a subapp in another example
         :on-start nil
         :on-stop nil

         [:on-change-input-text inputtext]
         (dispatch-action [:update-inputtext inputtext])

         :on-change-username
         (let [inputtext (:inputtext @model)]
           (dispatch-action [:update-user inputtext]))




         )
  )

(defn -on-action
  [model action]
  (match action
         [:update-inputtext inputtext]
         (assoc model :inputtext inputtext)

         [:update-user username]
         (assoc model :localname username)

  ))

(defn view-model
  [model]
  {:inputtext (reaction (:inputtext @model))
   :localname (reaction (:localname @model))})

(defn loginform [inputtext localname dispatch]
    [:div.col-sm-2
     [:input
      {:id "my-input-box"
       :type "text"
       :value inputtext
       :onChange (fn [_]
                   (let [v (.-value (gdom/getElement "my-input-box"))]
                     (.log js/console "change something!!!: " v)
                     (dispatch [:on-change-input-text v])))}]
     [:button#btn-login
      {:type "button"
       :onClick (fn []
                  (.log js/console "logging in!!!")
                  (dispatch :on-change-username))}
      "Secure login!"]
     [:div (str "input text: " inputtext)]
     [:div (str "user name: " localname)]])

(defn view
  [{:keys [inputtext localname] :as _view-model} dispatch]
  [:div
   [:h2 "Welcome to my Carry experiment"]
   [:div.row
    [loginform @inputtext @localname dispatch]]])

(def blueprint {:initial-model -initial-model
                :on-signal     -on-signal
                :on-action     -on-action})




