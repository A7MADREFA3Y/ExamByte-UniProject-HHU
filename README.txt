Step by step how to code works

the code start with the localhost 8080 with the annotation localhost:8080/
you will be directed to a landing page, with exam byte welcome page, then by
pressing login you will be redirected to a GitHub login page. after that you will
be redirected according to your role to a one of the dashBoards ( Admin, user, Korrektor )
throw a methode.

how can I change the role form admin to user ?

if you go to the webSecurityConfig package then to AppUserService.
uncomment the line 34 and then comment the lines 40 until 50.
this will give anyone who log in the user, and for korrektor role uncomment the
line 37.

---------------------------- PLEASE NOTE THAT ---------------------------------
1) if the applications is not working check first the client id/secret form GitHub first
2) if you change the port form 8080 to 1234 for example the code won't run with the
login page, alternatively try ( http://localhost:1234/login ) and after that everything
will run smooth as butter

if you have any further questions please contact me. thank you