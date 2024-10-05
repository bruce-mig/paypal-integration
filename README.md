## PayPal Integration
This project demonstrates the use of  PayPal payment SDK with Java SpringBoot and ThymeLeaf.

## How to run project
1. Clone project with `git clone https://github.com/bruce-mig/paypal-integration.git`
2. Create or Login to your PayPal Developer account at https://developer.paypal.com/dashboard/.
   - Enable Sandbox mode.
   - Navigate to App & Credentials and create a new app.
   - Save the app ***Client ID*** and ***Client Secret*** in Hashicorp Vault.
3. In project root directory, start Docker and run `docker compose up`,
4. Initialise and unseal Hashicorp Vault and login with the token.
5. Vault is available at `localhost:8200`
6. Export the Vault token before running the project.
7. The web application is available in the browser at `localhost:8080`.

[//]: # (Home Page)

[//]: # (![home page]&#40;/app/images/index.png&#41;)

### Payment Page
![payment page](/app/images/payment.png)

### Success Page
![success page](/app/images/success.png)



