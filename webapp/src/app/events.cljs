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
  (let [instance-factory (:instance-factory web3-details)
        _ (js/console.log (str "accounts: " (-> web3-details :web3 (.-eth) .getAccounts)))]
    (.preventDefault event)
    (-> web3-details :web3 (.-eth) .getAccounts
        (.then (fn [accounts]
                 (js/console.log (str "accounts: " accounts))
                 (-> instance-factory
                     .-methods
                     (.create accounts 0)
                     (.send (clj->js {:from (get accounts 0)}))
                     (.then (fn [a] (js/console.log (str "hey: " a))))))))))