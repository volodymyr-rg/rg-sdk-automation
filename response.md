
# AUTH_NO

Six digit code returned by the banking institution in response to successful auth-only, ticket, and purchase transactions.

# AVS_RESPONSE

One character response code returned in response to an Address Verification request in the transaction.  Refer to the AVSResponseCodes.pdf which summarizes the values returned for Address Verification.

# BALANCE_AMOUNT

When present, the amount remaining on a prepaid card. This value is not returned for all transactions. It is only present if the data is returned by the bank.

# BALANCE_CURRENCY

When present, the currency which denominates the BALANCE_AMOUNT variable. This value is not returned for all transactions. It is only present if the data is returned by the bank.

# BANK_RESPONSE_CODE

The response code from the bank or processor.

# CARD_BIN

This element carries the credit card issuer’s BIN which is the first 6 digits of the card that was used.

# CARD_COUNTRY

A comma-separated list indicating the country associated with the BIN of a card used in a credit card transaction.  The value returned can be NULL if the country is not currently known.

# CARD_DEBIT_CREDIT

The value in this field indicates whether the card type is associated with debit, credit, or both. Valid values for this field are provided below.

| Code | Description |
|------|-------------|
| 0    | Debit       |
| 1    | Credit      |
| 2    | Unknown     |

# CARD_DESCRIPTION

A free-form description of the card type, e.g. GOLD, PLATINUM, or PREPAID

# CARD_EXPIRATION

Expiration date of a card used in a credit card transaction.  The expiration date is returned in MMYY format.

# CARD_HASH

The one-way hash of card number used in a credit card transaction.  This hash value combined with the associated customer code can be used in subsequent transactions to perform purchases, voids, credits, etc..

# CARD_ISSUER_NAME

Bank/Issuing Organization.

# CARD_ISSUER_PHONE

Bank/Issuing organization phone number.

# CARD_ISSUER_URL

Issuing organization URL or URL of the website that contains relevant info.

# CARD_LAST_FOUR

Last four digits of the card number used in a credit card transaction.

# CARD_REGION

This field contains a comma-separated list indicating the geographic region associated with the card. Valid region codes are listed below. When a card belongs to more than one region, the regions will be provided in a comma-separated list, e.g. 1,2,3.

| Code | Description                           |
|------|---------------------------------------|
| 1    | USA                                   |
| 2    | Canada                                |
| 3    | Europe                                |
| 4    | Asia Pacific                          |
| 5    | Latin America & Caribbean             |
| 6    | Central Europe, Middle East, & Africa |

# CARD_TYPE

Indicator of card type used for a credit card transaction.  The following table outlines the list of values that can be returned.

| Code         | Description      |
|--------------|------------------|
| AMEX         | American Express |
| CARTEBLANCHE | Carte Blanche    |
| CB           | Cartes Bancaires |
| DINERSCLUB   | Diners Club      |
| DISCOVER     | Discover         |
| JCB          | JCB              |
| MAESTRO      | Maestro          |
| MC           | MasterCard       |
| SOLO         | Solo             |
| VISA         | Visa             |

# CARD_UPDATED

Possible values: TRUE or FALSE.

If the returned cardUpdated field is set to TRUE it means that RocketGate's MIT Card Updater service has updated either the PAN of the card that was provided in the original request or the expiry date of the card that was provided in the original request, or both the PAN and the expiry date.

When CARD_UPDATED is TRUE, integrators will find the updated card hash in the CARD_HASH (cardHash) field in the Gateway Response. Integrators will also find the updated expiry date, last-four digits, card description, and BIN in the CARD_EXPIRATION, CARD_LAST_FOUR, CARD_DESCRIPTION, and CARD_BIN fields of the Gateway Response.

# CVV2_CODE

One character response code return in response to CVV2 request in the transaction.  The following table outlines the list of values that can be returned.

| Code | Description                      |
|------|----------------------------------|
| M    | Match                            |
| N    | No match                         |
| P    | Not processed                    |
| S    | Should have been present         |
| U    | Issuer unable to process request |

# ECI

Electronic Commerce Indicator (ECI) is a value that is returned from the Visa/MasterCard Directory Server to indicate the authentication results of your customer’s credit card payment on 3D Secure.

| Visa Code | Description                                                                                                                                                                                                         |
|-----------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 05        | Both cardholder and card issuing bank are 3D enabled. 3D card authentication is successful                                                                                                                          |
| 06        | Either cardholder or card issuing bank is not 3D enrolled. 3D card authentication is unsuccessful, in sample situations as:<br><br>1. 3D cardholder not enrolled<br><br>2. Card issuing bank is not 3D Secure ready |
| 07        | Authentication is unsuccessful or not attempted. The credit card is either a non-3D card or card issuing bank does not handle it as a 3D transaction                                                                |

| MasterCard Code | Description                                                                                                                                                                                                         |
|-----------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 00              | Authentication is unsuccessful or not attempted. The credit card is either a non-3D card or card issuing bank does not handle it as a 3D transaction                                                                |
| 01              | Either cardholder or card issuing bank is not 3D enrolled. 3D card authentication is unsuccessful, in sample situations as:<br><br>1. 3D Cardholder not enrolled<br><br>2. Card issuing bank is not 3D Secure ready |
| 02              | Both cardholder and card issuing bank are 3D enabled. 3D card authentication is successful                                                                                                                          |

# EXCEPTION

The description for an error message.

# IOVATION_TRACKING_NO

The Transaction ID in Iovation Platform.

# IOVATION_DEVICE

The Iovation Device identifier

# IOVATION_RESULTS

The Iovation Reputation Manager recommendation. The following are the valid return values.

- A = Accept
- R = Review
- D = Decline

# IOVATION_SCORE

The risk score total of all rules trigger assigned by Iovation.

# IOVATION_RULE_COUNT

The Number of rules triggered in Iovation scan.

# IOVATION_RULE_TYPE_N

Iovation category of rule triggered. There can be multiple results. If IOVATION_RULE_COUNT is N, then there would be N rule types accessed by IOVATION_RULE_TYPE_1…IOVATION_RULE_TYPE_N

# IOVATION_RULE_REASON_N

Iovation descriptive rule name triggered. There can be multiple results. If IOVATION_RULE_COUNT is N, then there would be N rule reasons accessed by IOVATION_RULE_REASON_1…IOVATION_RULE_REASON_N

# IOVATION_RULE_SCORE_N

Iovation rule score triggered. There can be multiple results. If IOVATION_RULE_COUNT is N, then there would be N rule scores accessed by IOVATION_RULE_SCORE_1…IOVATION_RULE_SCORE_N

# IS_BANK_HARD_DECLINE

The transaction is subject to the merchant option, ProcessorHardDeclineCodes (e.g. TSYSHardDeclineCodes). This field is only returned when the merchant has configured the option on the processor where the transaction was processed.

# JOIN_AMOUNT

The amount billed for the initial join operation.

# JOIN_DATE

The date upon which the membership was created.

# LAST_BILLING_AMOUNT

The amount of the last successful billing operation performed

# LAST_BILLING_DATE

The date of the last successful billing operation performed.

# LAST_REASON_CODE

The REASON_CODE of the last subscription transaction performed.

# MERCHANT_ACCOUNT

Merchant account to which the transaction was applied. The value is a merchant account sequence ID.

# MERCHANT_ADVICE_CODE

The scheme's advice code indicates whether merchants should retry a given declined transaction. It is usually only available for Mastercard declined transactions, some processors may return this on other card brands. Possible values are:

| Possible values | Description                                          |
|-----------------|------------------------------------------------------|
| 01              | Updated or additional information needed             |
| 02              | Try again later                                      |
| 03              | Do not try again                                     |
| 04              | Token requirements not fulfilled for this token type |
| 21              | Payment cancellation                                 |
| 22              | Merchant does not qualify for product code           |
| 24              | Retry after 1 hour                                   |
| 25              | Retry after 24 hours                                 |
| 26              | Retry after 2 days                                   |
| 27              | Retry after 4 days                                   |
| 28              | Retry after 6 days                                   |
| 29              | Retry after 8 days                                   |
| 30              | Retry after 10 days                                  |

# MERCHANT_CUSTOMER_ID

In the case where transactions are declined with REASON_CODE 208, 209, 210, 218, duplicate Subscriptions found this element carries the MERCHANT_CUSTOMER_ID of the existing/duplicate subscription.

# MERCHANT_INVOICE_ID

In the case where transactions are declined with REASON_CODE 208, 209, 210, 218, duplicate Subscriptions found this element carries the MERCHANT_INVOICE_ID of the existing/duplicate subscription.

# MERCHANT_PRODUCT_ID

Optional identification value used to identify the product of a transaction within RocketGate reports. For example, many reports allow you to group sales by Product ID. This string value is optional and is provided as a convenience to the merchant.

# MERCHANT_SITE_ID

Optional identification number used to identify the source of a transaction within RocketGate reports. For example, many reports allow you to group sales by Site ID. This integer value is optional and is provided as a convenience to the merchant.

# PAN_TOKEN

Indicates whether the transaction was completed with a PAN (a Primary Account Number, a credit card number) or with a network token instead of a PAN.

Returns "pan" for PAN transactions or "network_token" for network token transactions.

# PARTIAL_AUTH

Will be set to TRUE and returned in the payload if the transaction was processed as a partial authorization (also known as a partial approval).

# PAY_HASH

This element carries a one-way hash of the credit card or EuroDebit account used in the transaction.  This value can be submitted in subsequent transactions to identify the card number or EuroDebit account against which a transaction is to be performed.

# PAY_LAST_FOUR

This element carries the last four digits of the credit card or EuroDebit account used in the transaction.

# PAY_TYPE

This element carries the type of payment mechanism used in the transaction.  Currently, two types of payment mechanisms are supported.

- CREDIT – The transaction was performed using a credit card.
- DEBIT – The transactions were performed using a EuroDebit account.

# PROCESSOR_3DS

This element indicates that the 3DSecure authentication is performed by the processor's MPI.  Depending on the processor, the 3DS flow and action to be performed by the merchant may be different than using RocketGate's MPI. If you are currently using a processor that performed 3DS Please contact our integration team to learn the 3DS flow for your processor.

TRUE - The authentication is performed by the processor.

FALSE or null - The authentication is performed by RocketGate.

# RESPONSE_CODE

Integer code that indicates the success or failure of a transaction.

A value of zero indicates that the transaction completed successfully.  In this instance, the GatewayResponse object contains details of the transaction, e.g. auth-code, etc.

A non-zero value indicates that the transaction failed or was rejected.  In this instance, the GatewayResponse object contains a REASON_CODE that explains the failure.

The following table outlines the response codes that can be returned.

| Code | Description                                 | Associated Reason Codes |
|------|---------------------------------------------|-------------------------|
| 0    | Success                                     | Always 0                |
| 1    | Bank Decline                                | 100 through 199         |
| 2    | RocketGate Scrubbing Decline                | 200 through 299         |
| 3    | System Error                                | 300 through 399         |
| 4    | Rejected: Missing Fields / Field Validation | 400 through 499         |

# RETRIEVAL_ID

The unique identifier of the transaction that is returned by the processor. Each processor has different formats of the value.

# REASON_CODE

Integer code that provides the reason a transaction has failed.

The [GatewayDeclineReasonCodes](https://help.rocketgate.com/support/solutions/articles/28000018169-gatewayresponse-error-decline-codes) document outlines the reason codes that can be returned.  Note that this file is subject to change.

# REBILL_AMOUNT

The amount which the subscription will rebill. This is returned when calling PerformRebillUpdate().

# REBILL_CURRENCY

The 3-character (ISO 4217) currency code representing the currency of the rebill transaction.

# REBILL_CYCLE

A number representing the subscription's rebill cycle at the time of the request.

The REBILL_CYCLE field will be empty if the transaction is not a subscription payment or if the rebill cycle is unknown.

Applies to RocketGate managed rebills.

# REBILL_DATE

The date on which the subscription will rebill. This is returned when calling  PerformRebillUpdate().

# REBILL_END_DATE

The date on which the subscription will be canceled. This is returned when calling  PerformRebillCancel().

# REBILL_FREQUENCY

The current rebilling cycle.

The following table summarizes the valid values for this argument. A numeric value specifies the frequency of rebilling in DAYS (For example rebill-freq=7 would specify a subscription that would renew every 7 days).

| Value         | Meaning                                          |
|---------------|--------------------------------------------------|
| MONTHLY       | Rebilling occurs once per month.                 |
| QUARTERLY     | Rebilling occurs once per quarter, i.e. 3 months |
| SEMI-ANNUALLY | Rebilling occurs semi-annually, i.e. 6 months    |
| ANNUALLY      | Rebilling occurs annually.                       |
| Numeric Value | Rebilling occurs once per # of days specified    |

# REBILL_STATUS

The current rebilling status.

| Value     | Meaning                   |
|-----------|---------------------------|
| ACTIVE    | Subscription is Active.   |
| SUSPENDED | Subscription is Suspended |

# ROCKETPAY_INDICATOR

Will only be present in the response payload for merchants who use RocketPay (also known as "remember me"). When present, this the field will have one of the following values:

| Value | Meaning                                                                                                      |
|-------|--------------------------------------------------------------------------------------------------------------|
| 1     | RocketPay is enabled for the merchant, but the customer did not use RocketPay for this transaction.          |
| 2     | A new RocketPay wallet (also known as "remember me") was created for the paying customer.                    |
| 3     | The customer used an existing RocketPay wallet ("remember me"), and this is a new customer for the merchant. |
| 4     | The customer used an existing RocketPay wallet and is a returning customer to the merchant's site.           |

# SCHEME_SETTLEMENT_DATE

Transaction settlement date returned by credit card schemes.  This is currently only returned by MasterCard and Maestro.

This value is used in specific conditions for the card on file (CoF) feature.

# SCHEME_TRANSACTION_ID

Transaction id returned by credit card schemes.

This value is used in specific conditions for the card on file (CoF) feature.

# SCRUB_RESULTS

A string containing the results of RocketGate fraud scrubbing.  This element is a string in the format shown below, where the “#” is replaced with a non-negative integer value.

**NEGDB=#,PROFILE=#,ACTIVITY=#**

The count provided for NEGDB indicates the number of negative database entries found that match the billing information provided by the user.  The count provided for PROFILE indicates that the number of data inconsistencies found in the billing information provided by the user.  The count provided for ACTIVITY provides an indicator of the number of recent risk-related transactions associated with the user.

This element will be populated only be if fraud scrubbing was set to TRUE or IGNORE in the associated request.

# SETTLED_AMOUNT

The settled value of the transaction performed on behalf of the merchant.

This value is different than the requested amount when the currency requested is different than your base currency.

# SETTLED_CURRENCY

The currency in which the transaction was settled.

This value is configured once at setup and represents the currency which you settle and report your revenues in.

# TRANSACT_ID

Unique sixteen-digit alpha-numeric value assigned to the transaction.

This ID is assigned by the RocketGate network and is unique to the transaction.  It can be used to reference the transaction in reports or other inquiries.  This ID must be included in subsequent void and ticket transactions to identify the original transaction that is to be voided or ticketed.

# TRANSACTION_TIME

The transaction date and time as a string in the following format.

`yyyy-MM-dd HH:mm:ss`

# UDF01

The user-defined field. It returns the value provided in the GatewayRequest, param UDF01.

# UDF02

The user-defined field. It returns the value provided in the GatewayRequest, param UDF02.  

