; namespace is extracted into a separate src folder in order to be reused in composite app examples
(ns directionsurveycarry.views
  (:require [goog.dom :as gdom]
            [directionsurveycarry.actions :as actions]
            [directionsurveycarry.db :as mydb]
            [directionsurveycarry.utils :as utils]
            [cljs.core.match :refer-macros [match]]
            [cljsjs.handsontable]
            [cljsjs.highcharts]
            [reagent.ratom :refer [reaction]]))

(def -initial-model {:inputtext ""
                     :localname ""
                     :localtableconfig utils/init-tableconfig})

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

         [:on-set-value inputval]
         (let [changeDatas (mapv #(assoc-in % [3] (js/parseFloat (get-in % [3]))) inputval)
               tmpchangeData (first changeDatas)
               row (get tmpchangeData 0)
               col (get tmpchangeData 1)
               val (get tmpchangeData 3)
               changeData (-> {}
                              (assoc :row row)
                              (assoc :col col)
                              (assoc :val val))]
           (if (and (some? row)
                    (some? col)
                    (some? val))
             (dispatch-action [:set-value changeData])
             ))

         )
  )

(defn -on-action
  [model action]
  (match action
         [:update-inputtext inputtext]
         (assoc model :inputtext inputtext)

         [:update-user username]
         (assoc model :localname username)

         [:set-value changeDatas]
         (actions/set-action model changeDatas)

  ))

(defn view-model
  [model]
  {:inputtext (reaction (:inputtext @model))
   :localname (reaction (:localname @model))
   :localtableconfig (reaction (:localtableconfig @model))})

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

(defn mylocaltable
  [inputtableconfig dispatch]
  (let [tableconfig inputtableconfig
        table (atom {:table nil})]
    [:div.col-sm-4
     [:div
      {:style {:min-width "310px" :max-width "800px" :margin "0 auto"}
       :ref (fn [mydiv]
              (if (some? mydiv)
                (swap! table assoc :table
                       (js/Handsontable mydiv (clj->js (assoc-in tableconfig [:afterChange] #(do
                                                                                               (dispatch [:on-set-value (js->clj %)]))))))
                (let [mytable (:table @table)]
                  (if (some? mytable)
                    (do
                      (.destroy mytable)
                      (swap! table assoc :table nil))))))}]]))

(defn mylocalchart
  [inputtableconfig]
  (let [tableconfig inputtableconfig
        my-chart-config (utils/gen-chart-config-handson tableconfig)
        chart (atom {:chart nil})]
    [:div.col-sm-4
     [:div
      {:style {:height "100%" :width "100%" :position "relative"}
       :ref (fn [mydiv]
              (if (some? mydiv)
                (swap! chart assoc :chart (js/Highcharts.Chart. mydiv (clj->js @my-chart-config)))
                (let [mychart (:chart @chart)]
                  (if (some? mychart)
                    (do
                      (.destroy mychart)
                      (swap! chart :chart nil))))))}]]))

(defn view
  [{:keys [inputtext localname localtableconfig] :as _view-model} dispatch]
  [:div
   [:h2 "Welcome to my Carry experiment"]
   [:div.row
    [loginform @inputtext @localname dispatch]]
   [:div.row
    [mylocaltable @localtableconfig dispatch]
    [mylocalchart @localtableconfig]]])

(def blueprint {:initial-model -initial-model
                :on-signal     -on-signal
                :on-action     -on-action})




