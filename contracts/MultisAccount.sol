pragma solidity ^0.5.0;

import "@openzeppelin/contracts-ethereum-package/contracts/GSN/GSNRecipient.sol";
import "@openzeppelin/upgrades/contracts/Initializable.sol";

import "./Factory.sol";
import "./MultiSigWallet.sol";

contract MultisAccountFactory is Initializable, GSNRecipient, MultiSigWallet, Factory {
    /*
     * Public functions
     */
    /// @dev Allows verified creation of multisignature wallet.
    /// @param _owners List of initial owners.
    /// @param _required Number of required confirmations.
    /// @return Returns wallet address.
    function initialize(address[] memory _owners, uint _required) initializer
        public
    {
        MultiSigWallet.initialize(_owners, _required);
        register(address(this));
    }
}