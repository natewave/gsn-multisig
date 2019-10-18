(ns app.views
  (:require ["@portis/web3/es" :default Portis]
            ["web3" :as Web3]
            [shadow.resource :as rc]))

(def contract-abi (.parse js/JSON (rc/inline "./MultisAccount.json")))
(def portis-app-id "94134bbc-b4b7-4dd2-91c1-f0714c7fb3cd")

;; (def contract-addr "0x06Ab0E124B3A36c9Cf879dD46DC8eb92729e2123") ;; MultisAccount.sol on ropsten
(def contract-addr "0x58EB3B25202047E9B2ad9Bb5DE43Edec96b5EF44") ;; MultisAccountWithDailyLimit.sol on ropsten

(defn init-web3 []
  ;; fromInjected to use with metamask
  ;; fromConnection to use with nodes, eg. local or infura
  (let [portis (new Portis
                    portis-app-id
                    "ropsten"
                    ;;(js-obj "gasRelay" true)
                    #js {:gasRelay true})]

    (new Web3 portis.provider)))

(defn header []
  [:div
   [:h1 "GSN-enabled Multisig"]])

(defn deploy-multisig []
  (let [web3 (init-web3)
        instance-factory (web3.eth.Contract. contract-abi contract-addr)
        web3-details {:web3 web3
                      :instance-factory instance-factory
                      :accounts (.getAccounts (.-eth web3))}]
    [:div
     [:button.btn {:on-click #(deploy %1 web3-details)} "Deploy Multisig"]]))

(defn app []
  [:div
   [header]
   [deploy-multisig]])
