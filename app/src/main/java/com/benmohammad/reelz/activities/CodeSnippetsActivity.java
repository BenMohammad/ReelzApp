package com.benmohammad.reelz.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.benmohammad.reelz.R;
import com.benmohammad.reelz.adapter.Communicator;
import com.benmohammad.reelz.adapter.SnippetAdapter;
import com.benmohammad.reelz.model.Snippet;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CodeSnippetsActivity extends AppCompatActivity implements Communicator {

    String [] files;
    ArrayList<Snippet> arr;
    private AdView adView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snippets);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        arr = new ArrayList<>();

        loadSnippets();
    }

    private void loadSnippets(){
        arr.clear();
        Snippet obj1 = new Snippet("Lambda (Basic)", "import java.util.ArrayList;\n" +
                "\n" +
                "public class LambdaInJava {\n" +
                "  public static void main(String[] args) {\n" +
                "    ArrayList<Integer> numbers = new ArrayList<Integer>();\n" +
                "    numbers.add(5);\n" +
                "    numbers.add(9);\n" +
                "    numbers.add(8);\n" +
                "    numbers.add(1);\n" +
                "    numbers.forEach( (n) -> { System.out.println(n); } );\n" +
                "  }\n" +
                "}");

        Snippet obj2 = new Snippet("Filter", "import java.util.ArrayList;  \n" +
                "import java.util.List;  \n" +
                "import java.util.stream.Stream;   \n" +
                "class Product{  \n" +
                "    int id;  \n" +
                "    String name;  \n" +
                "    float price;  \n" +
                "    public Product(int id, String name, float price) {  \n" +
                "        super();  \n" +
                "        this.id = id;  \n" +
                "        this.name = name;  \n" +
                "        this.price = price;  \n" +
                "    }  \n" +
                "}  \n" +
                "public class FilterInJava{  \n" +
                "    public static void main(String[] args) {  \n" +
                "        List<Product> list=new ArrayList<Product>();  \n" +
                "        list.add(new Product(1,\"Samsung A5\",100f));  \n" +
                "        list.add(new Product(3,\"Iphone 6S\",300f));  \n" +
                "        list.add(new Product(2,\"Sony Xperia\",200f));  \n" +
                "        list.add(new Product(4,\"Nokia Lumia\",150f));  \n" +
                "        list.add(new Product(5,\"Redmi4 \",260f));  \n" +
                "        list.add(new Product(6,\"Lenevo Vibe\",190f));  \n" +
                "          \n" +
                "    \n" +
                "        Stream<Product> filtered_data = list.stream().filter(p -> p.price > 200);  \n" +
                "          \n" +
                "    \n" +
                "        filtered_data.forEach(  \n" +
                "                product -> System.out.println(product.name+\": \"+product.price)  \n" +
                "        );  \n" +
                "    }  \n" +
                "}");




        Snippet obj3 = new Snippet("Functional Interface", "@FunctionalInterface\n" +
                "interface GenericInterface<T> {\n" +
                "\n" +
                "    \n" +
                "    T func(T t);\n" +
                "}\n" +
                "\n" +

                "public class FunctionalImplementationInJava {\n" +
                "\n" +
                "    public static void main( String[] args ) {\n" +
                "\n" +
                "        GenericInterface<String> reverse = (str) -> {\n" +
                "\n" +
                "            String result = \"\";\n" +
                "            for (int i = str.length()-1; i >= 0 ; i--)\n" +
                "            result += str.charAt(i);\n" +
                "            return result;\n" +
                "        };\n" +
                "\n" +
                "        System.out.println(\"Lambda reversed = \" + reverse.func(\"Lambda\"));\n" +
                "\n" +
                "        GenericInterface<Integer> factorial = (n) -> {\n" +
                "\n" +
                "            int result = 1;\n" +
                "            for (int i = 1; i <= n; i++)\n" +
                "            result = i * result;\n" +
                "            return result;\n" +
                "        };\n" +
                "\n" +
                "        System.out.println(\"factorial of 5 = \" + factorial.func(5));\n" +
                "    }\n" +
                "}");




        Snippet obj4 = new Snippet("Fibonacci", "import java.util.List;\n" +
                "import java.util.stream.*;\n" +
                "\n" +
                "public class FibonacciInJava {\n" +
                "   public static void main(String args[]) {\n" +
                "      System.out.println(FibonacciTest.generate(10));\n" +
                "   }\n" +
                "   public static List generate(int series) {\n" +
                "      return Stream.iterate(new int[]{0, 1}, s -> new int[]{s[1], s[0] + s[1]}) /\n" +
                "                  .limit(series)\n" +
                "     .map(n -> n[0])\n" +
                "     .collect(Collectors.toList());\n" +
                "   }\n" +
                "}");

        Snippet obj5 = new Snippet("DoubleConsumer", "import java.util.function.DoubleConsumer;\n" +
                "\n" +
                "public class DoubleConsumerInJava {\n" +
                "   public static void main(String args[]) {\n" +
                "      DoubleConsumer increment = doubleVal -> {       \n" +
                "         System.out.println(\"Incrementing \" + doubleVal + \" by one\");\n" +
                "         System.out.println(\"Current Value : \"+ (doubleVal+1));\n" +
                "      };\n" +
                "      DoubleConsumer decrement = doubleVal -> {       \n" +
                "         System.out.println(\"Decrementing \" + doubleVal + \" by one\");\n" +
                "         System.out.println(\"Current Value : \" + (doubleVal-1));\n" +
                "      };\n" +
                "      DoubleConsumer result = increment.andThen(decrement);\n" +
                "      result.accept(777);\n" +
                "   }\n" +
                "}");

        Snippet obj6 = new Snippet("Streamn",
                "import java.util.Arrays;\n" +
                        "import java.util.List;\n" +
                        "import java.util.stream.Collectors;\n" +
                        " \n" +
                        "public class StreamInJava {\n" +
                        "  public static void main(String[] args) {\n" +
                        "    List<Integer> intLst = Arrays.asList(1, 2, 3, 4);\n\n" +
                        "    List<Integer> newLst = intLst.stream()\n" +
                        "                    .map(n->n*2)\n" +
                        "                      .collect(Collectors.toList());\n" +
                        "    \n" +
                        "    System.out.println(newLst);\n" +
                        "    // [2, 4, 6, 8]\n" +
                        "  }\n" +
                        "}");



        Snippet obj7 = new Snippet("BiFunction", "import java.util.Arrays;\n" +
                "import java.util.List;\n" +
                "import java.util.function.BiFunction;\n" +
                "\n" +
                "public class BiFunctionInJava {\n" +
                "\n" +
                "    public static void main(String[] args) {\n" +
                "\n" +
                "        \n" +
                "        BiFunction<Integer, Integer, Integer> func = (x1, x2) -> x1 + x2;\n" +
                "\n" +
                "        Integer result = func.apply(2, 3);\n" +
                "\n" +
                "        System.out.println(result); \n" +
                "\n" +
                "        \n" +
                "        BiFunction<Integer, Integer, Double> func2 = (x1, x2) -> Math.pow(x1, x2);\n" +
                "\n" +
                "        Double result2 = func2.apply(2, 4);\n" +
                "\n" +
                "        System.out.println(result2);    \n" +
                "\n" +
                "        // take two Integers and return a List<Integer>\n" +
                "        BiFunction<Integer, Integer, List<Integer>> func3 = (x1, x2) -> Arrays.asList(x1 + x2);\n" +
                "\n" +
                "        List<Integer> result3 = func3.apply(2, 3);\n" +
                "\n" +
                "        System.out.println(result3);\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "}");

        Snippet obj8 = new Snippet("BiFunction Advanced", "import java.util.function.BiFunction;\n" +
                "import java.util.function.Function;\n" +
                "\n" +
                "public class BiFunctionInJava {\n" +
                "\n" +
                "    public static void main(String[] args) {\n" +
                "\n" +
                "        // Take two Integers, pow it into a Double, convert Double into a String.\n" +
                "        String result = convert(2, 4,\n" +
                "                (a1, a2) -> Math.pow(a1, a2),\n" +
                "                (r) -> \"Pow : \" + String.valueOf(r));\n" +
                "\n" +
                "        System.out.println(result);     \n" +
                "\n" +
                "        // Take two Integers, multiply into an Integer, convert Integer into a String.\n" +
                "        String result2 = convert(2, 4,\n" +
                "                (a1, a2) -> a1 * a1,\n" +
                "                (r) -> \"Multiply : \" + String.valueOf(r));\n" +
                "\n" +
                "        System.out.println(result2);    \n" +
                "\n" +
                "        // Take two Strings, join both, join \"cde\"\n" +
                "        String result3 = convert(\"a\", \"b\",\n" +
                "                (a1, a2) -> a1 + a2,\n" +
                "                (r) -> r + \"cde\");      \n" +
                "\n" +
                "        System.out.println(result3);\n" +
                "\n" +
                "        // Take two Strings, join both, convert it into an Integer\n" +
                "        Integer result4 = convert(\"100\", \"200\",\n" +
                "                (a1, a2) -> a1 + a2,\n" +
                "                (r) -> Integer.valueOf(r));\n" +
                "\n" +
                "        System.out.println(result4);    \n" +
                "\n" +
                "    }\n" +
                "\n" +
                "    public static <A1, A2, R1, R2> R2 convert(A1 a1, A2 a2,\n" +
                "                                              BiFunction<A1, A2, R1> func,\n" +
                "                                              Function<R1, R2> func2) {\n" +
                "\n" +
                "        return func.andThen(func2).apply(a1, a2);\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "}");

        Snippet obj9 = new Snippet("BiPredicate", "import java.util.function.BiPredicate;\n" +
                "\n" +
                "public class BiPredicateInJava {\n" +
                "\n" +
                "    public static void main(String[] args) {\n" +
                "\n" +
                "        BiPredicate<String, Integer> filter = (x, y) -> {\n" +
                "            return x.length() == y;\n" +
                "        };\n" +
                "\n" +
                "        boolean result = filter.test(\"ReelZ\", 5);\n" +
                "        System.out.println(result);  \n" +
                "\n" +
                "        boolean result2 = filter.test(\"java\", 10);\n" +
                "        System.out.println(result2); \n" +
                "    }\n" +
                "\n" +
                "}");

        Snippet obj10 = new Snippet("BiPredicate Advanced", "import java.util.Arrays;\n" +
                "import java.util.List;\n" +
                "import java.util.function.BiPredicate;\n" +
                "import java.util.stream.Collectors;\n" +
                "\n" +
                "public class BiPredicateInJava {\n" +
                "\n" +
                "    public static void main(String[] args) {\n" +
                "\n" +
                "        List<Domain> domains = Arrays.asList(new Domain(\"google.com\", 1),\n" +
                "                new Domain(\"reelz.com\", 10),\n" +
                "                new Domain(\"spam.com\", 0),\n" +
                "                new Domain(\"love.com\", 2));\n" +
                "\n" +
                "        BiPredicate<String, Integer> bi = (domain, score) -> {\n" +
                "            return (domain.equalsIgnoreCase(\"google.com\") || score == 0);\n" +
                "        };\n" +
                "\n" +
                "        // if google or score == 0\n" +
                "        List<Domain> result = filterBadDomain(domains, bi);\n" +
                "        System.out.println(result); \n" +
                "\n" +
                "        //  if score == 0\n" +
                "        List<Domain> result2 = filterBadDomain(domains, (domain, score) -> score == 0);\n" +
                "        System.out.println(result2); \n" +
                "\n" +
                "        // if start with i or score > 5\n" +
                "        List<Domain> result3 = filterBadDomain(domains, (domain, score) -> domain.startsWith(\"i\") && score > 5);\n" +
                "        System.out.println(result3); \n" +
                "\n" +
                "        // chaining with or\n" +
                "        List<Domain> result4 = filterBadDomain(domains, bi.or(\n" +
                "                (domain, x) -> domain.equalsIgnoreCase(\"microsoft.com\"))\n" +
                "        );\n" +
                "        System.out.println(result4); \n" +
                "\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "    public static <T extends Domain> List<T> filterBadDomain(\n" +
                "            List<T> list, BiPredicate<String, Integer> biPredicate) {\n" +
                "\n" +
                "        return list.stream()\n" +
                "                .filter(x -> biPredicate.test(x.getName(), x.getScore()))\n" +
                "                .collect(Collectors.toList());\n" +
                "\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "class Domain {\n" +
                "\n" +
                "    String name;\n" +
                "    Integer score;\n" +
                "\n" +
                "    public Domain(String name, Integer score) {\n" +
                "        this.name = name;\n" +
                "        this.score = score;\n" +
                "    }\n" +
                "    \n" +
                "}");

        Snippet obj11 = new Snippet("UnaryOperator", "import java.util.function.Function;\n" +
                "import java.util.function.UnaryOperator;\n" +
                "\n" +
                "public class UnaryOperatorInJava {\n" +
                "\n" +
                "    public static void main(String[] args) {\n" +
                "\n" +
                "        Function<Integer, Integer> func = x -> x * 2;\n" +
                "\n" +
                "        Integer result = func.apply(2);\n" +
                "\n" +
                "        System.out.println(result);         \n" +
                "\n" +
                "        UnaryOperator<Integer> func2 = x -> x * 2;\n" +
                "\n" +
                "        Integer result2 = func2.apply(2);\n" +
                "\n" +
                "        System.out.println(result2);        \n" +
                "\n" +
                "    }\n" +
                "\n" +
                "}");

        Snippet obj12 = new Snippet("Supplier",
                "import java.util.Date;\n" +
                "import java.util.function.Supplier;\n" +
                "public class SupplierInJava {\n" +
                " public static void main(String args[]) {\n" +
                "  //Supplier instance with lambda expression\n" +
                "  Supplier<String> helloStrSupplier = () -> new String(\"Hello\");\n" +
                "  String helloStr = helloStrSupplier.get();\n" +
                "  System.out.println(\"String in helloStr is->\"+helloStr+\"<-\");\n" +
                "   \n" +
                "  //Supplier instance using method reference to default constructor\n" +
                "  Supplier<String> emptyStrSupplier = String::new;\n" +
                "  String emptyStr = emptyStrSupplier.get();\n" +
                "  System.out.println(\"String in emptyStr is->\"+emptyStr+\"<-\");\n" +
                "   \n" +
                "  //Supplier instance using method reference to a static method\n" +
                "  Supplier<Date> dateSupplier= SupplierFunctionExample::getSystemDate;\n" +
                "  Date systemDate = dateSupplier.get();\n" +
                "  System.out.println(\"systemDate->\" + systemDate);\n" +
                " }\n" +
                " public static Date getSystemDate() {\n" +
                "  return new Date();\n" +
                " }\n" +
                "}");

        Snippet obj13 = new Snippet("TriFunction",
                "interface TriFunction<T, U, V, R> {\n" +
                        "  R apply(T t, U u, V v);\n" +
                        "}" +
                        "\n" +
                        "class Sum {\n" +
                        "  Integer doSum(String s1, String s2) {\n" +
                        "    return Integer.parseInt(s1) + Integer.parseInt(s1);\n" +
                        "  }\n" +
                        "}" +
                        "\n" +

                        "public class TriFunctionJava {\n" +

                        " public static void main(String args[]) {\n" +
                        "TriFunction<Sum, String, String, Integer> lambda =\n" +
                        "  (Sum s, String arg1, String arg2) -> s.doSum(arg1, arg1);\n" +
                        "System.out.println(lambda.apply(new Sum(), \"1\", \"4\"));" +
                        " }\n" +
                        "}\n" +
                        "");

        Snippet obj14 = new Snippet("Predicate",
                "public class PredicateInJava {\n" +
                        "\n" +
                        "   public static void main(String args[]) {\n" +
                        "      List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);\n" +
                        "\t\t\n" +

                        "\t\t\n" +
                        "      System.out.println(\"Print all numbers:\");\n" +
                        "\t\t\n" +

                        "      eval(list, n->true);\n" +
                        "\t\t\n" +

                        "\t\t\n" +
                        "      System.out.println(\"Print even numbers:\");\n" +
                        "      eval(list, n-> n%2 == 0 );\n" +
                        "\t\t\n" +

                        "\t\t\n" +
                        "      System.out.println(\"Print numbers greater than 3:\");\n" +
                        "      eval(list, n-> n > 3 );\n" +
                        "   }\n" +
                        "\t\n" +
                        "   public static void eval(List<Integer> list, Predicate<Integer> predicate) {\n" +
                        "\n" +
                        "      for(Integer n: list) {\n" +
                        "\n" +
                        "         if(predicate.test(n)) {\n" +
                        "            System.out.println(n + \" \");\n" +
                        "         }\n" +
                        "      }\n" +
                        "   }\n" +
                        "}");


        Snippet obj15 = new Snippet("ToIntBiFunction",
                "import java.util.function.ToIntBiFunction;\n" +
                        "public class ToIntBiFunctionInJava {\n" +
                        "   public static void main(String args[]) {\n" +
                        "      ToIntBiFunction<Integer, Integer> test = (t, u) -> t * u;\n" +
                        "      System.out.println(\"The multiplication of t and u is: \" + test.applyAsInt(8, 15));\n" +
                        "      System.out.println(\"The multiplication of t and u is: \" + test.applyAsInt(10, 7));\n" +
                        "   }\n" +
                        "}\n");

        Snippet obj16 = new Snippet("Optional",
                "import java.util.Optional;\n" +
                        "\n" +
                        "public class OptionalInJava {\n\n" +
                        "\tpublic static void main(String[] args) {\n" +
                        "\n" +
                        "\t\t\n" +
                        "\t\tOptional<Void> emptyOptional = Optional.empty();\n" +
                        "\n" +
                        "\t\t\n" +
                        "\t\tSystem.out.println(\" Is optional is empty : \" + emptyOptional.isEmpty());\n" +
                        "\n" +
                        "\t\t\n" +
                        "\t\tOptional<String> stringOptional = Optional.of(\"Hello\");\n" +
                        "\n" +
                        "\t\tif (stringOptional.isPresent()) {\n" +
                        "\t\t\tSystem.out.println(\"Getting value from stringOptional : \" + stringOptional.get());\n" +
                        "\t\t}\n" +
                        "\n" +
                        "\t\t\n" +
                        "\t\tOptional<Integer> intOptionbal = Optional.of(1244);\n" +
                        "\t\tSystem.out.println(\"Integer Optional: \" + intOptionbal.get());\n" +
                        "\t\t\n" +
                        "\t\tOptional<String> ofNullable = Optional.ofNullable(\"Non null value\");\n" +
                        "\n" +
                        "\t\tString content = ofNullable.get();\n" +
                        "\t\tSystem.out.println(\"Ofnullable value :\" + content);\n" +
                        "\n" +
                        "\t\t\n" +
                        "\t\tOptional nullOptional = Optional.ofNullable(null);\n" +
                        "\t\tnullOptional.get();" +
                        "\n" +
                        "\t}\n" +
                        "}");

        Snippet obj17 = new Snippet("Optional Advanced",
                "import java.util.Arrays;\n" +
                        "import java.util.List;\n" +
                        "import java.util.Optional;\n" +
                        "\n" +
                        "public class OptionalIfPresentInJava {\n" +
                        "\tpublic static void main(String[] args) {\n" +
                        "\n" +
                        "\t\tSystem.out.println(\"Example 1 : ------ Optional string ------ \");\n" +
                        "\t\t\n" +
                        "\t\tOptional<String> optional = Optional.ofNullable(\"ReelZ\");\n" +
                        "\n" +
                        "\t\t\n" +
                        "\t\toptional.ifPresent(value -> System.out.println(value));\n" +
                        "\n" +
                        "\t\tSystem.out.println(\"Example 2 : ------  Optional List of integers ------ \");\n" +
                        "\n" +
                        "\t\t\n" +
                        "\t\tList<Integer> list = Arrays.asList(1, 2, 3, 4, 5);\n" +
                        "\n" +
                        "\t\t\n" +
                        "\t\tOptional<List<Integer>> numbersOfListOptional = Optional.ofNullable(list);\n" +
                        "\n" +
                        "\t\t\n" +
                        "\t\tnumbersOfListOptional.ifPresent(optionalList -> optionalList.forEach(v -> System.out.println(v)));\n" +
                        "\n" +
                        "\t\tOptional empty = Optional.empty();\n" +
                        "\t\tempty.ifPresent(valeu -> System.out.println(\"no value\"));\n" +
                        "\n" +
                        "\t}\n" +
                        "}");


        Snippet obj18 = new Snippet("DoubleFunction", "import java.util.function.DoubleFunction;\n" +
                "public class DoubleFunctionInJava {\n" +
                "   public static void main(String[] args) {\n" +
                "      DoubleFunction<String> getGrade = marks -> { \n" +
                "         if(marks > 90 && marks <= 100) {\n" +
                "            return \"A\";\n" +
                "         }\n" +
                "         else if(marks > 70 && marks <= 90) {\n" +
                "            return \"B\";\n" +
                "         }\n" +
                "         else if(marks > 50 && marks <= 70) {\n" +
                "            return \"C\";\n" +
                "         }\n" +
                "         else {\n" +
                "            return \"D\";\n" +
                "         }\n" +
                "      };\n" +
                "      double input = 95;\n" +
                "      System.out.println(\"Input Marks: \" + input);\n" +
                "      String grade = getGrade.apply(input);\n" +
                "      System.out.println(\"Grade : \"+ grade + \"\\n\");\n" +
                "      input = 85;\n" +
                "      System.out.println(\"Input Marks: \" + input);\n" +
                "      System.out.println(\"Grade : \" + getGrade.apply(input) + \"\\n\");\n" +
                "      input = 67;\n" +
                "      System.out.println(\"Input Marks: \" + input);\n" +
                "      System.out.println(\"Grade : \" + getGrade.apply(input) + \"\\n\");\n" +
                "      input = 49;\n" +
                "      System.out.println(\"Input Marks: \" + input);\n" +
                "      System.out.println(\"Grade : \" + getGrade.apply(input));\n" +
                "   }\n" +
                "}");




        arr.add(obj1);
        arr.add(obj2);
        arr.add(obj3);
        arr.add(obj4);
        arr.add(obj5);
        arr.add(obj6);
        arr.add(obj7);
        arr.add(obj8);
        arr.add(obj9);
        arr.add(obj10);
        arr.add(obj11);
        arr.add(obj12);
        arr.add(obj13);
        arr.add(obj14);
        arr.add(obj15);
        arr.add(obj16);
        arr.add(obj17);
        arr.add(obj18);




        File f = new File("" + getFilesDir() + "/snippet/");
        FilenameFilter filenameFilter = new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                return s.contains(".");
            }
        };

        if(!f.exists()) {
            f.mkdir();
        }
        files = f.list(filenameFilter);
        assert files != null;
        for(String fileName: files) {
            try {
                String yourFilePath = getApplicationContext().getFilesDir() + "/snippet/" + fileName;
                FileInputStream fin = new FileInputStream(yourFilePath);
                InputStreamReader isr = new InputStreamReader(fin);
                BufferedReader bufferedReader = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String line = "";
                while(true) {
                    try {
                        if(!((line = bufferedReader.readLine()) != null)) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    sb.append(line + "\n");
                }
                Snippet tempContainer = new Snippet(fileName.substring(0, fileName.length() - 5), sb.toString());
                arr.add(tempContainer);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        ListView l1 = findViewById(R.id.sniplist);
        l1.setAdapter(new SnippetAdapter(arr, getApplicationContext(),this));
    }

    public void customSetResult(String snippet) {
        Intent i = new Intent();
        i.putExtra("snippet", snippet);
        setResult(RESULT_OK, i);
        finish();
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                return true;
            case R.id.menuInfo:
                Intent intent = new Intent(this, InfoActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.snippets_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


}
