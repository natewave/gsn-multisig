(ns app.views
  (:require ["@openzeppelin/network" :refer [fromInjected fromConnection ephemeral]]
            ;;["@openzeppelin/gsn-provider" :refer [GSNProvider]]
            ;; ["ethereumjs-wallet" :refer [generate]]
            ["web3" :as Web3]
            [app.state :refer [app-state]]
            [app.events :refer [increment decrement fetch-value]]
            [shadow.resource :as rc]))

(def contract-abi (.parse js/JSON (rc/inline "./Counter.json")))
(def sign-key (ephemeral))

(js/console.log (str "sign-key: " sign-key ", address: " (.-address sign-key)))

;; this is for ropsten (https://ropsten.etherscan.io/address/0x6F866Aee6a3c562968c461A8b7d63113B18c567B)
;; change it to use your own deployed contract if you want to test eg. in ganache/local
(def contract-addr "0x6F866Aee6a3c562968c461A8b7d63113B18c567B")

(defn init-web3 []
  ;; fromInjected to use with metamask
  ;; fromConnection to use with nodes, eg. local or infura
  (let [web3-context-promise (fromConnection "https://ropsten.infura.io/v3/f2dfa06b62db4226bc53595fd2af411f"
                                             (js-obj "gsn" (js-obj "signKey" sign-key)))
                                           ;; this doesn't work for some reason: (clj->js {:gsn {:signKey sign-key}}))
        ]
    (-> web3-context-promise
        (.then (fn [web3-context]
                 (let [web3 (.-lib web3-context)]
                   {:web3 web3
                    :accounts (.-accounts web3-context)}))))))

(defn header []
  [:div
   [:h1 "GSN-enabled demo counter"]])

(defn counter []
  (let [web3-details (-> (init-web3)
                         (.then (fn [{:keys [web3 accounts]}]
                                  (let [contract (js/web3.eth.Contract. contract-abi contract-addr)
                                        _ (fetch-value contract)]
                                    {:contract (js/web3.eth.Contract. contract-abi contract-addr)
                                     :web3 web3
                                     :accounts accounts
                                     :sign-key sign-key}))))]
    [:div
     [:button.btn {:on-click #(decrement %1 web3-details)} "-"]
     [:button {:disabled true} (get @app-state :count)]
     [:button.btn {:on-click #(increment %1 web3-details)} "+"]]))

(defn app []
  [:div
   [header]
   [counter]])
