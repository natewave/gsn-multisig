pragma solidity ^0.5.0;

import "@openzeppelin/contracts-ethereum-package/contracts/GSN/GSNRecipient.sol";


import "./GSNMultiSigWalletWithDailyLimit.sol";

contract MultisAccountWithDailyLimit is GSNRecipient {
    GSNMultiSigWalletWithDailyLimit[] deployedWallets;

    event ContractInstantiation(address sender, address instantiation);

    function initialize() initializer public
    {
        GSNRecipient.initialize();
    }

    function createMultisigWallet(address[] memory _owners, uint _required) public returns (address wallet)
    {
        GSNMultiSigWalletWithDailyLimit multisig = new GSNMultiSigWalletWithDailyLimit();
        multisig.initialize(_owners, _required);
        deployedWallets.push(multisig);
        wallet = address(multisig);

        emit ContractInstantiation(_msgSender(), wallet);
    }

    function getDeployedWallets() public view returns(GSNMultiSigWalletWithDailyLimit[] memory) {
        return deployedWallets;
    }

    function acceptRelayedCall(address relay, address from, bytes calldata encodedFunction, uint256 transactionFee, uint256 gasPrice, uint256 gasLimit, uint256 nonce, bytes calldata approvalData, uint256 maxPossibleCharge) external view returns (uint256, bytes memory) {
        return (0, "");
    }

}