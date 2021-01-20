# bank-account-kata
Provided kata:
### Bank account kata

Think of your personal bank account experience When in doubt, go for the simplest solution

#### Requirements
- Deposit and Withdrawal
- Account statement (date, amount, balance)
- Statement printing

The expected result is a service API, and its underlying implementation, that meets the expressed needs.
  Nothing more, especially no UI, no persistence.
#### User Stories
##### US 1:
In order to save money
As a bank client
I want to make a deposit in my account
##### US 2:
In order to retrieve some or all of my savings
As a bank client
I want to make a withdrawal from my account
##### US 3:
In order to check my operations
As a bank client
I want to see the history (operation, date, amount, balance) of my operations
### Comments
- This current version has no security ; authentication is "done" by injecting a "user-id" header that identifies the
  user
- This version supposes the user does not wish to abuse the system. Since the structures used aren't thread-safe,
  we could imagine a situation where the user deposits a certain amount of money and then runs
  many withdrawal requests at the same time. The "InsufficientFundsException"
  could be skipped thus allowing the user to "rob the bank"
- No Swagger or any other API description has been done, sorry about that. Check the "AccountController" class to
  get more info