(ns directionsurveycarry.actions
  (:require [directionsurveycarry.utils :as utils :refer [create-msg]]
            [clojure.string :as str]))

;; (defn onincrementaction1 [model]
;;   (.log js/console "Doing something on incrementaction1!!!")
;;   (update model :val1 inc))

;; (defn ondecrementaction1 [model]
;;   (.log js/console "Doing something on decrementaction1!!!")
;;   (update model :val1 dec))

;; (defn onincrementaction2 [model]
;;   (.log js/console "Doing something on incrementaction2!!!")
;;   (-> model
;;     (update :val1 inc)
;;     (update :val2 inc)))

;; (defn ondecrementaction2 [model]
;;   (.log js/console "Doing something on decrementaction2!!!")
;;   (-> model
;;     (update :val1 dec)
;;     (update :val2 dec)))

;; (defn donothing [model]
;;   model)

;; (defn clickchange [model]
;;   (swap! mydb/appstate assoc :test "aaa!!!!")
;;   model)

;; (defn update-something [model data]
;;   (.log js/console (str "appstate: " data))
;;   model)


;; (defn user-login [name]
;;   (if (str/blank? name)
;;     (js/alert "Please enter a user name")
;;     (do
;;       (.log js/console (str "Logging in with user: " name))
;;       (swap! mydb/local-login assoc :user/name name)
;;       (let [usernames (:user/names @mydb/global-users)]
;;         (.log js/console (str "Usernames: " usernames))
;;         (if (in? usernames name)
;;           (.log js/console (str "User existed!!!"))
;;           (send-message! app-state (create-msg :addUser {:name name :pass "mypass"})))))))


(defn handle-user-change-MD [tableconfig action]
  (let [dataTable (:data tableconfig)
        rowIdx (:row action)
        colIdx (:col action)
        newMD (:val action)
        tmpDataTable1 (assoc-in dataTable [rowIdx colIdx] newMD)
        tmpDataTable1 (vec (sort #(compare (get %1 0) (get %2 0)) tmpDataTable1))
        tmpDataTable2 (assoc-in tmpDataTable1 [0 2] (* 180.0
                                                       (/
                                                         (js/Math.acos
                                                           (/ (double (get-in tmpDataTable1 [0 1])) (double (get-in tmpDataTable1 [0 0]))))
                                                         js/Math.PI)))
        newDataTable (reduce (fn [data rowIdx]
                               (let [md1 (get-in data [(- rowIdx 1) 0])
                                     md2 (get-in data [rowIdx 0])
                                     tvd1 (get-in data [(- rowIdx 1) 1])
                                     tvd2 (get-in data [rowIdx 1])
                                     dev3 (* 180.0
                                             (/
                                               (js/Math.acos (/ (double (- tvd1 tvd2)) (double (- md1 md2))))
                                               js/Math.PI))]
                                 (assoc-in data [rowIdx 2] dev3)))
                             tmpDataTable2
                             (range 1 (count tmpDataTable2)))
        newtableconfig (assoc tableconfig :data newDataTable)]
    newtableconfig))

(defn handle-user-change-TVD [tableconfig action]
  (let [dataTable (:data tableconfig)
        rowIdx (:row action)
        colIdx (:col action)
        newTVD (:val action)
        tmpDataTable1 (assoc-in dataTable [rowIdx colIdx] newTVD)
        tmpDataTable2 (assoc-in tmpDataTable1 [0 2] (* 180.0
                                                       (/
                                                         (js/Math.acos
                                                           (/ (double (get-in tmpDataTable1 [0 1])) (double (get-in tmpDataTable1 [0 0]))))
                                                         js/Math.PI)))
        newDataTable (reduce (fn [data rowIdx]
                                (let [md1 (get-in data [(- rowIdx 1) 0])
                                      md2 (get-in data [rowIdx 0])
                                      tvd1 (get-in data [(- rowIdx 1) 1])
                                      tvd2 (get-in data [rowIdx 1])
                                      dev3 (* 180.0
                                             (/
                                               (js/Math.acos (/ (double (- tvd1 tvd2)) (double (- md1 md2))))
                                               js/Math.PI))]
                                  (assoc-in data [rowIdx 2] dev3)))
                             tmpDataTable2
                             (range 1 (count tmpDataTable2)))
        newtableconfig (assoc tableconfig :data newDataTable)]
    newtableconfig))

(defn handle-user-change-Deviation [tableconfig action]
  (let [dataTable (:data tableconfig)
        rowIdx (:row action)
        colIdx (:col action)
        newDeviation (:val action)
        tmpDataTable1 (assoc-in dataTable [rowIdx colIdx] newDeviation)
        tmpDataTable2 (assoc-in tmpDataTable1 [0 1] (* (get-in tmpDataTable1 [0 0]) (Math/cos (* (/ (get-in tmpDataTable1 [0 2]) 180.0) Math/PI))))
        newDataTable (reduce (fn [data rowIdx]
                               (let [md1 (get-in data [(- rowIdx 1) 0])
                                     md2 (get-in data [rowIdx 0])
                                     tvd1 (get-in data [(- rowIdx 1) 1])
                                     dev2 (get-in data [rowIdx 2])
                                     tvd2 (+ tvd1 (* (- md2 md1) (js/Math.cos (* (/ dev2 180.0) js/Math.PI))))]
                                 (assoc-in data [rowIdx 1] tvd2)))
                           tmpDataTable2
                           (range 1 (count tmpDataTable2)))
        newtableconfig (assoc tableconfig :data newDataTable)]
    newtableconfig))

(defn handle-table-actions [tableconfig action]
  (let [colIdx (:col action)]
    ;(.log js/console "action: " action)
    (cond
      (= 0 colIdx) (handle-user-change-MD tableconfig action)
      (= 1 colIdx) (handle-user-change-TVD tableconfig action)
      (= 2 colIdx) (handle-user-change-Deviation tableconfig action))))

(defn set-action [model changeData]
  (let [mycurrenttableconfig (:localtableconfig model)
        newtableconfig (handle-table-actions mycurrenttableconfig changeData)
        newmodel (assoc model :localtableconfig newtableconfig)]
    (.log js/console (str "currenttableconfig: " mycurrenttableconfig))
    (.log js/console (str "changeData: " changeData))
    (.log js/console (str "newtableconfig: " newtableconfig))
    newmodel))










