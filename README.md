# MobileApplicationsConsultantTest
V1.0 to get book data and use Gson to parse them.
V1.1 Because I check it contains a new column Author to the list at about 05/25/2018 8PM. So I add Author property and related function to class Book to implement.Also, because this json file is about 428K and reading from InputStreamReader using string is quite slowly, I use StringBuilder to make it faster(about 1.5 min per time).
V1.2 I improve the performance from 1.5 min to about 10 seconds. This is because I used String to join the result when loop to get the result. Now this has changed to StringBuilder. 
        Also I add the process bar to system to show the process of parse
