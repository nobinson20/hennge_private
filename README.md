# hennge_private
Private repo for Hennge global internship coding challenge

### Mission 1: Write a Go program
It is compilable, fulfills all rules written in the direction. To make it run without any 'for' loop, I chosed to use recursion. I understand that it would be a bit hard to see what's input and what's output in the terminal after the program is terminated, but since it's to be auto-tested I didn't add any line or words to hinder testing.


### Mission 2: Publish code as a secret gist
Learned Gist is a bit different thing from Git repo.

### Mission 3: Send URL of the gist's link
I used a tool named 'Httpie'

http POST https://api.challenge.hennge.com/challenges/003 github_url=https://gist.github.com/nobinson20/cd21da104fed4a4a79f14453ff21a98b contact_email=nobinson20@gmail.com userid:nobinson20@gmail.com password:john@example.orgTOKEN


http -v POST https://api.challenge.hennge.com/challenges/003 Authorization:Basic%20 github_url=https://gist.github.com/nobinson20/cd21da104fed4a4a79f14453ff21a98b contact_email=nobinson20@gmail.com userid:nobinson20@gmail.com password:john@example.orgTOKEN

http -v POST https://api.challenge.hennge.com/challenges/003 Authorization:Basic%20bm9iaW5zb24yMEBnbWFpbC5jb206MTI1NTk2MzY2NA== github_url=https://gist.github.com/nobinson20/cd21da104fed4a4a79f14453ff21a98b contact_email=nobinson20@gmail.com


blahblahblah 는
ninja@example.com:1773133250
형태

userid:password


http -v POST https://api.challenge.hennge.com/challenges/003 "Authorization: Basic bm9iaW5zb24yMEBnbWFpbC5jb206MDM3MDcxNzMwOA==" contact_email=nobinson20@gmail.com github_url=https://gist.github.com/nobinson20/cd21da104fed4a4a79f14453ff21a98b



# Problem Solved!

I made http request with Java, whose source code is located at 'untitled1' in this repository.

In fact, the error occured due to a silly typo in the direction, which is:

String seed64 = "6e6f62696e736f6e3230406e617665722e636f6d4844454348414c4c454e4745303033"; //[usrID]HDECHALLENGE003

not

String seed64 = "6e6f62696e736f6e323040676d61696c2e636f6d48454e4e47454348414c454e4745303033"; //[usrID]HENNGECHALLENGE003



# Fin


### Tried to check if copy->httpie requeset was the problem, but it failed possibly because I already sent it.
#### So many possible scenarios, such as IP detected, some parameters from request are matched data saved in the server.

Just attempted the way I used to do.
1) Copy stdout value from JAVA project written in intellij
2) Copy to blank after "Authorization: Basic "
3) Run

http -v POST https://api.challenge.hennge.com/challenges/003 "Authorization: Basic bm9iaW5zb24yMEBnbWFpbC5jb206MTMyNTg4OTI4NA==" contact_email=nobinson20@gmail.com github_url=https://gist.github.com/nobinson20/cd21da104fed4a4a79f14453ff21a98b


#### *****Result******
POST /challenges/003 HTTP/1.1
Accept: application/json, */*
Accept-Encoding: gzip, deflate
Authorization: Basic bm9iaW5zb24yMEBnbWFpbC5jb206MTMyNTg4OTI4NA==
Connection: keep-alive
Content-Length: 126
Content-Type: application/json
Host: api.challenge.hennge.com
User-Agent: HTTPie/2.0.0

{
    "contact_email": "nobinson20@gmail.com",
    "github_url": "https://gist.github.com/nobinson20/cd21da104fed4a4a79f14453ff21a98b"
}

HTTP/1.1 400 Bad Request
Access-Control-Allow-Origin: https://challenge.hennge.com
Connection: keep-alive
Content-Length: 162
Content-Type: application/json
Date: Fri, 20 Mar 2020 11:49:32 GMT
Via: 1.1 e175847e898eb176a9bfc215ff971192.cloudfront.net (CloudFront)
X-Amz-Cf-Id: 20z8hU_qKaznZgzl0Ep0Lto9lipqwRyl0WWrKslBLzMWOsYw77l1iQ==
X-Amz-Cf-Pop: ICN54
X-Amzn-Trace-Id: Root=1-5e74adcc-e8dce08ce5f4d04f63eb4651;Sampled=0
X-Cache: Error from cloudfront
x-amz-apigw-id: JsAX6F2xNjMFgNA=
x-amzn-RequestId: aff7ce18-4d1c-460f-a6cb-4cb96cf4ad2b

{
    "message": "Could not save your solution. It is possible that the applicant does not exist or their solution has been already provided. Please check your input."
}


### Another attempt (changing email address)

#### Run with (nobinson20@naver.com not gmail.com)
http -v POST https://api.challenge.hennge.com/challenges/003 "Authorization: Basic bm9iaW5zb24yMEBuYXZlci5jb206MTgzNzY5NTc3Mg==" contact_email=nobinson20@naver.com github_url=https://gist.github.com/nobinson20/cd21da104fed4a4a79f14453ff21a98b


POST /challenges/003 HTTP/1.1
Accept: application/json, */*
Accept-Encoding: gzip, deflate
Authorization: Basic bm9iaW5zb24yMEBuYXZlci5jb206MTgzNzY5NTc3Mg==
Connection: keep-alive
Content-Length: 126
Content-Type: application/json
Host: api.challenge.hennge.com
User-Agent: HTTPie/2.0.0

{
    "contact_email": "nobinson20@naver.com",
    "github_url": "https://gist.github.com/nobinson20/cd21da104fed4a4a79f14453ff21a98b"
}

HTTP/1.1 500 Internal Server Error
Access-Control-Allow-Origin: https://challenge.hennge.com
Connection: keep-alive
Content-Length: 58
Content-Type: application/json
Date: Fri, 20 Mar 2020 12:01:06 GMT
Via: 1.1 4a9dd8e8caa55ec5ba68e9ec50bfb721.cloudfront.net (CloudFront)
X-Amz-Cf-Id: mqblza3kuAcVwRgQ-u0N5l4hnJVqNAvnSWNQqtgqc07nvJOfV9TaAw==
X-Amz-Cf-Pop: ICN54
X-Amzn-Trace-Id: Root=1-5e74b081-ccd2411a76b97ab0ab58f992;Sampled=0
X-Cache: Error from cloudfront
x-amz-apigw-id: JsCEUH6otjMFUdg=
x-amzn-RequestId: 86e8b722-d513-4c1e-9a82-ed5aef62624e

{
    "message": "An internal error happened, please try again"
}










