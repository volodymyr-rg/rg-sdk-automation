# ACCOUNT_NO

Customer’s checking/savings account number used in ACH transactions.

Either the ACCOUNT_NO or CARD_HASH element is required in all ACH purchase transactions.  If both the ACCOUNT_NO and CARD_HASH elements are used in the same request, the ACCOUNT_NO element takes precedent.

# AFFILIATE

This argument carries a merchant defined affiliate code.  This data is displayed in a number of reports, such as the chargeback and sales reports, and in the customer details section of the support tools.

This element is optional and is provided as a convenience to the merchant.  The maximum length for this field is 32 characters.

# AMOUNT

Value of the transaction.  The element is required for PeformPurchase and PerformAuthOnly and must be between 1.00 and 999,999.99 inclusive. Additionally, $0 AuthOnly (card validation) when available through the back end processor.

This value can be omitted for void and credit transactions as well as credit card ticket transactions if the amount is the same as the original purchase or auth-only transaction.

# AVS_CHECK

Turn on/off Address Verification.  By default, Address Verification is off.  The following table summarizes the valid values for this element.

| Value  | Meaning                                                                                             |
|--------|-----------------------------------------------------------------------------------------------------|
| YES    | Address Verification is to be performed.                                                            |
| NO     | Address Verification is not to be performed.                                                        |
| IGNORE | Perform Address Verification and return the result code, but do not take action based upon results. |

# BILLING_ADDRESS

Customer’s billing street address.  This element is optional but should be provided if Address Verification is enabled.

Maximum length for this field is 128 characters.

# BILLING_CITY

Customer’s billing city.  This element is optional but should be provided if Address Verification is enabled.

Maximum length for this field is 32 characters.

# BILLING_COUNTRY

Customer’s billing country.  This element is optional but should be provided if Address Verification is enabled. If provided, the two-character BILLING_COUNTRY must comply with [the ISO 3166-1 alpha-2 standards for country codes](https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2).

Maximum length for this field is 2 characters.

# BILLING_STATE

Customer’s billing state.  This element is optional but should be provided if Address Verification is enabled.

Maximum length for this field is 32 characters.

# BILLING_TYPE

Indicator for the type of billing operation. This field is optional and can contain any single character value. By convention, the RocketGate system uses the following indicators.

One time non-membership sale – S (This is the default)

Initial membership billing/signup – I

Trial membership – T

Conversion of trial to full membership – C

Instant upgrade of trial membership to full membership – U

Standard rebill of membership – R

Maximum length for this field is 1 character.

# BILLING_ZIPCODE

Customer’s billing zip code.  This element is optional but should be provided if Address Verification is enabled.

Maximum length for this field is 32 characters.

Note: For US addresses, the ZIP+4 code (i.e. 12345-6789) is supported.

# BROWSER_ACCEPT_HEADER

This field is only used for 3D Secure transactions and is required for merchants who have configured the “bypass” processing flow. See the 3D-Secure Programmers Guide.

This parameter is expected to hold the “Accept” value retrieved from the headers submitted by the cardholder’s browser.

# BROWSER_USER_AGENT

This field is only used for 3D Secure transactions and is required for merchants who have configured the “bypass” processing flow. See the 3D-Secure Programmers Guide.

This parameter is expected to hold the “User-Agent” value retrieved from the headers submitted by the cardholder’s browser.

# CAPTURE_DAYS

When used with an authorization transaction, this parameter specifies how many days after the authorization a capture should be performed. As an example, if a CAPTURE_DAYS value of 3 is used with an authorization performed on 2015-07-20 15:00:00, the capture will be scheduled for 2015-07-23 15:00:00.

This can be used for One Time Sales, Trial and Regular Subscription Joins.

For additional info: https://support.rocketgate.com under Processing->Gateway->Delayed
capture via CAPTURE_DAYS param

Valid values for this are 1 through 5

# CARD_HASH

One-way hash representing a customer’s credit card number.

A one-way hash value representing a card number is returned for each credit card transaction.  This hash value can be submitted in subsequent transactions in place of other credit card elements, i.e. CARD_NO, EXPIRATION_MONTH, EXPIRATION_YEAR.

To be valid, the CARD_HASH element must be used in conjunction with the MERCHANT_CUSTOMER_ID element.  When the CARD_HASH element is used, it is not necessary to submit values for the EXPIRATION_MONTH and EXPIRATION_YEAR elements.  However, values can be submitted for these elements to update the expiration date of the associated card in the RocketGate database.

Either the CARD_HASH or CARD_NO element is required in all credit card purchase or auth-only transactions.  If both the CARD_NO and CARD_HASH elements are used in the same request, the CARD_NO element takes precedent.

# CARDNO

Customer’s credit card number.

Either the CARD_NO or CARD_HASH element is required in all credit card purchase or auth-only transactions.  If both the CARD_NO and CARD_HASH elements are used in the same request, the CARD_NO element takes precedent.

# CLONE_CUSTOMER_RECORD

This is a flag value. I must be set to TRUE, ON, YES, or 1 to enable the cloning function.

When enabled, the customer data (card, address, etc.) identified by MERCHANT_CUSTOMER_ID will be copied and used to make a purchase as the customer specified by CLONE_TO_CUSTOMER_ID. In the process, a new customer record will be created for CLONE_TO_CUSTOMER_ID.

The system checks to ensure the MERCHANT_CUSTOMER_ID actually exists. It also verifies that the value of CLONE_TO_CUSTOMER_ID is not the same as MERCHANT_CUSTOMER_ID.

# CLONE_TO_CUSTOMER_ID

This is the id of the customer that is to be created. If the purchase or auth-only transaction is successful, a new customer record will be created for CLONE_TO_CUSTOMER_ID. If the transaction does not succeed, no new record is created.

If the parameters specified in CLONE_CUSTOMER_RECORD and/or CLONE_TO_CUSTOMER_ID are invalid, error code 451 will be returned.

# COF_FRAMEWORK

Specifies the transaction type under the Credential-on-File framework. This is an optional element. The following table summarizes the valid values for this element.

| Value | Meaning                         |
|-------|---------------------------------|
| CIT   | Customer Initiated Transaction. |
| MIT   | Merchant Initiated Transaction. |

The value provided overrides the default COF flag set by RocketGate.

# CURRENCY

Currency of the transaction that is to be processed against the user’s credit card.  This element is optional.  If this element is omitted, the transaction will be conducted in US Dollars (USD).

For a complete list of valid currency codes, please refer to the document entitled “Currency Codes for Multi-currency Processing”.

# CUSTOMER_FIRSTNAME

Cardholder’s first name.  This element is optional.

Maximum length for this field is 32 characters.

# CUSTOMER_LASTNAME

Cardholder’s first name.  This element is optional.

Maximum length for this field is 32 characters.

# CUSTOMER_PASSWORD

Cardholder’s password.  This element is optional and typically only provided when RocketGate is providing customer support services which involve password updates.

# CUSTOMER_PHONE_NO

Cardholder’s phone number.  This element is optional.

# CVV2

CVV2 value from a customer’s credit card.  This element is optional.

# CVV2_CHECK

Turn on/off CVV2 Verification.  This element is optional.  By default, CVV2 Verification is off.  The following table summarizes the valid values for this element.

| Value  | Meaning                                                                                          |
|--------|--------------------------------------------------------------------------------------------------|
| YES    | CVV2 Verification is to be performed.                                                            |
| NO     | CVV2 Verification is not to be performed.                                                        |
| IGNORE | Perform CVV2 Verification and return the result code, but do not take action based upon results. |

# EMAIL

Customer’s email address.  This element is optional.

# EMBEDDED_FIELDS_TOKEN

The session ID that returned from Embedded Fields API. This element should be present for merchants who use the Embedded Fields API when making the GatewayRequest for the payment.

This element is contional.

# EXPIRATION_MONTH

Expiration month on the customer’s credit card.  Must be an integer value in the range of 1 through 12.

This element is required for all credit card transactions in which the CARDNO element is used.  This element is optional for credit card transactions that use the CARD_HASH element.

# EXPIRATION_YEAR

Expiration year on the customer’s credit card.  Must be an integer value in the range 7 through 99, or 2007 through 2099.

This element is required for all credit card transactions in which the CARDNO element is used.  This element is optional for credit card transactions that use the CARD_HASH element.

# FAILURE_POSTBACK_URL

The URL to receive the postback when the payment authorization is declined. This element is optional.

This option is to be used in conjunction with the [Build Payment Link API](https://help.rocketgate.com/support/solutions/articles/28000024349-build-payment-link-api-simplified-3ds-flow-) (simplified 3DS flow).

This value will override the pre-configured merchant option, [HostedPageDeclinePostbackURL](https://help.rocketgate.com/support/solutions/articles/28000022694-hosted-page-hostedpagedeclinepostbackurl).

# FAILURE_URL

Redirect URL when the payment authorization failed. This element is optional.

This option is to be used in conjunction with the [Build Payment Link API](https://help.rocketgate.com/support/solutions/articles/28000024349-build-payment-link-api-simplified-3ds-flow-) (simplified 3DS flow).

This value will override the pre-configured merchant option, HostedPageFailureURL.

# GENERATE_POSTBACK

Generate a postback from RocketGate to Merchant’s pre-configured postback URL. This is applicable to Void/Credits, Rebills, and Xsell requests. By default Gateway transactions do not receive postbacks.

Set to TRUE, ON, or 1 to enable.

The types of postbacks generated are as follows:

- Gateway API transactions flagged as joins or trials will be sent to the [XsellPostbackURL](https://help.rocketgate.com/support/solutions/articles/28000015487-hosted-page-xsellpostbackurl).
- Any other type of gateway transaction will be sent to the [RebillPostbackURL](https://help.rocketgate.com/support/solutions/articles/28000015485-hosted-page-rebillpostbackurl).
- If GENERATE_POSTBACK is used with the [RocketGate Embedded Fields integration](https://help.rocketgate.com/support/solutions/articles/28000029290-ajax-embedded-fields-programmer-s-guide-v1-3-), a [hosted page postback](https://help.rocketgate.com/support/solutions/articles/28000006675-hostedpage-postback) will be sent.  

# GOOGLE_PAY_TOKEN

When the payment method is Google Pay, GOOGLE_PAY_TOKEN represents the token that the customer selected to pay with from their Google Pay account.

# IOVATION_BLACK_BOX

Iovation device fingerprinting black box data from ReputationShield client.  This element is optional.

# IOVATION_ENABLED

Turn on/off Iovation fraud service. This element is optional.  By default, Iovation is off.  The following table summarizes the valid values for this element.

| Value | Meaning                                         |
|-------|-------------------------------------------------|
| YES   | Iovation fraud analysis is to be performed.     |
| NO    | Iovation fraud analysis is not to be performed. |

# IOVATION_RULE

Iovation business rule ID used to obtain a recommendation.  This element is optional.

# IPADDRESS

Customer’s IP address.  This element is optional.

Supported IPv4 and IPv6. Maximum length for this field is 32 characters.

# MERCHANT_ACCOUNT

Specifies the merchant account for which the transaction is to be applied. The value for the MERCHANT_ACCOUNT parameter is the merchant account sequence ID. The merchant account sequence ID can be found in the Mission Control Merchant Accounts list.
                  
![](https://s3.amazonaws.com/cdn.freshdesk.com/data/helpdesk/attachments/production/28026893434/original/B4gDhnvdcbRnLqoesUEF0NPLvQ4S5azvVw.png?1744226184)

The MERCHANT_ACCOUNT element is optional.  If this element is omitted, the RocketGate network will assign the transaction to an appropriate account based upon the card type and a load balancing algorithm.

**IMPORTANT**: If an account is specified with the MERCHANT_ACCOUNT parameter and the transaction is declined, the decline will not be cascaded. To allow cascading, use the PREFERRED_MERCHANT_ACCOUNT parameter.

# MERCHANT_CASCADED_AUTH

Indicator to whether include the previous 3DS authentication data in the cascade transaction. Possible values are:

TRUE - Reuse the 3DS authentication data in the cascade transaction.
FALSE - Cascade the transaction as non-3DS. This is the default behavior if the parameter is not set.

This element is to be used in conjunction with the PAYMENT_LINK_TOKEN that returned from [BuildPaymentLink API](https://help.rocketgate.com/support/solutions/articles/28000024349-build-payment-link-api-simplified-3ds-flow-) (simplified 3DS flow).

**IMPORTANT**: Merchants should leverage the presence of 3D_ECI returned on the declined transaction to determine if the 3DS authentication was successful. If 3D_ECI not present or value equal to 00, or 07 (failed 3DS), then merchants should not attempt to use this MERCHANT_CASCADED_AUTH option

# MERCHANT_CUSTOMER_ID

Customer ID assigned to the customer within the merchant’s internal systems.  This argument is required for recurring billing and is required for credit card transactions that use the CARD_HASH element.  This element is optional in all other transactions is provided as a convenience to the merchant.

Maximum length for this field is 36 characters.

# MERCHANT_DESCRIPTOR

Merchant name to be displayed on cardholder’s statement.  The value provided overrides the default descriptor provided by the bank and/or processor.

Note:  This feature is not supported by all banks or processors.  Please check with RocketGate customer service before using it.

# MERCHANT_DESCRIPTOR_TRIAL

Merchant name to be displayed on cardholder’s statement on Trial Conversion (Visa Only for Visa Trial Mandate)

# MERCHANT_DESCRIPTOR_CITY

Merchant phone/city to be displayed on cardholder’s statement.  The value provided overrides the default phone/city provided by the bank and/or processor.

Note:  This feature is not supported by all banks or processors.  Please check with RocketGate customer service before using it.

Maximum length for this field is 13 characters.

# MERCHANT_INVOICE_ID

This argument carries an invoice or transaction ID assigned to the transaction within the merchant’s internal systems.  This argument is required for recurring billing and is used as an ID for rebill and cancel postbacks allowing you to differentiate between various subscriptions a customer may have. Otherwise, for non-recurring billing, it is optional and is provided as a convenience to the merchant. We recommend passing this value though as it can be very helpful in reconciling transactions.

The maximum length of this argument is 36 characters.

# MERCHANT_ID

Identification number assigned to the merchant within the RocketGate network.  This element is required in every transaction.

# MERCHANT_PASSWORD

Validation password assigned to the merchant within the RocketGate network.  This element is required in every transaction.

# MERCHANT_PRODUCT_ID

Optional identification value used to identify the product of a transaction within RocketGate reports. For example, many reports allow you to group sales by Product ID. This string value is optional and is provided as a convenience to the merchant.

The maximum length of this argument is 36 characters.

# MERCHANT_SITE_ID

Optional identification number used to identify the source of a transaction within RocketGate reports. For example, many reports allow you to group sales by Site ID. This integer value is optional and is provided as a convenience to the merchant.

If specified, the Site ID number must be an integer value from 0 to 50000.

# NETWORK_TOKENIZATION_DISABLED

This parameter is used to disable the Network Token usage on the transaction. By default, RocketGate processes all Gateway API transactions with the Network Token if the selected merchant account is activated with the Network Token feature. When set to TRUE, the transaction would be processed with PAN (Payment Account Number).

If the parameter is set to FALSE or absent, it will prioritize Network Token if available.

# OMIT_RECEIPT

This argument is used to disable the optional Email Receipts functionality. Email receipts must first be setup in Mission Control at which point all transactions receive receipts. When set to TRUE, ON, or 1, this will disable sending the receipt.

# PARTIAL_AUTH_FLAG

Specifies whether a partial authorization is acceptable for the Auth-only or Purchase transaction. When enabled, a transaction can be completed successfully for an amount that is less than the amount requested. When disabled, a transaction must be completed for the full requested amount to be successful. In either case, the actual settled amount is returned in the response in the SETTLED_AMOUNT parameter.

Flag values of TRUE, ON, YES, or 1 enable the use of partial authorizations. Any other value disables partial authorizations. By default, partial authorizations are disabled.

Note:  This feature is only currently supported by merchant accounts processed with EPX, Nashville, and TSYS. You may contact your acquirers to verify if additional configurations required.

# PAYINFO_TRANSACT_ID

One-way hash representing a customer’s credit card number, ACH, or debit account.

This parameter can be used in place of the CARD_HASH parameter on sale and authorization transactions.

When used, the PAYINFO_TRANSACT_ID should be populated with the transaction ID from a previous successful transaction. This transaction ID will be used to retrieve and populate the card data similar to the CARD_HASH parameter.

If the value provided for the PAYINFO_TRANSACT_ID is invalid, error 453 – INVALID_PAYINFO_TRANSACT_ID is returned.

Note that if the transaction contains a CARDNO value, ACCOUNT_NO value, CARD_HASH value, or PAY_HASH value, the PAYINFO_TRANSACT_ID parameter is ignored.

# PAYMENT_LINK_TOKEN

The session ID returned from the [Build Payment Link API](https://help.rocketgate.com/support/solutions/articles/28000024349-build-payment-link-api-simplified-3ds-flow-). The parameter is used when merchants perform cascade/re-attempt after the original transaction is performed, which RocketGate will automatially populate the key transaction data, such as PAN, CVV, etc. This parameter is optional.

This parameter can also be used with MERCHANT_CASCADED_AUTH if merchants desire to pass on the 3DS data to the cascade/re-attempt transaction.

# PREFERRED_MERCHANT_ACCOUNT

Set a preferred initial account for cascading. This is an optional parameter. The value for the PREFERRED_MERCHANT_ACCOUNT parameter is the merchant account sequence ID. The merchant account sequence ID can be found in the Mission Control Merchant Accounts list.

If an account is sent in this parameter, the account is moved to the front of the load-balancer list. The cascade first tries the preferred merchant account, if failed, then cascade to other accounts in the load balancer.

Note: The preferred account **must** be in the load balancer. If the account is not in the load balancer, RocketGate will use another randomly selected account from the load balance .

# PROCESSOR_3DS

This argument indicates that the 3DSecure authentication is performed by the processor's MPI. This is an optional parameter.

This parameter should be returned if the same param is returned in the [GatewayResponse](https://help.rocketgate.com/support/solutions/articles/28000018236-gatewayresponse) for a 3DS lookup.

# REBILL_AMOUNT

This argument is used to indicate that the transaction is a recurring charge.  The argument specifies the dollar value of the subsequent charges.  This value may be different than the initial amount specified in AMOUNT above.  This argument is optional.

# REBILL_COUNT

This argument is used to indicate that the number of times to rebill before automatically canceling. Rebill-count=0 is a membership that does not recur. This argument is optional.

# REBILL_END_DATE

This argument is used to indicate that the transaction is a recurring charge with a specific end date.  The argument specifies the date at which recurring billing is scheduled to terminate.  The date value must be specified in **_YYYY-MM-DD_** format.

This argument is optional.  If REBILL_END_DATE argument is omitted, rebilling will continue until it is canceled due to a customer request or billing failure, e.g. expired card.

When used with PeformRebillUpdate() or PeformRebillCancel(), setting REBILL_END_DATE=CLEAR indicates that a previous cancel request should be cleared.

When used with PerformRebillCancel(), setting REBILL_END_DATE=NOW will set the cancel date to the current time and effectively prevent any subsequent billing.

# REBILL_FREQUENCY

This argument is used to indicate that the transaction is a recurring charge.  The argument specifies the frequency of the recurring charges.  The following table summarizes the valid values for this argument. This argument is optional. A numeric value specifies the frequency of rebilling in DAYS (For example rebill-freq=7 would specify a subscription that would renew every 7 days).

| Value         | Meaning                                                        |
|---------------|----------------------------------------------------------------|
| MONTHLY       | Rebilling occurs once per month                                |
| QUARTERLY     | Rebilling occurs once per quarter, i.e. 3 months               |
| SEMI-ANNUALLY | Rebilling occurs semi-annually, i.e. 6 months                  |
| ANNUALLY      | Rebilling occurs annually                                      |
| LIFE          | Lifetime Membership                                            |
| Numeric Value | Rebilling occurs once per # of days specified. Max value 1,095 |
# REBILL_REACTIVATE

Used with the PerformRebillUpdate function. When set to TRUE, it reactivates the last cancelled subscription identified by the given customer ID (MERCHANT_CUSTOMER_ID) and invoice ID (MERCHANT_INVOICE_ID). Thus, the customer ID and Invoice ID are compulsory parameters.

In the case of more than one subscription being identified by the customer ID and invoice ID, while there is already an active rebill, then no reactivation would occur and the request would fail.

Unless the REBILL_START is specified, the next rebill will be set to AUTO on the successful request.

# REBILL_RESCHEDULE

Used in [BuildPaymentLink API (Simplified 3DS flow)](https://help.rocketgate.com/support/solutions/articles/28000024349-simplified-3ds-flow-buildpaymentlink-api). When set to TRUE, it allows merchants to perform an upgrade on a subscription identified by the MERCHANT_INVOICE_ID parameter. It also revives the subscription if the subscription has been set to cancel or cancelled, the cancellation date will be cleared automatically.

Also, for subscriptions that have been previously suspended, it will resume the subscriptions (similar to REBILL_RESUME).

# REBILL_RESUME

Used with the PerformRebillUpdate function. When set to TRUE, it causes the rebilling to resume for the subscription identified by the MERCHANT_INVOICE_ID parameter.

# REBILL_START

If the transaction is a recurring charge, this argument specifies the number of minutes or days after the initial transaction before rebilling begins.  This argument can be used to control trial periods, e.g. begin rebilling after an initial three day period.

The REBILL_START parameter determines when the next rebill operation will be performed. Following is a list of valid values and behavior.

| Value         | Meaning                                                                                                                                                                                                                                   |
|---------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| AUTO          | Causes rebilling to be scheduled as an offset from the current day. For example, if a membership is scheduled for monthly billing, the next rebill will occur one month from the current day, i.e. the day the REBILL_RESUME is executed. |
| TODAY or NOW  | Causes the next rebilling to be executed immediately.                                                                                                                                                                                     |
| Numeric value | Causes rebilling to be scheduled "X" number of days from the current day. For example, if the value 5 is provided, the next rebill will occur 5 days from the current day.                                                                |

If the REBILL_START parameter is omitted, the rescheduling defaults to AUTO.

This argument is optional.  If this argument is omitted, the value provided for REBILL_FREQUENCY determines the starting date for rebilling. If set, the argument must be greater than 0 and less than 550. It sets a value between 1 day and 18 months. Additionally, we also support 10 and 30 minute trials by passing a value of either PT10M or PT30M.

If the transaction is an update to an existing recurring charge and the desire is to automatically increment the REBILL_DATE to today + the configured/requested REBILL_FREQUENCY, this argument can be set to “AUTO”. See Full/Instant Upgrade in Appendix A for an example.

When updating an existing subscription via PerformRebillUpdate(),  the REBILL_START value can also be set to a timestamp in YYYY-MM-DD HH:MM:SS format.

# REBILL_SUSPEND

Used with the PerformRebillUpdate function. When set to TRUE, it causes the subscription identified by the MERCHANT_INVOICE_ID parameter to be suspended. When a subscription is suspended, no rebilling is performed.

# REFERENCE_GUID

This is the transaction identifier of the request for 3DSecure lookup. This argument is required when performing a payment after a 3DS lookup.

**IMPORTANT**: Aside from this param, merchants should also include all other payment info (card hash, currency, amount, etc.) in the payment request. If only reference GUID is provided, RocketGate cannot perform a cascade when the transaction is declined.

# REFERENCE_SCHEME_SETTLEMENT_DATE

This optional argument is for card or credentials on file (CoF) transactions.  The settlement date is complementary information related to the reference card scheme transaction ID and is ignored if used without it. Please refer to REFERENCE_SCHEME_TRANSACTION_ID for details.

This is the settlement date of the original transaction used to store credit card credentials and currently applies only to transactions using MasterCard or Maestro.

The format is 4 digits (2 digits for the month and 2 digits for the year).  Ex: A settlement date of November 2020 would be: "1120".

# REFERENCE_SCHEME_TRANSACTION_ID

This optional argument is for merchant initiated transactions (MIT) using card or credentials on file (CoF).  It specifies the card scheme transaction ID of the transaction that was originally used to store the card information on file.

If this argument is not specified for a CoF transaction, Rocketgate searches for an initial transaction reference in its database history.

This argument is used for subsequent CoF transactions that had their initial transaction on another gateway than RocketGate.

Please refer to the appropriate credit card scheme for the exact format of the scheme transaction ID.  The maximum length accepted by RocketGate is 32 characters.

Note: The transaction settlement date (currently only for MasterCard and Maestro) has to be specified in REFERENCE_SCHEME_SETTLEMENT_DATE

# USE_PRIMARY_SCHEMEID

Optional. A boolean (TRUE or FALSE) that indicates whether this transaction request will use the SchemeID of the primary transaction.

Notes on usage of USE_PRIMARY_SCHEMEID:

- The boolean USE_PRIMARY_SCHEMEID can be used as an alternative for [XsellUsePrimarySchemeID](https://help.rocketgate.com/support/solutions/articles/28000024998-gateway-service-xselluseprimaryschemeid), which requires the use of the GenerateXsell.
- If USE_PRIMARY_SCHEMEID is set to TRUE, the parameters REFERENCE_SCHEME_TRANSACTION_ID and REFERENCE_SCHEME_SETTLEMENT_DATE should be provided in the request.
  - REFERENCE_SCHEME_TRANSACTION_ID and REFERENCE_SCHEME_SETTLEMENT_DATE can be retrieved by using the [PerformLookup](https://help.rocketgate.com/support/solutions/articles/28000018238-gatewayservice) method of the [GatewayService API](https://help.rocketgate.com/support/solutions/articles/28000018238-gatewayservice).
- The result of setting USE_PRIMARY_SCHEMEID and providing REFERENCE_SCHEME_TRANSACTION_ID and REFERENCE_SCHEME_SETTLEMENT_DATE will be the same as using the [XsellUsePrimarySchemeID](https://help.rocketgate.com/support/solutions/articles/28000024998-gateway-service-xselluseprimaryschemeid) option and the GenerateXsell method.
  - That is, RocketGate would submit the Xsell initial transaction as a rebill and replace the SchemeID of the Xsell initial transaction so that all subsequent transactions would use the same SchemeID. Note: It only applies to transactions with billing type of Initial or Trial.

# REFERRER_URL

Optional. The URL of the merchant's website that accepted the payment transaction.

In certain scenarios, processors and fraud verification service providers downstream from RocketGate may request the URL of the website that accepted the payment transaction. Integrators may use this optional field to provide the site URL.

# SHOW_PAYMENT_FORM

Indicator whether to present the RocketGate payment page to the cardholder. This element is conditional, it must be included if merchants do not pass a PAN or Card Hash in the request.

If a PAN or CardHash is passed along while this parameter is TRUE, RocketGate will present the payment page to the cardholder with the prefilled card data. Cardholders would be able to edit or change their card info.

When this parameter The default value is FALSE.

This element is to be used in conjunction with the [BuildPaymentLink API](https://help.rocketgate.com/support/solutions/articles/28000024349-build-payment-link-api-simplified-3ds-flow-) (simplified 3DS flow).

# ROUTING_NO

Customer’s checking/savings routing number used in ACH transactions.

# SAVINGS_ACCOUNT

Boolean value that indicates if this account is a Checking or Savings account. Default value is FALSE.

| Value | Meaning                       |
|-------|-------------------------------|
| FALSE | Account is a Checking Account |
| TRUE  | Account is a Savings Account  |

# SCRUB

Turn on/off fraud all scrubbing (Activity, NegDB, and Profile) in a single setting.

SCRUB element takes precedence over SCRUB_ACTIVITY, SCRUB_NEGDB, and SCRUB_PROFILE. If the SCRUB element is present in the gateway request, it controls all three scrubs.

By default, fraud scrubbing is not performed.  The following table summarizes the valid values for this element.

| Value  | Meaning                                                                                        |
|--------|------------------------------------------------------------------------------------------------|
| YES    | Fraud scrubbing is to be performed.                                                            |
| NO     | Fraud scrubbing is not to be performed.                                                        |
| IGNORE | Perform fraud scrubbing and return the result code, but do not take action based upon results. |

# SCRUB_ACTIVITY

Turn on/off Activity fraud scrubbing.  Valid values are TRUE, FALSE, IGNORE

# SCRUB_NEGDB

Turn on/off Negative Database fraud scrubbing.  Valid values are TRUE, FALSE, IGNORE

# SCRUB_PROFILE

Turn on/off  Suspicious Profile fraud scrubbing.  Valid values are TRUE, FALSE, IGNORE

# SS_NUMBER

Customer’s last 4 digits of Social Security number used in ACH transactions.

The CPF (Brazillian Tax ID) of the cardholder, is required by the processor ePag/Letpay. It can also be used with the SolidGate and Unlimint processor if merchants are using their Brazilian processing.

# STYLE_SHEET

The style.css files that to be applied on RocketGate's HostedPage. This element is optional.

This element is to be used in conjunction with the [BuildPaymentLink API](https://help.rocketgate.com/support/solutions/articles/28000024349-build-payment-link-api-simplified-3ds-flow-) (simplified 3DS flow). This value will override the default RocketGate's CSS or pre-configured merchant option, [HostedPageCSS](https://help.rocketgate.com/support/solutions/articles/28000016240-hosted-page-hostedpagecss).

# STYLE_SHEET2

The style.css files that to be applied on RocketGate's HostedPage. This element is optional.

This element is to be used in conjunction with the [BuildPaymentLink API](https://help.rocketgate.com/support/solutions/articles/28000024349-build-payment-link-api-simplified-3ds-flow-) (simplified 3DS flow). This value will override the default RocketGate's CSS or pre-configured merchant option, [HostedPageCSS](https://help.rocketgate.com/support/solutions/articles/28000016240-hosted-page-hostedpagecss).

# STYLE_SHEET3

The style.css files that to be applied on RocketGate's HostedPage. This element is optional.

This element is to be used in conjunction with the [BuildPaymentLink API](https://help.rocketgate.com/support/solutions/articles/28000024349-build-payment-link-api-simplified-3ds-flow-) (simplified 3DS flow). This value will override the default RocketGate's CSS or pre-configured merchant option, [HostedPageCSS](https://help.rocketgate.com/support/solutions/articles/28000016240-hosted-page-hostedpagecss).

# SUB_MERCHANT_ID

Sub Merchant ID. This is only submitted by registered Payment Facilitators.

# SUCCESS_POSTBACK_URL

The URL to return the postback when the payment authorization is approved. This element is optional.

This element is to be used in conjunction with the [BuildPaymentLink API](https://help.rocketgate.com/support/solutions/articles/28000024349-build-payment-link-api-simplified-3ds-flow-) (simplified 3DS flow).

This value will override the pre-configured merchant option, [HostedPagePostbackURL](https://help.rocketgate.com/support/solutions/articles/28000015482-hosted-page-hostedpagepostbackurl).

# SUCCESS_URL

Redirect URL when the payment authorization is successful. This element is optional.

This element is to be used in conjunction with the [BuildPaymentLink API](https://help.rocketgate.com/support/solutions/articles/28000024349-build-payment-link-api-simplified-3ds-flow-) (simplified 3DS flow).

This value will override the pre-configured merchant option, HostedPageSuccessURL.

# TRANSACT_ID

Unique identification number of a transaction number to be voided, credit, or ticketed.  This element is required for void and ticket transactions.  This element is optional for credit transactions.

# TRANSACTION_TYPE

The transaction type or [GatewayService](https://help.rocketgate.com/support/solutions/articles/28000018238-gatewayservice) to be initiated. The following table lists the acceptable values for this argument:

| Value          | Meaning                                   |
|----------------|-------------------------------------------|
| CC_AUTH        | PerformAuthOnly                           |
| CC_TICKET      | PerformTicket                             |
| CC_PURCHASE ** | PerformPurchase (includes an ACH Payment) |
| CC_CONFIRM     | PerformConfirmation                       |
| CC_CREDIT      | PerformCredit                             |
| CC_VOID        | PerformVoid                               |
| CARDUPLOAD     | PerformCardUpload                         |
| CARDSCRUB      | PerformCardScrub                          |
| GENERATEXSELL  | GenerateXsell                             |
| LOOKUP         | PerformLookup                             |
| REBILL_CANCEL  | PerformRebillCancel                       |
| REBILL_UPDATE  | PerformRebillUpdate                       |

** The CC_PURCHASE may be overridden if the option, [IovationAuthOnly](https://help.rocketgate.com/support/solutions/articles/28000003892--fraud-options-iovationauthonly), or [WhiteListAuthOnly](https://help.rocketgate.com/support/solutions/articles/28000016266-fraud-options-whitelistauthonly) is used.

# THREATMETRIX_SESSION_ID

Session ID from ThreatMetrix Device Fingerprinting tool.

# TRANSLATIONS

JSON object contains translations to:

- Override the HostedPage static labels
- Populate the custom labels configured in HostedPage Vars options ([Masthead, Sidebar, SidebarRight, FormWrapperPre, FormWrapperPost, Footer](https://help.rocketgate.com/support/solutions/articles/28000016122-hosted-page-vars-customizing-your-hosted-purchase-page))

This element is optional. It is to be used with the [BuildPaymentLink API](https://help.rocketgate.com/support/solutions/articles/28000024349-build-payment-link-api-simplified-3ds-flow-) (simplified 3DS flow).

Learn more about how to use this parameter at [Hosted Page - Customize display texts for specific languages](https://help.rocketgate.com/support/solutions/articles/28000026188-hosted-page-customize-display-texts-for-specific-languages)

# UDF01

User data field 1.  This element is optional and is provided as a convenience to the merchant.

Maximum length for this field is 36 characters.

# UDF02

User data field 2.  This element is optional and is provided as a convenience to the merchant.

Maximum length for this field is 1024 characters.

# USERNAME

Customer’s username within the merchant’s internal system.  This element is optional.

# XSELL_CUSTOMER_ID

Destination/Partner customer ID. If omitted, MERCHANT_CUSTOMER_ID is used.  This element is optional and only used in the **_GenerateXsell_** function.

# XSELL_MERCHANT_ID

Destination/Partner merchant ID. If omitted, MERCHANT_ID is used. This element is optional and only used in the **_GenerateXsell_** function.

# XSELL_REFERENCE_XACT

Transaction ID associated with MERCHANT_CUSTOMER_ID.  This element is optional and only used in the **_GenerateXsell_** function.
