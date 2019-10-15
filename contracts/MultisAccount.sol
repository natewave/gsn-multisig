pragma solidity ^0.5.0;

import "@openzeppelin/contracts-ethereum-package/contracts/GSN/GSNRecipient.sol";

import "./Factory.sol";
import "./MultiSigWallet.sol";

contract MultisAccount is GSNRecipient, Factory {
    /*
     * Public functions
     */
    /// @dev Allows verified creation of multisignature wallet.
    /// @param _owners List of initial owners.
    /// @param _required Number of required confirmations.
    /// @return Returns wallet address.

    // function initialize(address[] memory _owners, uint _required) initializer
    //     public
    // {
    //     MultiSigWallet.initialize(_owners, _required);
    //     register(address(this));
    // }

    function create(address[] memory _owners, uint _required) public returns (address wallet)
    {
        MultiSigWallet multis = new MultiSigWallet(_owners, _required);
        wallet = address(multis);
        register(wallet);
    }

    // accept all
    function acceptRelayedCall(address relay, address from, bytes calldata encodedFunction, uint256 transactionFee, uint256 gasPrice, uint256 gasLimit, uint256 nonce, bytes calldata approvalData, uint256 maxPossibleCharge) external view returns (uint256, bytes memory) {
        return (0, "");
    }

}