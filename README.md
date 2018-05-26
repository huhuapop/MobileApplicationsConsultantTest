# MobileApplicationsConsultantTest
V1.0 to get book data and use Gson to parse them.
V1.1 Because I check it contains a new column Author to the list at about 05/25/2018 8PM. So I add Author property and related function to class Book to implement.Also, because this json file is about 428K and reading from InputStreamReader using string is quite slowly, I use StringBuilder to make it faster(about 1.5 min per time).
