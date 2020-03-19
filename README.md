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









PYTHON으로 
