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

## Maven dependency
```xml
<dependency>
    <groupId>com.svetylkovo</groupId>
    <artifactId>rojo</artifactId>
    <version>1.0.1</version>
</dependency>
```
## How to use Rojo
### POJO matching
Let's have an input string which will be used to demonstrate the Rojo's features. We will do some regexp matching in a short (completely fictional) document which describes the results of a fruit picking session:
```java
String input = "John picked 7 apples on 2/6/2016.\n" +
               "Peter picked only 2 pears on 13/6/2016.\n" +
               "Jane collected 5 bananas on 5/7/2016.";
```

Now we'll define our POJO class:

```java
@Regex("([A-Z]\\w+).+(\\d) (\\w+) on (\\d+/\\d+/\\d+)")
public class FruitPicker {

    @Group(1)
    private String name;

    @Group(2)
    private int count;

    @Group(3)
    private String fruitType;

    @Group(4)
    @DateFormat("dd/MM/yyyy")
    private Date date;

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

    public String getFruitType() {
        return fruitType;
    }

    public void setFruitType(String fruitType) {
        this.fruitType = fruitType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
```

To use the POJO matching feature, call the **Rojo.of()** method. To get the first fruit picker, continue by calling **match()**: 
```java
Optional<FruitPicker> firstPicker = Rojo.of(FruitPicker.class).match(input);
firstPicker.ifPresent( picker ->
    System.out.println("The first picker is "+picker.getName()+", who picked "+picker.getCount()+" "+picker.getFruitType())
);
```
Console output:
```
The first picker is John, who picked 7 apples
```

Note that **match()** returns an Optional, so if there was no match found, the Optional will be empty. To get a List of all fruit pickers, you will use **matchList()**:
```java
List<FruitPicker> allPickers = Rojo.of(FruitPicker.class).matchList(input);
for ( FruitPicker picker : allPickers) {
    System.out.println(picker.getName()+" ("+picker.getCount()+" "+picker.getFruitType()+" on "+picker.getDate()+")");
}
```
Console output:
```
John (7 apples on Thu Jun 02 00:00:00 CEST 2016)
Peter (2 pears on Mon Jun 13 00:00:00 CEST 2016)
Jane (5 bananas on Tue Jul 05 00:00:00 CEST 2016)
```

You can achieve the same thing by using the **matchStream()** directly:
```java
Rojo.of(FruitPicker.class).matchStream(input).forEach( picker ->
    System.out.println(picker.getName()+" ("+picker.getCount()+" "+picker.getFruitType()+" on "+picker.getDate()+")")
);
```

Stream can have a lot of advantages. E.g. you can use it just to count all picked fruits:
```java
long total = Rojo.of(FruitPicker.class).matchStream(input)
                .collect(Collectors.summingInt(FruitPicker::getCount));

System.out.println("Total fruits collected: "+total);
```
Console output:
```
Total fruits collected: 14
```

### Nested matching
Imagine a situation where you want to match groups to POJO's fields, where one of those fields is an another POJO class with its own regex, which will match the content of the previously parsed group. Let's define such scenario, where we will match person's name and store his/her height and weight into the Body class, which will have a method to compute a person's BMI index:

```java
@Regex("(\\w+): (.+)")
public class Person {

    @Group(1)
    private String name;

    @Group(2)
    private Body body;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }
}
```

```java
@Regex("(\\d+)cm,(\\d+)kg")
public class Body {

    @Group(1)
    private double height;
    
    @Group(2)
    private double weight;

    public double getBmi() {
        return weight/Math.pow((height/100),2);
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
```

We will match this input String and print the results:
```java
String input = "Thomas: 180cm,75kg\n" +
        "Jane: 163cm,45kg\n" +
        "Mark: 175cm,60kg";

Rojo.of(Person.class).matchStream(input).forEach( person -> {
    System.out.println(person.getName()+"'s BMI index is: "+person.getBody().getBmi());
});
```

Console output:
```
Thomas's BMI index is: 23.148148148148145
Jane's BMI index is: 16.937031879257784
Mark's BMI index is: 19.591836734693878
```

### Plain matching
You don't always need to match a POJO bean, but Rojo also enables you to do the "plain matching" in much more convenient way, than if you've used the Java's Pattern.compile() manually. Let's print just the first picker's name: 
```java
Optional<String> firstName = Rojo.find("[A-Z]\\w+", input);
firstName.ifPresent( name ->
    System.out.println("The first picker is "+name)
);
``` 
Console output:
```
The first picker is John
```

To print out all names you can use **asList()**:
```java
List<String> allNames = Rojo.asList("[A-Z]\\w+", input);
for ( String name : allNames) {
    System.out.println(name);
}
```
Console output:
```
John
Peter
Jane
```
or the same thing using **asStream()**:
```java
Rojo.asStream("[A-Z]\\w+", input).forEach(System.out::println);
```

In some cases you may want to match for a pair of groups and see the result as a Map<String,String>. You can do that by calling **asMap()**. Let's find pairs which will contain the picker's name and the count of the fruits that he/she has collected:
```java
Map<String, String> pickersMap = Rojo.asMap("([A-Z]\\w+).+?(\\d)", input);
System.out.println(pickersMap);
```
Console output:
```
{John=7, Peter=2, Jane=5}
```

What if we wanted to change all collected fruit names to upper-case? There's a useful **replace()** method for that:
```java
String replaced = Rojo.replace("\\d \\w+", input, String::toUpperCase);
System.out.println(replaced);
```
Console output:
```
John picked 7 APPLES on 2/6/2016.
Peter picked only 2 PEARS on 13/6/2016.
Jane collected 5 BANANAS on 5/7/2016.
```
... for more advanced replacement you can use **replaceMatcher()**.

Since 1.0.1 there is a new **forEach()** method which allows you to iterate over the regexp matches, where it extracts all groups (2 and more, up to 10) into the lambda function arguments:
```java
Rojo.forEach("([A-Z]\\w+).+(\\d) (\\w+) on (\\d+/\\d+/\\d+)", input,
    (name, count, fruit, date) -> System.out.println(name + " (" + count + " " + fruit + " on " + date + ")")
);
```
Console output:
```
John (7 apples on 2/6/2016)
Peter (2 pears on 13/6/2016)
Jane (5 bananas on 5/7/2016)        
```

## Performance tuning
Since **Rojo.of()** and all other **plain-matching** methods always create a new Pattern instance inside, it might be expensive if you want to do the matching with the same regexp on a large amount of different input Strings. For this purpose you may want to store the matcher and re-use it. For POJO matching, you can just store the **RojoBeanMatcher** instance returned by **Rojo.of()**, so this code:
```java
SimpleBean bean = Rojo.of(SimpleBean.class)
                    .match(input).get();
```
will be replaced by:
```java
RojoBeanMatcher<SimpleBean> matcher = Rojo.of(SimpleBean.class);
SimpleBean bean = matcher.match(input).get();
```

For plain-matching there is a **Rojo.matcher()** method which obtains the **RojoMatcher** instance, so this code:
```java
List<String> list = Rojo.asList("[a-z]", input);
```
will turn into this one:
```java
RojoMatcher matcher = Rojo.matcher("[a-z]");
List<String> list = matcher.asList(input);
```
## Annotations overview
Class annotations:
- @Regex - here's where you specify your regexp pattern as String
- @Flags - if you want to use flags such as Pattern.DOTALL etc.

Field annotations:
- @Regex - only for List type of fields which don't use the "nested class matching" (=class annotated by @Regex itself) as a generic type
- @Group - group number that corresponds to the field
- @DateFormat - this annotation is mandatory only for the Date type fields, where you have to specify the date format

## Final words
If you have any ideas how to enhance this library, you're welcome to do so, because it would be awesome, if we could make this an ultimate Java library for a regular expression parsing.