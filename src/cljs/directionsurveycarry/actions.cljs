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


;; (defn set-action [rawchangeDatas]
;;   (when (and (some? rawchangeDatas) (some? (:user/name @mydb/local-login)))
;;     (let [changeDatas (mapv #(assoc-in % [3] (js/parseFloat (get-in % [3]))) rawchangeDatas)]
;;       (.log js/console "set-action: " changeDatas)
;;       (doseq [changeData changeDatas]
;;         (.log js/console "changeData: " changeData)
;;         (send-message! app-state (create-msg :setTableVal {:user (:user/name @mydb/local-login)
;;                                                            :row (get-in changeData [0])
;;                                                            :col (get-in changeData [1])
;;                                                            :val (get-in changeData [3])
;;                                                            :inst (.getTime (js/Date.))}))))))










