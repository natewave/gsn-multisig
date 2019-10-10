# GSN PoC

A simple app demonstrating the use of the Gas Station Network (GSN)

# How to run

```bash
# run ganache in a separate terminal
$ ganache-cli
# compile contracts and write down the deployed address
$ openzepplin create
# deploy a local GSN (RelayHub + relayers)
# this step is not necessary if testing on rinkby or mainnet, GSN already exists there
$ npx oz-gsn run-relayer
# fund recipient
$ npx oz-gsn fund-recipient --recipient $address
```

At this point, the whole GSN and GSN-enabled smart contracts are operational on the local ganache instance

## Webapp
The web app is using clojurescript's reagent along with shadow-cljs build tool

```bash
$ npm install
$ shadow-cljs server
$ shadow-cljs watch app
```

visit localhost:3000

## GSN details
The GSN-enabled web3 provider is created using `@openzeppelin/network`. It will use a key generated on the spot to sign all transactions on behalf of the user and will use the GSN to relay them to the network.

