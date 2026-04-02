# IPG(Internet Payment Gateway) service
Service related to payment management

base url: http://localhost:8082/api/v1/payment

1-get account information

    end point : /get-account-info/{cardId}

    path variables :
        card_id ->
            type : Long

    query params : null

    data : null
    
    headers : null

    example:
        request :
            curl -X GET http://localhost:8082/api/v1/payment/get-account-info/100000036
        response : 
            {
                "account":{
                    "cardId":100000036,
                    "ownerNationalId":3243214505,
                    "balance":20,
                    "status":"OPEN",
                    "createdAt":"2026-03-29T20:52:02.151+00:00"
                },
                "metaData":{
                    "requestId":"ab710b8a-511f-459e-a2d1-a6042c5d11d8",
                    "status":{
                        "statusCode":200,
                        "message":"Account found"
                    }
                }
            }

2-get account transactions

    end point : /get-account-transactions/{cardId}

    path variables :
        card_id ->
            type : Long

    query params : null

    data : null
    
    headers : null

    example:
        request :
            curl -X GET http://localhost:8082/api/v1/payment/get-account-transactions/100000037
        response :
            {
                "transaction": [
                {
                    "transactionId": 30,
                    "cardId": 100000039,
                    "fee": 20,
                    "status": "FAILED",
                    "transactionTime": "2026-03-31T13:12:00.300+00:00"
                },
                {
                "transactionId": 16,
                "cardId": 100000039,
                "fee": 20,
                "status": "PASSED",
                "transactionTime": "2026-03-31T13:02:10.744+00:00"
                },
                {
                "transactionId": 18,
                "cardId": 100000039,
                "fee": 20,
                "status": "FAILED",
                "transactionTime": "2026-03-31T13:02:13.573+00:00"
                },
                {
                "transactionId": 17,
                "cardId": 100000039,
                "fee": 20,
                "status": "PASSED",
                "transactionTime": "2026-03-31T13:02:13.131+00:00"
                }
                ],
                "metaData": {
                    "requestId": "480add9b-58c9-4baf-9811-8f3904e91945",
                    "status": {
                        "statusCode": 200,
                        "message": "Account found"
                    }
                }
            }

3-post account

    end point : /post-account

    path variables : null

    query params : null

    data :
        {
            nationalId ->
                type : Long
        }
    
    headers : 
        Content-Type : application/json

    example:
        request :
            curl -X POST http://localhost:8082/api/v1/payment/post-account 
            -d 
                '{
                    "nationalId" : 1000000039
                }' 
            -H 
                "Content-Type: application/json"
        response : 
            {
                "account":{
                    "cardId":100000040,
                    "ownerNationalId":1000000039,
                    "balance":0,
                    "status":"OPEN",
                    "createdAt":"2026-04-02T23:01:51.843+00:00"
                },
                "metaData":{
                    "requestId":"4847cbe3-b5e0-4ebc-9d87-12e22760ddc5",
                    "status":{
                        "statusCode":201,
                        "message":"Account created"
                    }
                }
            }

4-purchase

    end point : /purchase

    path variables : null

    query params : null

    data :
        {
            cardId ->
                type : Long
            fee ->
                type : Long
        }
    
    headers : 
        Content-Type : application/json

    example:
        request :
            curl -X POST http://localhost:8082/api/v1/payment/purchase 
            -d 
                '{
                "   cardId" : 100000039, 
                    "fee" : 20
                }' 
            -H 
                "Content-Type: application/json" 

        response : 
            {
                "transaction":{
                    "transactionId":43,
                    "cardId":100000039,
                    "fee":20,
                    "status":"PASSED",
                    "transactionTime":"2026-04-02T23:13:06.322+00:00"
                    },
                "metaData":{
                    "requestId":"b2c1788f-cd6d-4d4c-93c4-36a95b298358",
                    "status":{
                        "statusCode":200,
                        "message":"Successful transaction"
                    }
                }
            }

5-roll back purchase

    end point : /rollback-purchase

    path variables : null

    query params : null

    data :
        {
            transactionId ->
                type : Long
        }
    
    headers : 
        Content-Type : application/json

    example:
        request :
            curl -X POST http://localhost:8082/api/v1/payment/rollback-purchase 
            -d 
                '{
                    "transactionId" : 43
                }' 
            -H 
                "Content-Type: application/json"

        response : 
            {
                "metaData":{
                    "requestId":"d75c58f6-f698-4d5c-9387-31e655a76721",
                    "status":{
                        "statusCode":200,
                        "message":"Reverting money is done"
                    }
                }
            }

6-set account status

    end point : /set-account-status

    path variables : null

    query params : null

    data :
        {
            cardId ->
                type : Long
            accountStatus ->
                type : String(input should be only 'OPEN' or 'CLOSE')
        }
    
    headers : 
        Content-Type : application/json

    example:
        request :
            curl -X POST http://localhost:8082/api/v1/payment/set-account-status 
            -d 
                '{
                    "cardId" : 100000039, 
                    "accountStatus" : "CLOSE"
                }' 
            -H 
                "Content-Type: application/json"

        response : 
            {
                "metaData":{
                    "requestId":"42d1aefd-5cf6-4395-8d8e-dcfccb4090d6",
                    "status":{
                        "statusCode":204,
                        "message":"Account is successfully closed"
                    }
                }
            }