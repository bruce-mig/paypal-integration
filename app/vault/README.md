# Set up vault

###  Run vault container
`docker compose up`

### connect to vault container
`docker exec -it paypal_integration-vault-1 /bin/sh`
### get vault status, initially vault is at sealed status(Sealed true)
`vault status`

### initialize vault cluster
### it will generates 5 master key shares and root token which can use to authenticate http client
`vault operator init`


### unseal vault server, we need to give 3 key shares unseal the vault
### execute vault operator unseal command three times with three different key shares
`vault operator unseal`

# login to vault with root token
`vault login`

# enable kv secret engine
`vault secrets enable kv`