(ns app.events
  (:require [app.state :refer [app-state]]))

(defn increment
  [event contract]
  (.preventDefault event)
  (let [increase (-> contract .-methods .value)]
    (-> (.call increase)
        (.then (fn [value]
                 (js/console.log (str "value: " value))
                 (swap! app-state update-in [:count] inc))))))

(defn decrement
  [event contract]
  (.preventDefault event)
  (swap! app-state update-in [:count] dec))
