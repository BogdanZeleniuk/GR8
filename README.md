### Run all tests using Maven command:
mvn test -Denv=qa

### Test scenarios which could be added into *`Search Brewery`* Feature:
```
1. Check result if query search param for autocomplete is empty string
2. Check result when query search param for autocomplete accepts spaces only
3. Check result when query search param for autocomplete accepts city names with underscores
4. Check result when query search param for search brewery accepts city names with underscores

```

### Test automation approaches for *`List Breweries`* Feature:
```
To cover 'List Breweries' feature with automated API tests I would extend
current OpenBreweryDbService with another endpoint /breweries and different query 
parameters. Basically there should be only one main method which would accept
the Map<String, String> params for a GET call. 
Inside 'org/gr8/model/enums' folder I would create one more enum
with the parameter names (for instance, by_city, by_country, by_ids etc). 
The existing BreweryType enum I would extend with the values, which are
described in the 'by_type' paramether and use @DataProvider for covering
all test scenarios with only one test method.

The testing techniques which I would use to create testing scenarios
and cover them with API tests would be: 
- Equivalent partitionong
- Error guessing
- Pairwise testing
It means that, first of all I would create couple of POSITIVE test scenarios
for each query param, and then create couple of NEGATIVE test scenarios with 
invalid query param values. Error quessing could be used to check NULL, EMPTY,
DIGITS in a sctring scenarios etc. 
For example, here are few possible test scenarios:
1. Validate the breweries contain contry/city/id/name... only those which 
are passed in the query params
2. Validate the number of breweries is equals to the one passed in param 
For 'sort' I would definitely use Pairwise testing since it can accept
one or more field. Using this testing technique we could minimize test
scenarios, count on the one hand, and maximize test couverage, on the other
hand. Obviously it is needed to verify ASC and DESC ordering for each 
test scenario using Pairwise testing. 
```