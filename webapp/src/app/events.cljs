(ns app.events
  (:require [app.state :as state]))

(defn fetch-value [contract]
  (let [txn (-> contract .-methods .value)]
    (-> (.call txn)
        (.then (fn [value]
                 (swap! state/app-state assoc-in [:count] value))))))
(defn increment
  [event web3-details]
  (.preventDefault event)
  (-> web3-details
      (.then (fn [details]
               (-> (.enable js/window.ethereum)
                   (.then (fn [] details)))))
      (.then (fn [{:keys [contract sign-key]}]
               (-> contract
                   .-methods
                   (.increase)
                   ;;(.send (clj->js {:from "0xC41878Ccbc4516b19571bF7871dcEd911923D5b4"}))
                   (.send (clj->js {:from "0xC41878Ccbc4516b19571bF7871dcEd911923D5b4"}))
                   (.then (fn [_] (fetch-value contract))))))))

(defn decrement
  [event web3-details]
  (.preventDefault event)
  (-> web3-details
      (.then (fn [details]
               (-> (.enable js/window.ethereum)
                   (.then (fn [] details)))))
      (.then (fn [{:keys [contract sign-key]}]
               (-> contract
                   .-methods
                   (.decrease)
                   (.send (clj->js {:from (.-address sign-key)}))
                   (.then (fn [_] (fetch-value contract))))))))
