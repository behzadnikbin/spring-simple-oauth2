# spring-simple-oauth2
A simple example of how to setup oauth2 in spring boot


Make jarfile:
----
```bash
mvn -DskipTests package
```

Run:
----
```bash
java -jar oauth2example-0.0.1-SNAPSHOT.jar
```

Examples:
====

Successful Login Request:
----
```bash
curl --request POST \
  --url http://localhost:8080/oauth/token \
  --header 'Authorization: Basic d2ViLWNsaWVudDp3ZWItc2VjcmV0' \
  --header 'content-type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW' \
  --form grant_type=password \
  --form username=admin \
  --form password=admin \
  --form client_id=web-client \
  --form scope=read
```

Successful Login Response:
----
```bash
{
  "access_token": "6a2c2546-6a91-4184-8bcb-c073ddee906e",
  "token_type": "bearer",
  "refresh_token": "2c543665-20ef-4cac-a17c-848661dcf3c6",
  "expires_in": 31535559,
  "scope": "read"
}
```
 
Unsuccessful Login Request:
----
```bash
curl --request POST \
  --url http://localhost:8080/oauth/token \
  --header 'Authorization: Basic d2ViLWNsaWVudDp3ZWItc2VjcmV0' \
  --header 'content-type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW' \
  --form grant_type=password \
  --form username=admin \
  --form password=invalid \
  --form client_id=web-client \
  --form scope=read
```

Unsuccessful Login Response:
----
```bash
{
  "error": "invalid_grant",
  "error_description": "Bad credentials"
}
```
 