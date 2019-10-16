(ns app.views
  (:require ["@portis/web3/es" :default Portis]
            ["web3" :as Web3]
            [app.state :refer [app-state]]
            [app.events :refer [deploy fetch-value]]
            [shadow.resource :as rc]))

(def contract-abi (.parse js/JSON (rc/inline "./MultisAccount.json")))
(def portis-app-id "94134bbc-b4b7-4dd2-91c1-f0714c7fb3cd")
(def networks {:1 "mainnet" :3 "ropsten" :4 "rinkeby"})

;; hard coded for ropsten
;; change it to use your own deployed contract if you want to test eg. in ganache/local
(def network-id :4)
(def contract-addr "0xAc0866AB174ea82bf646B73683B8689900dE7Bad") ;; <- ropsten 
;; (def contract-addr "0x6F866Aee6a3c562968c461A8b7d63113B18c567B") ;; rinkeby

(defn init-web3 []
  ;; fromInjected to use with metamask
  ;; fromConnection to use with nodes, eg. local or infura
  (let [portis (new Portis
                    portis-app-id
                    "ropsten"
                    #js {:gasRelay true}
                    ;;(js-obj "gasRelay" true)
                    )]

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
     [:button.btn {:on-click #(deploy %1 web3-details)} "Deploy Multisig"]
     ;; [:button {:disabled true} (get @app-state :count)]
     ]))

(defn app []
  [:div
   [header]
   [deploy-multisig]])
