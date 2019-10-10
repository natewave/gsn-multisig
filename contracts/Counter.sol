pragma solidity ^0.5.0;

import "@openzeppelin/contracts-ethereum-package/contracts/GSN/GSNRecipient.sol";

contract Counter is GSNRecipient {
  uint256 public value;

  function increase() public {
    value += 1;
  }

  function decrease() public {
    value -= 1;
  }

  function acceptRelayedCall(
    address relay,
    address from,
    bytes calldata encodedFunction,
    uint256 transactionFee,
    uint256 gasPrice,
    uint256 gasLimit,
    uint256 nonce,
    bytes calldata approvalData,
    uint256 maxPossibleCharge
  ) external view returns (uint256, bytes memory) {
    return _approveRelayedCall();
  }
}