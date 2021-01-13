# bank-account-kata
Provided kata:
### Bank account kata

Think of your personal bank account experience When in doubt, go for the simplest solution

#### Requirements
- Deposit and Withdrawal
- Account statement (date, amount, balance)
- Statement printing
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
- Currently only unit tests are available. 
  Depending on how the application is expected to be deployed we could imagine
  having a REST API, a Swing interface or a console program
- This current version supposes only one user uses the application ;
  there is no notion of user authentication.
- This version supposes the user does not wish to abuse the system. Since the structures used aren't thread-safe,
  we could imagine a situation where the user deposits a certain amount of money and then runs
  many withdrawal requests at the same time. The "InsufficientFundsException"
  could be skipped thus allowing the user to "rob the bank"
- Currently the operations are stored in memory. This means that if the system crashes,
  the user will lose all his money. This could be problematic.
  The best way to insure this would be to store the operations in a database.