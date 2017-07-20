(ns directionsurveycarry.core
  (:require [directionsurveycarry.views :as views]
            [directionsurveycarry.db :as mydb]
            [reagent.core :as r]
            [carry.core :as carry]
            [carry-reagent.core :as carry-reagent]))

(enable-console-print!)

(defn main
  []
  (let [app (carry/app views/blueprint)
        [app-view-model app-view] (carry-reagent/connect app views/view-model views/view)]
    (r/render app-view (.getElementById js/document "root"))
    ((:dispatch-signal app) :on-start)
    (assoc app :view-model app-view-model)))

(def app (main))

;;;;;;;;;;;;;;;;;;;;;;;; Figwheel stuff
(defn before-jsload
  []
  ((:dispatch-signal app) :on-stop))

(defn on-jsload
  []
  #_(. js/console clear))

;;;;;;;;;;;;;;;;;;;;;;;; Check db change
(defn appstatechangehandler []
  (.log js/console "appstate changes!!!")
  ((:dispatch-signal app) [:on-update-something @mydb/appstate]))

(add-watch mydb/appstate  :appstatewatch appstatechangehandler)



