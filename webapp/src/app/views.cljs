(ns app.views
  (:require ["@openzeppelin/gsn-provider" :refer [GSNProvider]]
            ["web3" :as Web3]
            ["ethereumjs-wallet" :refer [generate]]
            [app.state :refer [app-state]]
            [app.events :refer [increment decrement]]
            [shadow.resource :as rc]))

(def contract-abi (.parse js/JSON (rc/inline "./Counter.json")))

;; FIXME: change it to use your own deployed contract for ganache
(def contract-addr "0xa3ad9637c1816Cb43E77eE25c0252Ad187BE7e58")

(defn init-web3 []
  (let [gsn-provider (new GSNProvider "http://localhost:8545" (clj->js {:signKey (.-privKey (generate))}))]
    (new Web3 gsn-provider)))

(defn header
  []
  [:div
   [:h1 "GSN-enabled demo counter"]])

(defn counter
  [web3]
  (let [contract (js/web3.eth.Contract. contract-abi contract-addr)
        _ (js/console.log (str "web3: " (.-version web3)))
        _ (js/console.log (str "contract: " (-> contract .-methods .-value)))
        _ (aset js/window "contract" contract)]
    [:div
     [:button.btn {:on-click #(decrement %1 contract)} "-"]
     [:button {:disabled true} (get @app-state :count)]
     [:button.btn {:on-click #(increment %1 contract)} "+"]]))

(defn app []
  (let [web3 (init-web3)]
    [:div
     [header]
     [counter web3]]))
