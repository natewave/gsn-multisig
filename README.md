# GSN-enabled Multisig

A fork of gnosis multisigs to support the Gas Station Network (GSN)

# How to run

## On ganache / local env
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
For the purpose of the sample webapp, the GSN-enabled web3 provider is provided using portis.io

## Deployed contracts
Contracts have been deployed to ropsten at the following addresses:

* MultisAccount Factory instance: 0x06Ab0E124B3A36c9Cf879dD46DC8eb92729e2123
* MultisAccountWithDailyLimit factory instance: 0x58EB3B25202047E9B2ad9Bb5DE43Edec96b5EF44

## Deploy your own
Change settings in `networks.js` and add `.env` with infura key and 12-word mnemonic phrase:

```
INFURA_PROJECT_ID="ENTER INFURA PROJECT ID"
DEV_MNEMONIC="ENTER 12 WORD SEED PHRASE"
```

...then run

```shell
$ openzeppelin create
```