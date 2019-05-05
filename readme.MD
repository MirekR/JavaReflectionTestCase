Simple testcase for showcasing problem with immutability in Java.

Observe FinalObject declaration where object itself is final and it's member field "name" is final as well.
Object is constructed via static builder and has only private constructor.

In the test object is created with one name and assertion is done against this value. In the next step name field is re-assigned
via reflection. Test following showing that the value of the field name has changed although it's defined as final -> hence Java can't enforce proper immutability.

Run the example:

```
mvn clean install
```