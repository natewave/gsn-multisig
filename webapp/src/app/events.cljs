(ns app.events
  (:require [app.state :as state]))

(defn fetch-value [contract]
  (js/console.log (str "fetchign value")))
  ; (let [txn (-> contract .-methods .value)]
  ;   (-> (.call txn)
  ;       (.then (fn [value]
  ;                (swap! state/app-state assoc-in [:count] value))))))
(defn deploy
  [event web3-details]
  (let [instance-factory (:instance-factory web3-details)]
    (.preventDefault event)
    (-> instance-factory
        .-methods
        (.create 1 0)
        (.send)
        (.then (fn [a] (js/console.log (str "hey: " a)))))))