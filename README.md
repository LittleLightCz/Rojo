# Rojo
Rojo is a Java library for mapping the regular expression into a POJO objects and more! The regexp groups are automatically converted to the POJO's field types. The currently supported types are:

- String
- Integer / int
- Short / short
- Long / long
- Float / float
- Double / double
- BigInteger, BigDecimal
- Date

## How to use Rojo
First let's define our SimpleBean to show some simple regexp matching. It's gonna have field "name", which will be String, and "count", which will be int. It will look like this:

```java
@Regex("(\\w+):(\\d+)")
public class SimpleBean {

    @Group(1)
    private String name;
    @Group(2)
    private int count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
```

Now this is how we try to find the first match in the string "a:1,b:2,c:3":
```java
SimpleBean bean = Rojo.of(SimpleBean.class)
                    .match("a:1,b:2,c:3").get();
```

Note that **match()** method returns an Optional, so if there was no match found, the Optional will be empty. To get a List of results, you will use:
```java
List<SimpleBean> result = Rojo.of(SimpleBean.class)
                            .matchList("a:1,b:2,c:3");
```

Similarly you can use **matchIterator()** or **matchStream()**, to get the full power of the Java 8 Stream API.

What's more, you don't need to match always for a POJO bean, but Rojo also enables you to do the "plain matching" in much more convenient way, than if you've just used the Java's Pattern.compile() manually. Let's look at it, for a single find you can use:
```java
Optional<String> item = Rojo.find("[a-z]:2", "a:1,b:2,c:3");
``` 
for a List of results:
```java
List<String> list = Rojo.asList("[a-z]", "a:1,b:2,c:3");
```
for a Map of results, where Rojo expects you to specify **exactly 2 groups** in your regexp pattern:
```java
Map<String, String> map = Rojo.asMap("([a-z]):(\\d)", "a:1,b:2,c:3");
```
or if you want to replace all letters to upper-case:
```java
String replaced = Rojo.replace("[a-z]", "a:1,b:2,c:3", String::toUpperCase);
```
Note that all these methods have their "matcher" overloads in case you would like to use them directly.

## Annotations overview
Class annotations:
- @Regex - here's where you specify you'r regexp pattern as String
- @Flags - if you want to use flags such as Pattern.DOTALL etc.

Field annotations:
- @Group - group number that corresponds to the field
- @DateFormat - this annotations is mandatory only for the Date type fields, where you have to specify the date format

## Final words
If you have any ideas how to enhance this library, you're welcome to do so, because it would be awesome, if we could make this an ultimate Java library for a regular expression parsing.