(ns app.events)

(defn deploy
  [event web3-details]
  (let [instance-factory (:instance-factory web3-details)]
    (.preventDefault event)
    (-> web3-details :web3 (.-eth) .getAccounts
        (.then (fn [accounts]
                 (js/console.log (str "accounts: " accounts))
                 (-> instance-factory
                     .-methods
                     (.createMultisigWallet accounts 1)
                     (.send (clj->js {:from (get accounts 0)}))
                     (.then (fn [a] (js/console.log (str "Deployment result: " (js->clj a)))))))))))