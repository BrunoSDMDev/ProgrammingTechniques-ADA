package aula5;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Pipelines {

    public static void main(String[] args) {

        // Stream de números "aleatórios"
        Stream<Double> integerStream = Stream.generate(Math::random);

        // Filtre os números pares e positivos, ordenando e limitando a 10 elementos e, por fim, imprimindo o resultado

        integerStream
                .limit(100)
                .map(x -> {
                    Double aux = x * 10;
                    return aux.intValue();
                })
                .filter(y -> (y % 2 == 0) && (y > 0))
                .sorted()
                .forEach(System.out::println);
//        integerStream.filter (num -> num > 0 && num % 2 == 0).sorted().limit(10).forEach(System.out::println);


    }
}
